package entities;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import utils.Constants;

/**
 * Entity that represents the player of the game. As such,
 * it's motion is controlled by user input, and it has the
 * highest amount of unique interactions with other Entities.
 */
public class Player extends MovableRectangleEntity {
    /**
     * Basic Constructor
     */
    public Player() {
        super(Constants.WORLD_CENTER.x, Constants.WORLD_CENTER.y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
    }

    @Override
    public void update(float delta) {
        // resets velocity to zero each frame.
        setVelocity(0, 0);

        // changes velocity based on keyboard input.
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

        // applies the velocity to position.
        super.update(delta);
    }

    @Override
    public void render(Object renderer_) {
        ShapeRenderer renderer = (ShapeRenderer) renderer_;
        renderer.setColor(Color.BLUE);
        renderer.rect(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Allows for objects the Player collides with to
     * affect the player's life. Currently, it will
     * always kill the player, but a health system
     * could be used instead.
     */
    public void damage() {
        expire();
    }
}
