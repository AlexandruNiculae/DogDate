package ro.ubbcluj.mobileapp.dogdate.domain;

import java.io.Serializable;

/**
 * Created by elega on 2017-11-18.
 */

public class Dog implements Serializable {

    private String name;
    private String race;
    private String personality;
    private int age;

    public Dog(String _name, String _race, String _personality, int _age){
        name = _name;
        race = _race;
        personality = _personality;
        age = _age;
    }

    public String getName(){
        return name;
    }

    public String getRace(){
        return race;
    }

    public String getPersonality(){
        return personality;
    }

    public int getAge(){
        return age;
    }

    @Override
    public String toString(){
        return race+": "+name+" "+age+" "+personality;
    }

}
