package launchers;

import com.badlogic.gdx.backends.lwjgl.*;
import components.TheGame;
import components.Constants;

/**
 * Class that launches the game. This class is platform dependent
 * and only works on Desktop computers.
 */
public class DesktopLauncher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width  = Constants.SCREEN_WIDTH;
        config.height = Constants.SCREEN_HEIGHT;
        config.resizable = false;
        new LwjglApplication(new TheGame(), config);
    }
}