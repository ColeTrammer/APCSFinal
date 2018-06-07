package engine.entities;

import engine.entities.behaviors.Movable;
import engine.entities.components.MovementComponent;
import engine.entities.components.Rectangle;

@SuppressWarnings("unused")
public class EntityBlocker extends Wall {
    private final Class<? extends Entity> cls;

    public EntityBlocker(Rectangle rect, MovementComponent movementComponent, Class<? extends Entity> cls) {
        super(rect, movementComponent);
        this.cls = cls;
    }

    @Override
    public void expel(Movable movable) {
        if (cls.isInstance(movable)) {
            super.expel(movable);
        }
    }

    @Override
    public void render(Object renderTool) {
    }
}
