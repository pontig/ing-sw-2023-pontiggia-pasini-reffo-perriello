package it.polimi.ingsw.observer;

public interface ObserverView<SubjectType extends ObservableView<Message>, Message> {
    void updateC(SubjectType o, Message arg);
}
