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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.ubbcluj.mobileapp.dogdate.Utils.ApiUtils;
import ro.ubbcluj.mobileapp.dogdate.domain.Dog;
import ro.ubbcluj.mobileapp.dogdate.observers.Subject;
import ro.ubbcluj.mobileapp.dogdate.observers.UserDataRepository;
import ro.ubbcluj.mobileapp.dogdate.service.UserService;


public class DogListActivity extends AppCompatActivity  {

    private List<Dog> allDogs;
    //private DogsRepository repo;

    Intent data;

    UserService userService;
    private Subject userDataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_list);

        data = getIntent();

        //repo = new DogsRepository(getApplicationContext());
        userService = ApiUtils.getUserService();
        userDataRepository = UserDataRepository.getInstance();



//        try {
//            Gson gson = new Gson();
//            Type type = new TypeToken<List<Dog>>(){}.getType();
//
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//        }

        //Intent intent = getIntent();
        populate();




        //allDogs = (ArrayList<Dog>) intent.getSerializableExtra(getString(R.string.key_all_dogs));

    }

    public void alert(String title,String msg){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

        builder.setMessage(msg)
                .setTitle(title);

        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(this,MainActivity.class);
        back.putExtra("user",data.getStringExtra("user"));
        back.putExtra("access",data.getStringExtra("access"));
        setResult(Activity.RESULT_OK,back);
        System.out.println(back.getStringExtra("user"));
        System.out.println(back.getStringExtra("access"));
        finish();
    }

//    @Override
//    public void onBackPressed() {
//        Intent allDogsIntent = new Intent();
//        allDogsIntent.putExtra(getString(R.string.key_all_dogs), (Parcelable) allDogs);
//        setResult(Activity.RESULT_OK,allDogsIntent);
//        finish();
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (2) : {
                if (resultCode == Activity.RESULT_OK) {
                    populate();
                    this.data = data;

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

    private void populateList(){

        //allDogs = repo.getAllDogs();

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

    public void populate(){
        Call<JsonArray> call = userService.getAllDogs();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                System.out.println("Ah");
                System.out.println(response.code());
                Gson gson = new Gson();
                Type type = new TypeToken<List<Dog>>(){}.getType();
                allDogs = gson.fromJson(response.body(), type);
                populateList();
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
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
