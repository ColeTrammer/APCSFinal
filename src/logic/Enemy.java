package logic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy extends GameObject {
    public Enemy(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Object renderer_) {
        ShapeRenderer renderer = (ShapeRenderer) renderer_;
        renderer.setColor(Color.RED);
        renderer.rect(getX(), getY(), getWidth(), getHeight());
    }
}
