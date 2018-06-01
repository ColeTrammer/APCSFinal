package components;

import components.screens.EndScreen;
import components.screens.GameScreen;
import components.screens.MenuScreen;
import com.badlogic.gdx.Game;

/**
 * Class that represents the Game itself. Keeps track of
 * basic state like all game objects and provides methods
 * to switch between the numerous screens that comprise the game.
 */
public class TheGame extends Game {
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;

    @Override
    public void create() {
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        endScreen = new EndScreen(this);

        // immediately show the menu screen.
        showMenuScreen();
    }

    /**
     * Resets the game state.
     */
    public void reset() {
        gameScreen.reset();
    }

    /**
     * Shows the MenuScreen
     */
    public void showMenuScreen() {
        setScreen(menuScreen);
    }

    /**
     * Shows the GameScreen
     */
    public void showGameScreen() {
        setScreen(gameScreen);
    }

    /**
     * Shows the EndScreen
     */
    public void showEndScreen () {
        setScreen(endScreen);
    }
}
