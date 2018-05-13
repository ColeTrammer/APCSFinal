package entities;

import com.badlogic.gdx.utils.Array;

/**
 * The AbstractEntity class provides a standard implementation
 * of the observer aspects of the Entity interface, and is as such
 * completely independent of any design choices used to model
 * an actual Entity. It simply provides a framework for
 * all entities to expire, and nothing else.
 */
public abstract class AbstractEntity implements Entity {
    private Array<EntityObserver> observers;

    public AbstractEntity() {
        observers = new Array<>();
    }

    @Override
    public void addObserver(EntityObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(EntityObserver o) {
        observers.removeValue(o, true);
    }

    @Override
    public void expire() {
        for (EntityObserver o : observers) {
            o.expire(this);
        }
    }
}
