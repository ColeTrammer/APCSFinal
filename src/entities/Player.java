package entities;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import utils.Constants;

public class Player extends MovingEntity {
    public Player() {
        super(Constants.WORLD_CENTER.x, Constants.WORLD_CENTER.y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
    }

    @Override
    public void update(float delta) {
        setVelocity(0, 0);

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            subVelocityX(Constants.PLAYER_SPEED);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            addVelocityX(Constants.PLAYER_SPEED);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            subVelocityY(Constants.PLAYER_SPEED);
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            addVelocityY(Constants.PLAYER_SPEED);
        }

        super.update(delta);
    }

    @Override
    public void render(Object renderer_) {
        ShapeRenderer renderer = (ShapeRenderer) renderer_;
        renderer.setColor(Color.BLUE);
        renderer.rect(getX(), getY(), getWidth(), getHeight());
    }

    public void damage() {
        setExpired(true);
    }
}
