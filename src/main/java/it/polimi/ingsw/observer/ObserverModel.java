package it.polimi.ingsw.observer;


public interface ObserverModel<SubjectType extends ObservableModel<Message>, Message> {
    void updateS(SubjectType o, Message arg);
}
