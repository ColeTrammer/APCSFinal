package engine.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import engine.entities.behaviors.Afflictable;
import engine.entities.behaviors.InputReceiver;
import engine.entities.components.Acceleration;
import engine.entities.components.Rectangle;
import engine.entities.templates.MovableRectangleEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Entity that represents the player of the game. As such,
 * it's motion is controlled by user input, and it has the
 * highest amount of unique interactions with other Entities.
 */
@SuppressWarnings("unused")
public class Player extends MovableRectangleEntity implements Afflictable, InputReceiver {
    private final float xSpeed;
    private final float ySpeed;
    private boolean hitWall;
    private boolean jumping;
    private boolean ducking;
    private Map<Integer, Boolean> keysPressed;

    /**
     * Constructs the Player with the given initial position,
     * dimensions, speed of motion, jump height, and force of
     * gravity.
     *
     * @param xSpeed     Speed of the player's horizontal motion.
     * @param jumpHeight Max height of the player's jump.
     * @param gravity    Strength of gravity acting on the player.
     */
    public Player(Rectangle rect, float xSpeed, float jumpHeight, float gravity) {
        super(rect, new Acceleration(0, 0, 0, -gravity));
        this.xSpeed = xSpeed;
        // basic kinematics equation (v^2 = v_0^2 + 2 * a * x) with v_0 = 0
        this.ySpeed = (float) Math.sqrt(2 * gravity * jumpHeight);
        initKeysPressed();
    }

    @Override
    public void update(float delta) {
        // resets x-velocity to zero each frame.
        getVelocity().x = 0;

        // changes velocity based on keyboard input.
        if (keysPressed.get(Keys.LEFT) || keysPressed.get(Keys.A)) {
            getVelocity().x -= xSpeed;
        }
        if (keysPressed.get(Keys.RIGHT) || keysPressed.get(Keys.D)) {
            getVelocity().x += xSpeed;
        }
        /*
        This method of determining jump-ability is flawed. The player has 0 y-velocity
        at the height of its jump. However, floating point math means that it is unlikely
        that it will actually equal zero due to acceleration. Therefore, it will stay in
        as a FEATURE.
        */
        if ((keysPressed.get(Keys.SPACE) || keysPressed.get(Keys.W) || keysPressed.get(Keys.UP)) && getVelocity().y >= 0) {
            jumping = true;
            if (hitWall) {
                hitWall = false;
                getVelocity().y += ySpeed;
            }
        } else if (jumping) {
            jumping = false;
            if (getVelocity().y > ySpeed * 6 / 15) {
                getVelocity().y = ySpeed * 6 / 15;
            }
        }

        if (keysPressed.get(Keys.DOWN) || keysPressed.get(Keys.S)) {
            if (!ducking) {
                ducking = true;
                setHeight(getHeight() / 2);
            }
        } else if (ducking) {
            ducking = false;
            setHeight(getHeight() * 2);
        }

        if (ducking && canJump()) {
            getVelocity().x *= 2f / 4f;
        }
        // applies the acceleration to the velocity to the position.
        super.update(delta);
    }

    @Override
    public void render(Object rendererTool) {
        ShapeRenderer renderer = (ShapeRenderer) rendererTool;
        renderer.setColor(Color.BLUE);
        super.render(renderer);
    }

    @Override
    public void moveOutOf(Vector2 displacement) {
        super.moveOutOf(displacement);
        if (displacement.y > 0 && getVelocity().y < 0) {
            getVelocity().y = 0;
            hitWall = true;
        } else if (displacement.y < 0 && getVelocity().y > 0) {
            getVelocity().y *= -1;
        }
    }

    @Override
    public void receiveDamage() {
        expire();
    }

    private boolean canJump() {
        return getVelocity().y >= 0 && hitWall;
    }

    @Override
    public void keyDown(int keycode) {
        keysPressed.put(keycode, true);
    }

    @Override
    public void keyUp(int keycode) {
        keysPressed.put(keycode, false);
    }

    @Override
    public void keyTyped(char character) {

    }

    private void initKeysPressed() {
        keysPressed = new HashMap<>();
        keysPressed.put(Keys.A, Gdx.input.isKeyPressed(Keys.A));
        keysPressed.put(Keys.S, Gdx.input.isKeyPressed(Keys.S));
        keysPressed.put(Keys.D, Gdx.input.isKeyPressed(Keys.D));
        keysPressed.put(Keys.W, Gdx.input.isKeyPressed(Keys.W));
        keysPressed.put(Keys.LEFT, Gdx.input.isKeyPressed(Keys.LEFT));
        keysPressed.put(Keys.RIGHT, Gdx.input.isKeyPressed(Keys.RIGHT));
        keysPressed.put(Keys.UP, Gdx.input.isKeyPressed(Keys.UP));
        keysPressed.put(Keys.DOWN, Gdx.input.isKeyPressed(Keys.DOWN));
        keysPressed.put(Keys.SPACE, Gdx.input.isKeyPressed(Keys.SPACE));
    }
}
