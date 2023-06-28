package it.polimi.ingsw.observer;

import java.util.Vector;

/**
 * The ObservableModel class represents an observable model in the observer pattern
 *
 * @param <Message> the type of the message sent to the observers
 */
public class ObservableModel<Message> {
    private boolean changed = false;
    private Vector<ObserverModel<? extends ObservableModel<Message>, Message>> observers;

    /**
     * Constructs a new ObservableModel
     */
    public ObservableModel() {
        observers = new Vector<>();
    }

    /**
     * Adds an observer to the observable model
     *
     * @param o the observer to add
     */
    public synchronized void addObserverModel(ObserverModel<? extends ObservableModel<Message>, Message> o) {
        if (o == null)
            throw new NullPointerException();
        if (!observers.contains(o)) {
            observers.addElement(o);
        }
    }

    /**
     * Deletes an observer from the observable model
     *
     * @param o the observer to delete
     */
    public synchronized void deleteObserverModel(ObserverModel<? extends ObservableModel<Message>, Message> o) {
        observers.removeElement(o);
    }

    /**
     * Notifies all observers without passing any message
     */
    public void notifyObserversModel() {
        notifyObserversModel(null);
    }

    /**
     * Notifies all observers with the specified message
     *
     * @param arg the message to pass to the observers
     */
    public void notifyObserversModel(Message arg) {
        Object[] arrLocal;

        synchronized (this) {
            if (!changed)
                return;

            arrLocal = observers.toArray();
            clearChangedModel();
        }

        for (int i = arrLocal.length-1; i>=0; i--) {
            //System.out.println("Notifico observer client " + i + " = " + ((ObserverModel<ObservableModel<Message>, Message>) arrLocal[i]));
            ((ObserverModel<ObservableModel<Message>, Message>) arrLocal[i]).updateS(this, arg);
        }
        //System.out.println("\n\n");
    }

    /**
     * Sets the changed flag to indicate that this observable model has changed
     */
    protected synchronized void setChangedModel() {
        changed = true;
    }

    /**
     * Checks if this observable model has changed
     *
     * @return true if the model has changed, false otherwise
     */
    public synchronized boolean hasChangedModel() {
        return changed;
    }

    /**
     * Clears the changed flag to indicate that this observable model has no longer changed
     */
    protected synchronized void clearChangedModel() {
        changed = false;
    }
}
