package engine.entities;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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
     * Constructs the Player with the given initial position,
     * dimensions, speed of motion, jump height, and force of
     * gravity.
     * @param x x-coordinate of the entity's position.
     * @param y y-coordinate of the entity's position.
     * @param width width of the entity.
     * @param height height of the entity.
     * @param xSpeed Speed of the player's horizontal motion.
     * @param jumpHeight Max height of the player's jump.
     * @param gravity Strength of gravity acting on the player.
     */
    public Player(float x, float y, float width, float height, float xSpeed, float jumpHeight, float gravity) {
        super(x, y, width, height, 0, 0, 0, -gravity);
        this.xSpeed = xSpeed;
        // basic kinematics equation (v^2 = v_0^2 + 2 * a * x) with v_0 = 0
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

    @Override
    public void moveOutOf(Vector2 displacement) {
        super.moveOutOf(displacement);
        if (displacement.y > 0) {
            setVelocityY(0);
        }
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
