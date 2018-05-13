package components;

import entities.EntityManager;
import entities.Player;
import com.badlogic.gdx.Game;

public class TheGame extends Game {
   
    private EntityManager manager;
    
    @Override
    public void create() {
        manager = new EntityManager(new Player());
        showMenuScreen();
    }

    public void reset() {
        manager = new EntityManager(new Player());
    }

    public void showMenuScreen() {
        setScreen(new MenuScreen(this));
    }
    public void showGameScreen() {
        setScreen(new GameScreen(this, manager));
    }
    public void showEndScreen () { setScreen(new EndScreen (this)); }
}
