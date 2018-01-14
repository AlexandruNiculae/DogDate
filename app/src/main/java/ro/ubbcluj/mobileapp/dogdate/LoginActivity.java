package ro.ubbcluj.mobileapp.dogdate;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.ubbcluj.mobileapp.dogdate.Utils.ApiUtils;
import ro.ubbcluj.mobileapp.dogdate.domain.ResponseObj;
import ro.ubbcluj.mobileapp.dogdate.domain.User;
import ro.ubbcluj.mobileapp.dogdate.observers.Subject;
import ro.ubbcluj.mobileapp.dogdate.observers.UserDataRepository;
import ro.ubbcluj.mobileapp.dogdate.service.UserService;

public class LoginActivity extends AppCompatActivity {

    UserService userService;
    private Subject userDataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userService = ApiUtils.getUserService();
        userDataRepository = UserDataRepository.getInstance();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void logIn(View view){
        Intent startIntent = new Intent(this,MainActivity.class);
        EditText username = (EditText) findViewById(R.id.loginUsernameEdit);
        EditText pass = (EditText) findViewById(R.id.loginPasswordEdit);
        final User[] responseUser = new User[1];

        String u = username.getText().toString();
        String p = pass.getText().toString();

        if(!valid(u,p))
            return;

        Supplier<String> login = () -> {
            try {
                Call<ResponseObj> call = userService.login(u,p);
                call.enqueue(new Callback<ResponseObj>() {
                    @Override
                    public void onResponse(Call<ResponseObj> call, Response<ResponseObj> response) {
                        ResponseObj res = response.body();
                        if (res.getMessage().equals("true")) {
                            final String[] user = {username.getText().toString()};
                            final String[] access = {""};

                            Call<User> call2 = userService.getUser(user[0]);
                            call2.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    //showNotification(user[0]);
                                    responseUser[0] = response.body();
                                    access[0] = responseUser[0].getType();

                                    startIntent.putExtra("user", user[0]);
                                    startIntent.putExtra("access", access[0]);

                                    setResult(Activity.RESULT_OK, startIntent);
                                    finish();

                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    showToast("An error occured!");
                                }
                            });

                        } else {
                            showToast("Invalid username or password:");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseObj> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }

            return "done";
        };
        ExecutorService exe = Executors.newFixedThreadPool(2);
        CompletableFuture<String> promise = CompletableFuture.supplyAsync(login,exe);
        promise.thenAccept( idk -> {

        });
        promise.complete("done");
        try {
            String wait = promise.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        Call<ResponseObj> call = userService.login(u,p);
//        call.enqueue(new Callback<ResponseObj>() {
//            @Override
//            public void onResponse(Call<ResponseObj> call, Response<ResponseObj> response) {
//                ResponseObj res = response.body();
//                if(res.getMessage().equals("true")){
//                    final String[] user = {username.getText().toString()};
//                    final String[] access = {""};
//
//                    Call<User> call2 = userService.getUser(user[0]);
//                    call2.enqueue(new Callback<User>() {
//                        @Override
//                        public void onResponse(Call<User> call, Response<User> response) {
//                            responseUser[0] = response.body();
//                            access[0] = responseUser[0].getType();
//
//                            startIntent.putExtra("user",user[0]);
//                            startIntent.putExtra("access",access[0]);
//
//                            setResult(Activity.RESULT_OK,startIntent);
//                            finish();
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<User> call, Throwable t) {
//                            showToast("An error occured!");
//                        }
//                    });
//
//                }
//                else{
//                   showToast("Invalid username or password:");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseObj> call, Throwable t) {
//                System.out.println(t.getMessage());
//            }
//        });
    }

    private boolean valid(String username, String password) {

        if (username == null || username.trim().length() == 0) {
            showToast("Username is empty");
            return false;
        }
        if (password == null || password.trim().length() == 0) {
            showToast("Password is empty");
            return false;
        }
        return true;
    }

//    public void showNotification(View view){
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_face_black_24dp)
//                        .setContentTitle("Welcome to Dog Date")
//                        .setContentText("Welcome");
//
//        NotificationManager.notify().mNotificationManager.notify(001, mBuilder.build());
//    }

    public void showToast(String message)
    {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
