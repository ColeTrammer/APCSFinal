package launchers;

import com.badlogic.gdx.backends.lwjgl.*;
import components.TheGame;
import utils.Constants;

public class DesktopLauncher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width  = Constants.SCREEN_WIDTH;
        config.height = Constants.SCREEN_HEIGHT;
        config.resizable = false;
        new LwjglApplication(new TheGame(), config);
    }
}