package ro.ubbcluj.mobileapp.dogdate;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;
import ro.ubbcluj.mobileapp.dogdate.repository.AppDatabase;
import ro.ubbcluj.mobileapp.dogdate.repository.DogsDAO;

public class AddDogActivity extends AppCompatActivity {

    Dog doggo;
    TextView name,pers,age;
    TextView race;
    CheckBox sendToMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);

        name = (TextView) findViewById(R.id.addDogNameEdit);
        race = (TextView) findViewById(R.id.addDogSelectRaceButton);
        pers = (TextView) findViewById(R.id.addDogPersEdit);
        age = (TextView) findViewById(R.id.addDogAgeEdit);
    }

    public boolean validDog(){
        if(name.getText().toString().isEmpty() || race.getText().toString().equals("Select Race") || pers.getText().toString().isEmpty() || age.toString().isEmpty())
            return false;
        return true;
    }

    public void alert(String title,String msg){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

        builder.setMessage(msg)
                .setTitle(title);

        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addDog(View view){

        doggo = new Dog(name.getText().toString(),race.getText().toString(),pers.getText().toString(),Integer.parseInt(age.getText().toString()));

        sendToMail = (CheckBox) findViewById(R.id.addDogMailCheck);

        if (validDog()) {
            if(sendToMail.isChecked()){
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, doggo.toString());

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Select Email app"));
            }

            addToDB(doggo);
            Intent addIntent = new Intent(this,MainActivity.class);
            addIntent.putExtra(getString(R.string.key_add_dog),doggo);
            setResult(Activity.RESULT_OK,addIntent);
            finish();
        }
        else {
            if(name.getText().toString().isEmpty() || pers.getText().toString().isEmpty() || age.toString().isEmpty())
                alert("Empty fields","All fields must be completed!");
            else
                if(race.getText().toString().equals("Select Race"))
                    alert("Invalid race","A dog breed must be selected in order to proceed!");
                else
                {
                    int idk = Integer.parseInt(age.getText().toString());
                    if(idk < 0 || idk >20)
                        alert("Invalid age","A dog's age must be between 0 and 20!");
                }
        }
    }

    public void addToDB(Dog dog){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dogdate-db").allowMainThreadQueries()
                .build();
        DogsDAO dao = db.dogsDAO();
        dao.insertDog(dog);
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
