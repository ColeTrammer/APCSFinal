package components;

import entities.EntityManager;
import entities.Player;
import com.badlogic.gdx.Game;

/**
 * Class that represents the Game itself. Keeps track of
 * basic state like all game objects and provides methods
 * to switch between the numerous screens that comprise the game.
 */
public class TheGame extends Game {
    private EntityManager manager;
    
    @Override
    public void create() {
        manager = new EntityManager(new Player());
        // immediately show the menu screen.
        showMenuScreen();
    }

    /**
     * Resets the game state.
     */
    public void reset() {
        manager = new EntityManager(new Player());
    }

    /**
     * Shows the MenuScreen
     */
    public void showMenuScreen() {
        setScreen(new MenuScreen(this));
    }

    /**
     * Shows the GameScreen
     */
    public void showGameScreen() {
        setScreen(new GameScreen(this, manager));
    }

    /**
     * Shows the EndScreen
     */
    public void showEndScreen () {
        setScreen(new EndScreen (this));
    }
}
