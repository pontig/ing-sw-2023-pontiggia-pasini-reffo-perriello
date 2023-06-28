package it.polimi.ingsw.observer;

import java.util.Vector;

public class ObservableModel<Message> {
    private boolean changed = false;
    private Vector<ObserverModel<? extends ObservableModel<Message>, Message>> observers;

    public ObservableModel() {
        observers = new Vector<>();
    }

    public synchronized void addObserverModel(ObserverModel<? extends ObservableModel<Message>, Message> o) {
        if (o == null)
            throw new NullPointerException();
        if (!observers.contains(o)) {
            observers.addElement(o);
        }
    }

    public synchronized void deleteObserverModel(ObserverModel<? extends ObservableModel<Message>, Message> o) {
        observers.removeElement(o);
    }

    public void notifyObserversModel() {
        notifyObserversModel(null);
    }

    public void notifyObserversModel(Message arg) {
        Object[] arrLocal;

        synchronized (this) {
            if (!changed)
                return;
            arrLocal = observers.toArray();
            clearChangedModel();
        }

        for (int i = arrLocal.length-1; i>=0; i--) {
            System.out.println("Notifico observer client " + i + " = " + ((ObserverModel<ObservableModel<Message>, Message>) arrLocal[i]));
            ((ObserverModel<ObservableModel<Message>, Message>) arrLocal[i]).updateS(this, arg);
        }
        System.out.println("\n\n");
    }

    protected synchronized void setChangedModel() {
        changed = true;
    }
    public synchronized boolean hasChangedModel() {
        return changed;
    }
    protected synchronized void clearChangedModel() {
        changed = false;
    }
}
