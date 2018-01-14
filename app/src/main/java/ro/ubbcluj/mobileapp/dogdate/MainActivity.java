package ro.ubbcluj.mobileapp.dogdate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //private ArrayList<Dog> allDogs = new ArrayList<>();
    //private DogsRepository repo;

    private String user;
    private String role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button) findViewById(R.id.mainSyncButton);
        btn1.setVisibility(View.INVISIBLE);
        btn1.setClickable(false);

        Button btn2 = (Button) findViewById(R.id.dogs_button);
        btn2.setVisibility(View.INVISIBLE);
        btn2.setClickable(false);

        Button btn3 = (Button) findViewById(R.id.stats_button);
        btn3.setVisibility(View.INVISIBLE);
        btn3.setClickable(false);

        //populate();

    }

    //public void populate(){
        //repo = new DogsRepository(getApplicationContext());
        //allDogs = repo.getAllDogs();
    //}

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
        //populate();
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    //allDogs = (ArrayList<Dog>) data.getSerializableExtra(getString(R.string.key_all_dogs));
                    getUserData(data);
                }
                break;
            }
            case (2) : {
                if (resultCode == Activity.RESULT_OK) {
                    getUserData(data);
                }
                break;
            }

            case (3) : {
                if (resultCode == Activity.RESULT_OK) {
                    getUserData(data);

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
        intent.putExtra("user",user);
        intent.putExtra("access",role);
        int req_code_modify = 1;
        startActivityForResult(intent,req_code_modify);
    }

    public void showDogStats(View view) {
        Intent statsIntent = new Intent(this, DogStatsActivity.class);
        statsIntent.putExtra("user",user);
        statsIntent.putExtra("access",role);
        //statsIntent.putExtra(getString(R.string.key_all_dogs), allDogs);
        startActivityForResult(statsIntent,2);
    }

    public void login(View view){
        Intent loginIntent = new Intent(this, LoginActivity.class);
        int req_code_login = 3;
        startActivityForResult(loginIntent,3);
    }

    private void getUserData(Intent data){
        user = data.getStringExtra("user");
        role = data.getStringExtra("access");

        TextView idk = (TextView) findViewById(R.id.mainUserLabel);
        idk.setText(user);

        if(role.equals("admin")){
            Button btn = (Button) findViewById(R.id.stats_button);
            btn.setClickable(true);
            btn.setVisibility(View.VISIBLE);
        }
        else{
            Button btn = (Button) findViewById(R.id.stats_button);
            btn.setClickable(false);
            btn.setVisibility(View.INVISIBLE);
        }

        Button btn1 = (Button) findViewById(R.id.mainSyncButton);
        btn1.setVisibility(View.VISIBLE);
        btn1.setClickable(true);

        Button btn2 = (Button) findViewById(R.id.dogs_button);
        btn2.setVisibility(View.VISIBLE);
        btn2.setClickable(true);
    }


}
