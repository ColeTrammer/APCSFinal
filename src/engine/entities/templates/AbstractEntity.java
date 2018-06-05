package engine.entities.templates;

import com.badlogic.gdx.utils.Array;
import engine.entities.Entity;
import engine.utils.EntityObserver;

/**
 * The AbstractEntity class provides a standard implementation
 * of the observer aspects of the Entity interface, and is as such
 * completely independent of any design choices used to model
 * an actual Entity. It simply provides a framework for
 * all entities to expire, and nothing else.
 */
public abstract class AbstractEntity implements Entity {
    private final Array<EntityObserver> observers;
    private float delta;

    protected AbstractEntity() {
        observers = new Array<>();
    }

    @Override
    public void addObserver(EntityObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(EntityObserver observer) {
        observers.removeValue(observer, true);
    }

    @Override
    public void update(float delta) {
        this.delta = delta;
    }

    @Override
    public void expire() {
        for (EntityObserver observer : observers) {
            observer.expire(this);
        }
    }

    @Override
    public void spawn(Entity entity) {
        for (EntityObserver observer : observers) {
            observer.spawn(entity);
        }
    }

    protected float getDeltaTime() { return delta; }
}
