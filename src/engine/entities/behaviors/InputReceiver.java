package engine.entities.behaviors;

import engine.entities.Entity;

public interface InputReceiver extends Entity {
    void keyDown(int keycode);
    void keyUp(int keycode);
    void keyTyped(char character);
}
