package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * The AbstractEntity class provides a standard implementation
 * of the Entity interface, using a single Rectangle as its
 * hit box, and rendering a white rectangle on the screen'
 * in the render method.
 */
public abstract class AbstractEntity implements Entity {
    private Rectangle rect;
    private boolean expired;

    //public entities.AbstractEntity() { this(0, 0, 0, 0); }

    /**
     * Basic Constructor
     * @param x x-coordinate of the entity's position.
     * @param y y-coordinate of the entity's position.
     * @param width width of the entity
     * @param height height of the entity
     */
    public AbstractEntity(float x, float y, float width, float height) {
        rect = new Rectangle(x, y, width, height);
        expired = false;
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Object renderer_) {
        if (expired()) return;
        ShapeRenderer renderer = (ShapeRenderer) renderer_;
        renderer.setColor(Color.WHITE);
        renderer.rect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public boolean rendersWithShapeRenderer() {
        return true;
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
        if (other instanceof AbstractEntity) {
            checkCollisionInternal((AbstractEntity) other);
        } else {
            other.checkCollision(this);
        }
    }

    /**
     * Calls Collisions.collided(other, this) if this
     * rectangle overlaps with the other's rectangle.
     * @param other The entity this one is being checked against.
     */
    private void checkCollisionInternal(AbstractEntity other) {
        if (!this.expired && !other.expired && this.rect.overlaps(other.rect)) {
            Collisions.collided(this, other);
        }
    }

    @Override
    public boolean expired() {
        return expired;
    }

    public Rectangle getRect() { return rect; }
    public float getX() { return rect.getX(); }
    public float getY() { return rect.getY(); }
    public float getWidth()  { return rect.getWidth();  }
    public float getHeight() { return rect.getHeight(); }
    
    //public void setRect(Rectangle rect) { this.rect = rect; }
    //public void setX(float x) { rect.setX(x); }
    //public void setY(float y) { rect.setY(y); }
    //public void setWidth (float width)  { rect.setWidth (width);  }
    //public void setHeight(float height) { rect.setHeight(height); }
    public void setExpired(boolean expired) { this.expired = expired; }
    
    public void addX(float x) { rect.setX(rect.getX() + x); }
    public void addY(float y) { rect.setY(rect.getY() + y); }
    //public void addWidth (float width)  { rect.setWidth (rect.getWidth()  + width);  }
    //public void addHeight(float height) { rect.setHeight(rect.getHeight() + height); }

    public void subX(float x) { rect.setX(rect.getX() - x); }
    public void subY(float y) { rect.setY(rect.getY() - y); }
    //public void subWidth (float width)  { rect.setWidth (rect.getWidth()  - width);  }
    //public void subHeight(float height) { rect.setHeight(rect.getHeight() - height); }
}
