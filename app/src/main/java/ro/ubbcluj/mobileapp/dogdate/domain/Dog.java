package ro.ubbcluj.mobileapp.dogdate.domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by elega on 2017-11-18.
 */


@Entity(tableName = "dogs")
public class Dog implements Serializable {

    @SerializedName("key")
    @PrimaryKey(autoGenerate = true)
    public int key;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("race")
    @ColumnInfo(name = "race")
    private String race;

    @SerializedName("personality")
    @ColumnInfo(name = "personality")
    private String personality;

    @SerializedName("age")
    @ColumnInfo(name = "age")
    private int age;

    @Ignore
    public Dog(String _name, String _race, String _personality, int _age){
        name = _name;
        race = _race;
        personality = _personality;
        age = _age;
    }

    public Dog(int _key, String _name, String _race, String _personality, int _age){
        this(_name,_race,_personality,_age);
        setKey(_key);
    }

    public Dog(){
        key = -1;
        name = "";
        race = "";
        personality = "";
        age = 0;
    }

    public String getName(){
        return name;
    }
    public void setName(String _name) {name = _name;}

    public String getRace(){
        return race;
    }
    public void setRace(String _race) {race = _race;}

    public String getPersonality(){
        return personality;
    }
    public void setPersonality(String _personality) {personality = _personality;}

    public int getAge(){
        return age;
    }
    public void setAge(int _age) {age = _age;}

    public int getKey() {
        return this.key;
    }
    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public String toString(){
        return race+": "+name+" "+age+" "+personality;
    }

    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Dog))return false;
        Dog otherDog = (Dog)other;
        if(key != otherDog.key)
            return  false;
        return name.equals(otherDog.getName());
    }

}
