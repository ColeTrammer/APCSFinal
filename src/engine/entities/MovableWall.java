package engine.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import engine.entities.behaviors.Impassable;
import engine.entities.behaviors.Movable;
import engine.entities.templates.MovableRectangleEntity;
import engine.utils.Collisions;

@SuppressWarnings("unused")
public class MovableWall extends MovableRectangleEntity implements Impassable {
    private boolean firstUpdateCall;
    private final float boundX;
    private final float boundY;
    private final float boundWidth;
    private final float boundHeight;

    public MovableWall(float x, float y, float width, float height, float velX, float velY, float boundX, float boundY, float boundWidth, float boundHeight) {
        super(x, y, width, height, velX, velY);
        this.firstUpdateCall = true;
        this.boundX = boundX;
        this.boundY = boundY;
        this.boundWidth = boundWidth;
        this.boundHeight = boundHeight;
    }

    @Override
    public void update(float delta) {
        if (firstUpdateCall) {
            spawn(new EntityBlocker(boundX - getWidth(), boundY - getHeight(), getWidth(), boundHeight + 2 * getHeight(), getClass()));
            spawn(new EntityBlocker(boundX, boundY - getHeight(), boundWidth, getHeight(), getClass()));
            spawn(new EntityBlocker(boundX, boundY + boundHeight, boundWidth, getHeight(), getClass()));
            spawn(new EntityBlocker(boundX + boundWidth, boundY - getHeight(), getWidth(), boundHeight + 2 * getHeight(), getClass()));
            firstUpdateCall = false;
        }
        super.update(delta);
    }

    @Override
    public void render(Object rendererTool) {
        ShapeRenderer renderer = (ShapeRenderer) rendererTool;
        renderer.setColor(Color.BLACK);
        super.render(rendererTool);
    }

    @Override
    public void moveOutOf(Vector2 displacement) {
        if (Math.abs(displacement.x) >= Math.abs(displacement.y)) {
            setVelocityX(-getVelocityX());
        } else {
            setVelocityY(-getVelocityY());
        }
        super.moveOutOf(displacement);
    }

    /**
     * Moves the overlapping entity out of
     * the impassable one.
     *
     * @param movable The entity to expel.
     */
    @Override
    public void expel(Movable movable) {
        if (movable instanceof MovableRectangleEntity) {
            movable.moveOutOf(Collisions.expelDistance((MovableRectangleEntity) movable, this));
        }
    }
}
