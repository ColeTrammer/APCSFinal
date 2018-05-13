package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy extends AbstractEntity {
    public Enemy(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void render(Object renderer_) {
        ShapeRenderer renderer = (ShapeRenderer) renderer_;
        renderer.setColor(Color.RED);
        renderer.rect(getX(), getY(), getWidth(), getHeight());
    }
}
