package it.polimi.ingsw.observer;

/**
 * The ObserverView interface represents an observer in the observer pattern for views
 *
 * @param <SubjectType> the type of the observable subject
 * @param <Message> the type of the message passed from the subject to the observer
 */
public interface ObserverView<SubjectType extends ObservableView<Message>, Message> {
    /**
     * Updates the observer with the latest message from the observable subject
     *
     * @param o the observable subject
     * @param arg the message argument passed from the subject
     */
    void updateC(SubjectType o, Message arg);
}
