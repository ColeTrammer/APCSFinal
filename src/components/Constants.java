package components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * This class contains all the constants used throughout the codebase
 * to allow for easy modification of tunable effects, appearances, and interactions.
 */
@SuppressWarnings("unused")
public final class Constants {
    public static final int     SCREEN_WIDTH  = 1200;
    public static final int     SCREEN_HEIGHT = 800;
    public static final boolean RESIZABLE     = false;
    public static final int     FPS           = 60;
    public static final float   MAX_DELTA     = 0.2f;

    public static final int     NUM_LEVELS    = 3;

    public static final float   MENU_WORLD_WIDTH  = SCREEN_WIDTH;
    public static final float   MENU_WORLD_HEIGHT = SCREEN_HEIGHT;
    public static final Vector2 MENU_CENTER       = new Vector2(MENU_WORLD_WIDTH / 2, MENU_WORLD_HEIGHT / 2);
    public static final float   MENU_LABEL_SCALE  = 1.5f;
    
    public static final float   WORLD_WIDTH  = SCREEN_WIDTH;
    public static final float   WORLD_HEIGHT = SCREEN_HEIGHT;
    
    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = Math.min(SCREEN_WIDTH, SCREEN_HEIGHT);
    
    public static final Color MENU_BACKGROUND_COLOR = new Color(0, 0, 0, 1);
    public static final Color GAME_BACKGROUND_COLOR = new Color(1, 1, 1, 1);

    public static final float GRAVITY = 0.5f * FPS * FPS;

    public static final float PLAYER_WIDTH       = 36f;
    public static final float PLAYER_HEIGHT      = 36f;
    public static final float PLAYER_SPEED       = 6 * FPS;
    public static final float PLAYER_JUMP_HEIGHT = WORLD_HEIGHT / 5f;

    public static final float BORDER_WALL_THICKNESS = 20f;

    /*
    public static final float LASER_POSITION_OFFSET = 0.01f;
    public static final float LASER_SPEED           = PLAYER_SPEED * 2f / 3f;
    public static final float LASER_THICKNESS       = 5f;
    public static final float LASER_LENGTH          = 30f;
    public static final float LASER_SPAWN_INTERVAL  = 100f; // in milliseconds
    */

    public static final String MENU_MESSAGE      = "Click to play!";
    public static final String GAME_OVER_MESSAGE = "You died! Click to play again!";
    public static final String GAME_TITLE        = "Game";

    private Constants() {}
}
