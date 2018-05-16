package components;

import components.screens.EndScreen;
import components.screens.GameScreen;
import components.screens.MenuScreen;
import engine.utils.EntityManager;
import engine.entities.Player;
import com.badlogic.gdx.Game;
import engine.utils.ListEntityManager;

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
        manager = new ListEntityManager();
        manager.add(new Player(Constants.WORLD_CENTER.x, 0, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, Constants.PLAYER_SPEED, Constants.PLAYER_JUMP_HEIGHT, Constants.GRAVITY));
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
        manager.add(new Player(Constants.WORLD_CENTER.x, 0, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, Constants.PLAYER_SPEED, Constants.PLAYER_JUMP_HEIGHT, Constants.GRAVITY));
        gameScreen = new GameScreen(this, manager);
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
