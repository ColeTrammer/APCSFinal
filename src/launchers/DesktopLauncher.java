package launchers;

import com.badlogic.gdx.Application;
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
        config.resizable = Constants.RESIZABLE;
        config.backgroundFPS = Constants.FPS;
        config.title = Constants.GAME_TITLE;
        new LwjglApplication(new TheGame(), config).setLogLevel(Application.LOG_DEBUG);
    }
}