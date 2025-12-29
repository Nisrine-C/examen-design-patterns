package com.agents.observer;

import com.agents.model.Evenement;

public interface Observable {
    void addObserver(Observer obs);
    void removeObserver(Observer obs);
    void notifyObservers(Evenement e);
}
