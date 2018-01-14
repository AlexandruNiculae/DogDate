package ro.ubbcluj.mobileapp.dogdate.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by elega on 2018-01-13.
 */

public class User implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("type")
    private String type;

    public User(int id, String name, String password, String email, String type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
