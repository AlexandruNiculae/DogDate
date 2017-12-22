package ro.ubbcluj.mobileapp.dogdate.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;

/**
 * Created by elega on 2017-12-21.
 */

@Dao
public interface DogsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDogs(Dog... dogs);

    @Insert
    public void insertDog(Dog dog);

    @Update
    public void updateDogs(Dog... dogs);

    @Query("DELETE FROM dogs WHERE key = :key")
    public void deleteDog(int key);

    @Delete
    public void deleteDogs(Dog... dogs);

    @Query("SELECT * FROM dogs")
    public Dog[] getAllDogs();
}
