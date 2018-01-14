package ro.ubbcluj.mobileapp.dogdate.observers;

import java.util.ArrayList;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;

/**
 * Created by elega on 2018-01-13.
 */

public interface RepositoryObserver {
    void onUserDataChanged(ArrayList<Dog> places);
}
