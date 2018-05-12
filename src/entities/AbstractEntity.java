package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractEntity implements Entity {
    private Rectangle rect;
    private boolean expired;

    //public entities.AbstractEntity() { this(0, 0, 0, 0); }

    public AbstractEntity(float x, float y, float width, float height) {
        rect = new Rectangle(x, y, width, height);
        expired = false;
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Object renderer_) {
        ShapeRenderer renderer = (ShapeRenderer) renderer_;
        renderer.setColor(Color.WHITE);
        renderer.rect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public boolean rendersWithShapeRenderer() {
        return true;
    }

    @Override
    public boolean overlapsWith(Entity other) {
        if (other instanceof AbstractEntity) {
            return rect.overlaps(((AbstractEntity) other).getRect());
        } else {
            return other.overlapsWith(this);
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
