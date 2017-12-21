package ro.ubbcluj.mobileapp.dogdate;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;
import ro.ubbcluj.mobileapp.dogdate.repository.AppDatabase;
import ro.ubbcluj.mobileapp.dogdate.repository.DogsDAO;

public class ViewDogActivity extends AppCompatActivity {

    Dog doggo;
    TextView name,race;
    EditText pers,age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dog);

        Intent intent = getIntent();
        doggo = (Dog) intent.getSerializableExtra(getString(R.string.key_view_dog));
        fillData();
    }

    public void fillData(){
        name = (TextView) findViewById(R.id.viewDogNameView);
        name.setText(doggo.getName());

        race = (TextView) findViewById(R.id.viewDogSelectRaceButton);
        race.setText(doggo.getRace());

        pers = (EditText) findViewById(R.id.viewDogPersEdit);
        pers.setText(doggo.getPersonality());

        age = (EditText) findViewById(R.id.viewDogAgeEdit);
        age.setText(doggo.getAge()+"");
    }

    public Dog getData(){
        return new Dog(name.getText().toString(),race.getText().toString(),pers.getText().toString(),Integer.parseInt(age.getText().toString()));
    }


    public void UpdateDog(View view){
        updInDB(getData());
        Intent updIntent = new Intent(this,DogListActivity.class);
        updIntent.putExtra(getString(R.string.key_upd_dog),getData());
        setResult(Activity.RESULT_OK,updIntent);
        finish();
    }

    public void updInDB(Dog dog){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dogdate-db").allowMainThreadQueries()
                .build();
        DogsDAO dao = db.dogsDAO();
        dao.updateDogs(new Dog[]{doggo});
    }

    public void delFromDB(Dog dog){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dogdate-db").allowMainThreadQueries()
                .build();
        DogsDAO dao = db.dogsDAO();
        dao.deleteDog(doggo.key);
    }

    public void DeleteDog(View view){
        delFromDB(getData());
        Intent delIntent = new Intent(this,DogListActivity.class);
        delIntent.putExtra(getString(R.string.key_del_dog),getData());
        setResult(Activity.RESULT_OK,delIntent);
        finish();
    }

    public void selectRace(View view){
        String[] races;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        races = getResources().getStringArray(R.array.dog_races);
        builder.setTitle("Choose race")
                .setItems(R.array.dog_races, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedRace = races[which];
                        race.setText(selectedRace);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
