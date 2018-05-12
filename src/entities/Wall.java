package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Wall extends AbstractEntity {
    public Wall(float x, float y, float width, float height) {
        super(x, y, width, height);
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
}
