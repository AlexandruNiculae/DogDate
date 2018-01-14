package ro.ubbcluj.mobileapp.dogdate.observers;

import ro.ubbcluj.mobileapp.dogdate.domain.Dog;

/**
 * Created by elega on 2018-01-13.
 */

public interface Subject {
    void registerObserver(RepositoryObserver repositoryObserver);
    void removeObserver(RepositoryObserver repositoryObserver);
    void notifyObservers();
    void updateDog(Dog doggo);
    void removeDog(Dog doggo);
    void addDog(Dog doggo);
}
