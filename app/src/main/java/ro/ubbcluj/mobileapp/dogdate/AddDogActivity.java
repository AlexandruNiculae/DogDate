package ro.ubbcluj.mobileapp.dogdate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;

public class AddDogActivity extends AppCompatActivity {

    Dog doggo;
    TextView name,race,pers,age;
    CheckBox sendToMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);

        Intent intent = getIntent();

    }


    public void addDog(View view){
        name = (TextView) findViewById(R.id.nameText);
        race = (TextView) findViewById(R.id.raceText);
        pers = (TextView) findViewById(R.id.persText);
        age = (TextView) findViewById(R.id.ageText);

        doggo = new Dog(name.getText().toString(),race.getText().toString(),pers.getText().toString(),Integer.parseInt(age.getText().toString()));

        sendToMail = (CheckBox) findViewById(R.id.sendMailCheck);

        if(sendToMail.isChecked()){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, doggo.toString());

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Select Email app"));
        }


        Intent addIntent = new Intent(this,MainActivity.class);
        addIntent.putExtra("add-dog",doggo);
        setResult(Activity.RESULT_OK,addIntent);
        finish();
    }

}
