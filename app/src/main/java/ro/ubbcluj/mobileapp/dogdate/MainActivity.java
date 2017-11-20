package ro.ubbcluj.mobileapp.dogdate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
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
                    Dog newDog = (Dog)data.getSerializableExtra("add-dog");
                    allDogs.add(newDog);
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

    public void showAllDogs(MenuItem item)  {
        Intent intent = new Intent(this, DogListActivity.class);
        intent.putExtra("dogList", allDogs);
        startActivity(intent);
    }

    public void addNewDog(MenuItem item)  {
        Intent intent = new Intent(this, AddDogActivity.class);
        String message = "DOGGO NEW";
        intent.putExtra("msg", message);
        int req_code_add = 1;
        startActivityForResult(intent,req_code_add);
    }

    public void addDog(Dog dog){
        this.allDogs.add(dog);
    }
}
