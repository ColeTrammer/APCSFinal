package engine.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import engine.entities.behaviors.InputReceiver;

@SuppressWarnings("unused")
public class TextField extends Text implements InputReceiver {
    private final String inputPrompt;
    private boolean finished;

    public TextField(String inputPrompt, BitmapFont font, float x, float y) {
        super(inputPrompt, font, x, y);
        this.inputPrompt = inputPrompt;
    }

    @Override
    public void keyDown(int keycode) {

    }

    @Override
    public void keyUp(int keycode) {

    }

    @Override
    public void keyTyped(char character) {
        if (finished) {
            return;
        }
        if (character == '\r') {
            finished = true;
        } else if (character == '\b') {
            if (getText().length() > inputPrompt.length()) {
                setText(getText().substring(0, getText().length() - 1));
            }
        } else {
            setText(getText() + character);
        }
    }

    public String getInput() {
        if (finished) {
            return getText().substring(inputPrompt.length());
        } else {
            return null;
        }
    }
}
