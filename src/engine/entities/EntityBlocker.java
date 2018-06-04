package engine.entities;

import engine.entities.behaviors.Movable;

public class EntityBlocker extends Wall {
    private final Class<? extends Entity> cls;

    public EntityBlocker(float x, float y, float width, float height, Class<? extends Entity> cls) {
        super(x, y, width, height);
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
