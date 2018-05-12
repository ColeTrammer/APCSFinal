package logic;

import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    private Rectangle rect;
    
    //public logic.GameObject() { this(0, 0, 0, 0); }
     
    public GameObject(float x, float y, float width, float height) {
        rect = new Rectangle(x, y, width, height);
    }
    
    public abstract void update(float delta);
    public abstract void render(Object renderer_);
    
    public boolean rendersWithShapeRenderer() {
        return true; //default
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
    
    public void addX(float x) { rect.setX(rect.getX() + x); }
    public void addY(float y) { rect.setY(rect.getY() + y); }
    //public void addWidth (float width)  { rect.setWidth (rect.getWidth()  + width);  }
    //public void addHeight(float height) { rect.setHeight(rect.getHeight() + height); }

    public void subX(float x) { rect.setX(rect.getX() - x); }
    public void subY(float y) { rect.setY(rect.getY() - y); }
    //public void subWidth (float width)  { rect.setWidth (rect.getWidth()  - width);  }
    //public void subHeight(float height) { rect.setHeight(rect.getHeight() - height); }
}
