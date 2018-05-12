package logic;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import utils.Constants;

public class Player extends GameObject {
    private Vector2 velocity;
    private boolean alive;

    public Player() {
        super(Constants.WORLD_CENTER.x, Constants.WORLD_CENTER.y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        velocity = new Vector2();
        alive = true;
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
        if (!alive) return;
        ShapeRenderer renderer = (ShapeRenderer) renderer_;
        renderer.setColor(Color.BLUE);
        renderer.rect(getX(), getY(), getWidth(), getHeight());
    }

    public void damage() {
        alive = false;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
