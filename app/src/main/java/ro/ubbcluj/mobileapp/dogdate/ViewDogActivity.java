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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.ubbcluj.mobileapp.dogdate.Utils.ApiUtils;
import ro.ubbcluj.mobileapp.dogdate.domain.Dog;
import ro.ubbcluj.mobileapp.dogdate.observers.UserDataRepository;
import ro.ubbcluj.mobileapp.dogdate.repository.AppDatabase;
import ro.ubbcluj.mobileapp.dogdate.repository.DogsDAO;
import ro.ubbcluj.mobileapp.dogdate.service.UserService;

public class ViewDogActivity extends AppCompatActivity {

    Dog doggo;
    TextView name,race;
    EditText pers,age;

    UserService userService;
    UserDataRepository userDataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dog);

        userService = ApiUtils.getUserService();
        userDataRepository = UserDataRepository.getInstance();

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
        //updInDB(getData());
        Intent updIntent = new Intent(this,DogListActivity.class);
        Call<Dog> call = userService.updateDog(getData());
        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                userDataRepository.updateDog(doggo);

                updIntent.putExtra(getString(R.string.key_upd_dog),getData());
                setResult(Activity.RESULT_OK,updIntent);
                finish();
            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {

            }
        });

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
        //delFromDB(getData());
        Intent delIntent = new Intent(this,DogListActivity.class);
        Call<Dog> call = userService.removeDog("" + doggo.getKey());
        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                userDataRepository.removeDog(doggo);

                delIntent.putExtra(getString(R.string.key_del_dog),getData());
                setResult(Activity.RESULT_OK,delIntent);
                finish();
            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {

            }
        });

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
