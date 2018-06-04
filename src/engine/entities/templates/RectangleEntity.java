package engine.entities.templates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import engine.utils.Collisions;
import engine.entities.Entity;

/**
 * The RectangleEntity class provides a standard implementation
 * of the Entity interface, by extending the AbstractEntity class
 * and using a single Rectangle as its
 * hit box, and rendering a white rectangle on the screen'
 * in the render method.
 */
public abstract class RectangleEntity extends AbstractEntity {
    private final Rectangle rect;

    /**
     * Basic Constructor
     *
     * @param x      x-coordinate of the entity's position.
     * @param y      y-coordinate of the entity's position.
     * @param width  width of the entity.
     * @param height height of the entity.
     */
    protected RectangleEntity(float x, float y, float width, float height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Width and height parameters must be positive.");
        }
        rect = new Rectangle(x, y, width, height);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Object rendererTool) {
        ShapeRenderer renderer = (ShapeRenderer) rendererTool;
        renderer.rect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public RenderTool getRenderTool() {
        return RenderTool.SHAPE_RENDERER;
    }

    @Override
    public void checkCollision(Entity other) {
        /*
        if the entity is of this class
        then check if the rectangles overlap
        else its on the other class to deal with it
        For example, a hypothetical entity with multiple bounding
        rectangles or a polygon would need to implement
        the case where it collides with another one of its own
        and the other when it collides with this simple rectangle only entity
        */
        if (other instanceof RectangleEntity) {
            checkCollisionInternal((RectangleEntity) other);
        } else {
            other.checkCollision(this);
        }
    }

    /**
     * Calls Collisions.collided(this, other) if this
     * rectangle overlaps with the other's rectangle.
     *
     * @param other The entity this one is being checked against.
     */
    private void checkCollisionInternal(RectangleEntity other) {
        if (this.rect.overlaps(other.rect)) {
            Collisions.collided(this, other);
        }
    }

    //public Rectangle getRect() { return rect; }
    public float getX() {
        return rect.getX();
    }

    public float getY() {
        return rect.getY();
    }

    public float getWidth() {
        return rect.getWidth();
    }

    public float getHeight() {
        return rect.getHeight();
    }

    //public void setRect(Rectangle rect) { this.rect = rect; }
    //public void setX(float x) { rect.setX(x); }
    //public void setY(float y) { rect.setY(y); }
    protected void setWidth(float width) {
        rect.setWidth(width);
    }

    protected void setHeight(float height) {
        rect.setHeight(height);
    }

    protected void addX(float x) {
        rect.setX(rect.getX() + x);
    }

    protected void addY(float y) {
        rect.setY(rect.getY() + y);
    }

    protected void addWidth(float width) {
        rect.setWidth(rect.getWidth() + width);
    }

    protected void addHeight(float height) {
        rect.setHeight(rect.getHeight() + height);
    }

    protected void subX(float x) {
        rect.setX(rect.getX() - x);
    }

    protected void subY(float y) {
        rect.setY(rect.getY() - y);
    }

    protected void subWidth(float width) {
        rect.setWidth(rect.getWidth() - width);
    }

    protected void subHeight(float height) {
        rect.setHeight(rect.getHeight() - height);
    }
}
