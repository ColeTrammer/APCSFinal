package utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static int SCREEN_WIDTH  = 800;
    public static int SCREEN_HEIGHT = 600;
    
    public static final float   MENU_WORLD_WIDTH  = 800f;
    public static final float   MENU_WORLD_HEIGHT = 600f;
    public static final Vector2 MENU_CENTER       = new Vector2(MENU_WORLD_WIDTH / 2, MENU_WORLD_HEIGHT / 2);
    public static final float   MENU_LABEL_SCALE  = 1.5f;
    
    public static final float   WORLD_WIDTH  = 800f;
    public static final float   WORLD_HEIGHT = 600f;
    public static final Vector2 WORLD_CENTER = new Vector2(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
    
    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = 600f;
    
    public static final Color MENU_BACKGROUND_COLOR = new Color(0, 0, 0, 1);
    public static final Color GAME_BACKGROUND_COLOR = new Color(0, 0, 0, 1);
    
    public static final float PLAYER_WIDTH  = 36f;
    public static final float PLAYER_HEIGHT = 36f;
    public static final float PLAYER_SPEED  = 500f;

    public static final float WALL_THICKNESS = 20f;
}
