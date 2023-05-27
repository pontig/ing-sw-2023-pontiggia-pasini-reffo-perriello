package it.polimi.ingsw.observer;

import java.util.Vector;

public class ObservableView<Message> {
    private boolean changed = false;
    private Vector<ObserverView<? extends ObservableView<Message>, Message>> observers;

    public ObservableView() {
        observers = new Vector<>();
    }

    public synchronized void addObserverView(ObserverView<? extends ObservableView<Message>, Message> o) {
        if (o == null)
            throw new NullPointerException();
        if (!observers.contains(o)) {
            observers.addElement(o);
        }
    }

    public synchronized void deleteObserverView(ObserverView<? extends ObservableView<Message>, Message> o) {
        observers.removeElement(o);
    }

    public void notifyObserversView() {
        notifyObserversView(null);
    }

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

    public synchronized void setChangedView() {
        changed = true;
    }

    public synchronized boolean hasChangedView() {
        return changed;
    }

    protected synchronized void clearChangedView() {
        changed = false;
    }

    public Vector<ObserverView<? extends ObservableView<Message>, Message>> getObservers() {
        return observers;
    }
}
