package it.polimi.ingsw.observer;

import jdk.jfr.Event;

public interface ObserverModel<SubjectType extends ObservableModel<Message>, Message> {
    void updateS(SubjectType o, Message arg);
}
