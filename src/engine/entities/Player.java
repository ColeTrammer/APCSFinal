package engine.entities;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import engine.entities.templates.AcceleratingRectangleEntity;

/**
 * Entity that represents the player of the game. As such,
 * it's motion is controlled by user input, and it has the
 * highest amount of unique interactions with other Entities.
 */
public class Player extends AcceleratingRectangleEntity {
    private float xSpeed;
    private float ySpeed;

    /**
     * Basic Constructor
     */
    public Player(float x, float y, float width, float height, float xSpeed, float jumpHeight, float gravity) {
        super(x, y, width, height, 0, 0, 0, -gravity);
        this.xSpeed = xSpeed;
        this.ySpeed = (float) Math.sqrt(2 * gravity * jumpHeight);
    }

    @Override
    public void update(float delta) {
        // resets x-velocity to zero each frame.
        setVelocityX(0);

        // changes velocity based on keyboard input.
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            subVelocityX(xSpeed);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            addVelocityX(xSpeed);
        }
        if (Gdx.input.isKeyPressed(Keys.SPACE) && getVelocityY() == 0) {
            setVelocityY(ySpeed);
        }

        // applies the acceleration to the velocity to the position.
        super.update(delta);
    }

    @Override
    public void render(Object rendererTool) {
        ShapeRenderer renderer = (ShapeRenderer) rendererTool;
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
