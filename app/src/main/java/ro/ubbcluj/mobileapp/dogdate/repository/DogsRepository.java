package ro.ubbcluj.mobileapp.dogdate.repository;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;

/**
 * Created by elega on 2017-12-21.
 */

public class DogsRepository {

    private DogsDAO dogsDAO;
    private AppDatabase appdb;

    public DogsRepository(Context context){
        appdb = Room.databaseBuilder(context,
                AppDatabase.class, "dogdate-db").allowMainThreadQueries()
                .build();

        dogsDAO = appdb.dogsDAO();
    }

    public ArrayList<Dog> getAllDogs(){
        ArrayList<Dog> doggos = new ArrayList<>();
        Dog[] dogsFromDB = dogsDAO.getAllDogs();
        for (Dog doggo: dogsFromDB)
            doggos.add(doggo);
        return doggos;
    }
}
