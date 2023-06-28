package it.polimi.ingsw.observer;

/**
 * The ObserverModel interface represents an observer in the observer pattern for models
 *
 * @param <SubjectType> the type of the observable subject
 * @param <Message> the type of the message passed from the subject to the observer
 */
public interface ObserverModel<SubjectType extends ObservableModel<Message>, Message> {
    /**
     * Updates the observer with the latest message from the observable subject
     *
     * @param o the observable subject
     * @param arg the message argument passed from the subject
     */
    void updateS(SubjectType o, Message arg);
}
