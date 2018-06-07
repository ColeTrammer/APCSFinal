package engine.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import engine.entities.behaviors.Impassable;
import engine.entities.behaviors.Movable;
import engine.entities.components.MovementComponent;
import engine.entities.components.Rectangle;
import engine.entities.templates.MovableRectangleEntity;
import engine.entities.templates.RectangleEntity;
import engine.utils.Collisions;

/**
 * An entity that other entities cannot move through. It
 * is completely static and unchanging, only interacting
 * with other entities when in a collision.
 */
public class Wall extends MovableRectangleEntity implements Impassable {
    /**
     * Basic Constructor
     *
     */
    @SuppressWarnings("WeakerAccess")
    public Wall(Rectangle rect, MovementComponent movementComponent) {
        super(rect, movementComponent);
    }

    @Override
    public void render(Object renderTool) {
        ShapeRenderer renderer = (ShapeRenderer) renderTool;
        renderer.setColor(Color.BLACK);
        super.render(renderer);
    }

    @Override
    public void expel(Movable movable) {
        if (movable instanceof RectangleEntity) {
            movable.moveOutOf(Collisions.expelDistance((RectangleEntity) movable, this));
        }
    }
}
