package ro.ubbcluj.mobileapp.dogdate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;


public class DogListActivity extends AppCompatActivity  {

    private ArrayList<Dog> allDogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_list);

        Intent intent = getIntent();
        allDogs = (ArrayList<Dog>) intent.getSerializableExtra("dogList");

        ArrayAdapter<Dog> adapter = new ArrayAdapter<Dog>(this,android.R.layout.simple_list_item_1,allDogs);
        ListView dogs = (ListView) findViewById(R.id.dogListView);
        dogs.setAdapter(adapter);
    }
}
