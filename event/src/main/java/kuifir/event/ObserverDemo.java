package kuifir.event;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Observable;
import java.util.Observer;

/**
 * Package: kuifir.event
 * <p>
 * Description： {@link java.util.Observer}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/11 22:02
 * <p>
 * Version: 0.0.1
 */
public class ObserverDemo {
    public static void main(String[] args) {
        EventObservable eventObservable = new EventObservable();
        eventObservable.addObserver(new EventObserver());
        eventObservable.notifyObservers("hello world");

    }

    static class EventObservable extends Observable{
        @Override
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(new EventObject(arg));
            clearChanged();
        }

        @Override
        protected synchronized void setChanged() {
            super.setChanged();
        }
    }

    static class EventObserver implements Observer, EventListener {
        @Override
        public void update(Observable o, Object event) {
            EventObject eventObject = (EventObject) event;
            System.out.println(eventObject);
        }
    }
}
