package ro.ubbcluj.mobileapp.dogdate.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;

/**
 * Created by elega on 2017-12-21.
 */
@Database(entities = {Dog.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DogsDAO dogsDAO();
}
