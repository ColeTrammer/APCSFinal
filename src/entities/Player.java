package entities;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import utils.Constants;

public class Player extends AbstractEntity {
    private Vector2 velocity;

    public Player() {
        super(Constants.WORLD_CENTER.x, Constants.WORLD_CENTER.y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        velocity = new Vector2();
    }

    @Override
    public void update(float delta) {
        velocity.x = 0;
        velocity.y = 0;

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            velocity.x -= delta * Constants.PLAYER_SPEED;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            velocity.x += delta * Constants.PLAYER_SPEED;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            velocity.y -= delta * Constants.PLAYER_SPEED;
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            velocity.y += delta * Constants.PLAYER_SPEED;
        }

        addX(velocity.x);
        addY(velocity.y);
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

    public Vector2 getVelocity() {
        return velocity;
    }
}
