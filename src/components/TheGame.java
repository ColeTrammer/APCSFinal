package components;

import entities.ArrayEntityManager;
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
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;

    @Override
    public void create() {
        manager = new ArrayEntityManager();
        manager.add(new Player());
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this, manager);
        endScreen = new EndScreen(this);
        // immediately show the menu screen.
        showMenuScreen();
    }

    /**
     * Resets the game state.
     */
    public void reset() {
        manager.reset();
        manager.add(new Player());
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
