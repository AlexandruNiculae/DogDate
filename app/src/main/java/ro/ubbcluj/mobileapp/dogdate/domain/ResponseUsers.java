package ro.ubbcluj.mobileapp.dogdate.domain;

import java.util.ArrayList;

/**
 * Created by elega on 2018-01-13.
 */

public class ResponseUsers {

    private ArrayList<User> users;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<User> getData() {
        return users;
    }
}
