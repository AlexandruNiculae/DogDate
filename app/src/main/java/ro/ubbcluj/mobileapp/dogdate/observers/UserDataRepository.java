package ro.ubbcluj.mobileapp.dogdate.observers;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.ubbcluj.mobileapp.dogdate.Utils.ApiUtils;
import ro.ubbcluj.mobileapp.dogdate.domain.Dog;
import ro.ubbcluj.mobileapp.dogdate.service.UserService;

/**
 * Created by elega on 2018-01-13.
 */

public class UserDataRepository implements Subject {

    private ArrayList<Dog> doggoList;
    private static UserDataRepository INSTANCE = null;

    private ArrayList<RepositoryObserver> mObservers;

    private UserDataRepository() {
        mObservers = new ArrayList<>();
        getNewDataFromRemote();
    }


    //Create a singletone of the class
    public static UserDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDataRepository();
        }
        return INSTANCE;
    }

    @Override
    public void registerObserver(RepositoryObserver repositoryObserver) {
        if (!mObservers.contains(repositoryObserver))
            mObservers.add(repositoryObserver);
    }

    @Override
    public void removeObserver(RepositoryObserver repositoryObserver) {
        if (mObservers.contains(repositoryObserver))
            mObservers.remove(repositoryObserver);
    }

    @Override
    public void notifyObservers() {
        for (RepositoryObserver observer : mObservers) {
            observer.onUserDataChanged(doggoList);
        }
    }

    private void getNewDataFromRemote() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UserService userService = ApiUtils.getUserService();
                Call<JsonArray> call = userService.getAllDogs();
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        //
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Dog>>(){}.getType();
                        ArrayList<Dog> doggos = gson.fromJson(response.body(), type);
                        setUserData(doggos);
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {

                    }
                });
            }
        }, 5000);
    }

    public void updateDog(Dog doggo) {
        for (int i = 0; i < doggoList.size(); ++i) {
            if (doggoList.get(i).getKey() == doggo.getKey()) {
                doggoList.remove(i);
                doggoList.add(i, doggo);
                notifyObservers();
                break;
            }
        }
    }

    @Override
    public void removeDog(Dog doggo) {
        for (int i = 0; i < doggoList.size(); ++i) {
            if (doggoList.get(i).getKey() == doggo.getKey()) {
                doggoList.remove(i);
                break;
            }
        }
        for (int i = 0; i < doggoList.size(); ++i) {
            doggoList.get(i).setKey(i + 1);
        }
        notifyObservers();
    }

    @Override
    public void addDog(Dog doggo) {
        doggo.setKey(doggoList.size() + 1);
        doggoList.add(doggo);
        notifyObservers();
    }

    public void setUserData(ArrayList<Dog> doggoList) {
        this.doggoList = doggoList;
        notifyObservers();
    }
}
