package ro.ubbcluj.mobileapp.dogdate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;


public class MainActivity extends AppCompatActivity {


    private ArrayList<Dog> allDogs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allDogs.add(new Dog("Lucy","Husky","Friendly",2));
        allDogs.add(new Dog("Mark","Pitbull","Active",3));
        allDogs.add(new Dog("Puddles","Dalmatian","Scared",4));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.get_dogs:
                showToast("Loading dogs ...");
                return true;
            case R.id.add_dog:
                showToast("Opening dog creator ...");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    allDogs = (ArrayList<Dog>) data.getSerializableExtra(getString(R.string.key_all_dogs));
                    showToast("Dogs have been modified");
                }
                break;
            }
        }
    }
    public void showToast(String message)
    {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showAllDogs(View view)  {
        Intent intent = new Intent(this, DogListActivity.class);
        intent.putExtra(getString(R.string.key_all_dogs), allDogs);
        int req_code_modify = 1;
        startActivityForResult(intent,req_code_modify);
    }

    public void showDogStats(View view) {
        Intent statsIntent = new Intent(this, DogStatsActivity.class);
        statsIntent.putExtra(getString(R.string.key_all_dogs), allDogs);
        startActivity(statsIntent);
    }


}
