package components;

import logic.ObjectManager;
import logic.Player;
import com.badlogic.gdx.Game;

public class TheGame extends Game {
   
    private ObjectManager manager;
    
    @Override
    public void create() {
        showMenuScreen();
        manager = new ObjectManager(new Player());
    }
    
    public void showMenuScreen() {
        setScreen(new MenuScreen(this));
    }
   
    public void showGameScreen() {
        setScreen(new GameScreen(this, manager));
    }
}
