package ro.ubbcluj.mobileapp.dogdate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;
import ro.ubbcluj.mobileapp.dogdate.repository.DogsRepository;


public class DogListActivity extends AppCompatActivity  {

    private ArrayList<Dog> allDogs;
    private DogsRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_list);

        repo = new DogsRepository(getApplicationContext());

        Intent intent = getIntent();
        //allDogs = (ArrayList<Dog>) intent.getSerializableExtra(getString(R.string.key_all_dogs));
        populate();

    }


    @Override
    public void onBackPressed() {
        Intent allDogsIntent = new Intent();
        allDogsIntent.putExtra(getString(R.string.key_all_dogs),allDogs);
        setResult(Activity.RESULT_OK,allDogsIntent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (2) : {
                if (resultCode == Activity.RESULT_OK) {

//                    if(data.hasExtra(getString(R.string.key_upd_dog))){
//                        Dog doggo = (Dog) data.getSerializableExtra(getString(R.string.key_upd_dog));
//                        if (!allDogs.contains(doggo)){
//                            showToast(doggo.getName()+ " does not exists!");
//                        }
//                        else {
//                            allDogs.remove(doggo);
//                            allDogs.add(doggo);
//                            showToast("Updated " + doggo.getName());
//                        }
//                    }
//                    if(data.hasExtra(getString(R.string.key_del_dog))){
//                        Dog doggo = (Dog) data.getSerializableExtra(getString(R.string.key_del_dog));
//                        if (!allDogs.contains(doggo)){
//                            showToast(doggo.getName()+ " does not exists!");
//                        }
//                        else {
//                            allDogs.remove(doggo);
//                            showToast("Deleted " + doggo.getName());
//                        }
//
//                    }
                }
                break;
            }
            case (3) : {
                if (resultCode == Activity.RESULT_OK){
                    if(data.hasExtra(getString(R.string.key_add_dog))){
                        Dog doggo = (Dog) data.getSerializableExtra(getString(R.string.key_add_dog));
                        if (allDogs.contains(doggo)){
                            showToast(doggo.getName()+ " already exists!");
                        }
                        else{
                            allDogs.add(doggo);
                            showToast("Added " + doggo.getName());
                        }
                    }
                }
            }
        }
        populate();
    }

    public void populate(){

        allDogs = repo.getAllDogs();

        ArrayAdapter<Dog> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,allDogs);
        ListView dogs = (ListView) findViewById(R.id.dogListView);

        dogs.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Dog clicked = (Dog) parent.getItemAtPosition(position);
                viewDog(clicked);
            }
        });

        dogs.setAdapter(adapter);
    }

    public void viewDog(Dog doggo){
        Intent intent = new Intent(this, ViewDogActivity.class);
        intent.putExtra(getString(R.string.key_view_dog), doggo);
        int req_code_view_dog = 2;
        startActivityForResult(intent,req_code_view_dog);
    }

    public void addDog(View view){
        Intent intent = new Intent(this, AddDogActivity.class);
        int req_code_add_dog = 3;
        startActivityForResult(intent,req_code_add_dog);
    }


    public void showToast(String message)
    {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }


}
