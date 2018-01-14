package ro.ubbcluj.mobileapp.dogdate.service;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ro.ubbcluj.mobileapp.dogdate.domain.Dog;
import ro.ubbcluj.mobileapp.dogdate.domain.ResponseObj;
import ro.ubbcluj.mobileapp.dogdate.domain.User;

/**
 * Created by elega on 2018-01-13.
 */

public interface UserService {

    @GET("login")
    Call<ResponseObj> login(@Header("username") String username, @Header("password") String password);

    @GET("users")
    Call<JsonArray> getAllUsers();

    @POST("addUser")
    Call<User> addUser(@Body User user);

    @GET("dogs")
    Call<JsonArray> getAllDogs();

    @GET("user")
    Call<User> getUser(@Header("name") String name);

    @POST("removeDog")
    Call<Dog> removeDog(@Header("key") String key);

    @POST("updateDog")
    Call<Dog> updateDog(@Body Dog doggo);

    @POST("addDog")
    Call<Dog> addDog(@Body Dog doggo);
}