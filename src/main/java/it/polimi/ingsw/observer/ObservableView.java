package it.polimi.ingsw.observer;

import java.util.Vector;

/**
 * The ObservableView class represents an observable view in the observer pattern
 *
 * @param <Message> the type of the message sent to the observers
 */
public class ObservableView<Message> {
    private boolean changed = false;
    private Vector<ObserverView<? extends ObservableView<Message>, Message>> observers;

    /**
     * Constructs a new ObservableView
     */
    public ObservableView() {
        observers = new Vector<>();
    }

    /**
     * Adds an observer to the observable view
     *
     * @param o the observer to add
     */
    public synchronized void addObserverView(ObserverView<? extends ObservableView<Message>, Message> o) {
        if (o == null)
            throw new NullPointerException();
        if (!observers.contains(o)) {
            observers.addElement(o);
        }
    }

    /**
     * Deletes an observer from the observable view
     *
     * @param o the observer to delete
     */
    public synchronized void deleteObserverView(ObserverView<? extends ObservableView<Message>, Message> o) {
        observers.removeElement(o);
    }

    /**
     * Notifies all observers without passing any message
     */
    public void notifyObserversView() {
        notifyObserversView(null);
    }

    /**
     * Notifies all observers with the specified message
     *
     * @param arg the message to pass to the observers
     */
    public void notifyObserversView(Message arg) {
        Object[] arrLocal;

        synchronized (this) {
            if (!changed)
                return;
            arrLocal = observers.toArray();
            clearChangedView();
        }

        //System.out.println("Sono nell'observer");
        //for (int i = arrLocal.length - 1; i >= 0; i--)
        //    System.out.println((ObserverView<ObservableView<Message>, Message>) arrLocal[i]);
        //System.out.println("Fine");
        for (int i = arrLocal.length - 1; i >= 0; i--)
            ((ObserverView<ObservableView<Message>, Message>) arrLocal[i]).updateC(this, arg);

    }

    /**
     * Sets the changed flag to indicate that this observable view has changed
     */
    public synchronized void setChangedView() {
        changed = true;
    }

    /**
     * Checks if this observable view has changed
     *
     * @return true if the view has changed, false otherwise
     */
    public synchronized boolean hasChangedView() {
        return changed;
    }

    /**
     * Clears the changed flag to indicate that this observable view has no longer changed
     */
    protected synchronized void clearChangedView() {
        changed = false;
    }

    /**
     * Returns the list of observers of this observable view
     *
     * @return the list of observers
     */
    public Vector<ObserverView<? extends ObservableView<Message>, Message>> getObservers() {
        return observers;
    }
}
