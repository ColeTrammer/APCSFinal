var ArrayEntityManager = Java.type("engine.utils.ArrayEntityManager");
manager = manager || new ArrayEntityManager();
var Timer = Java.type("engine.utils.Timer");
timer = timer || new Timer();
var Level = Java.type("game.Level");
level = level || new Level(__FILE__);
var BitmapFont = Java.type("com.badlogic.gdx.graphics.g2d.BitmapFont");
font = font || new BitmapFont();

var Wall = Java.type("engine.entities.Wall");
var C = Java.type("game.Constants");
var Rectangle = Java.type("engine.entities.components.Rectangle");

manager.spawn(new Wall(
    new Rectangle(-C.BORDER_WALL_THICKNESS, -C.BORDER_WALL_THICKNESS, C.BORDER_WALL_THICKNESS, C.WORLD_HEIGHT + 2 * C.BORDER_WALL_THICKNESS)));
manager.spawn(new Wall(
    new Rectangle(0, -C.BORDER_WALL_THICKNESS, C.WORLD_WIDTH, C.BORDER_WALL_THICKNESS)));
manager.spawn(new Wall(
    new Rectangle(0, C.WORLD_HEIGHT, C.WORLD_WIDTH, C.BORDER_WALL_THICKNESS)));
manager.spawn(new Wall(
    new Rectangle(C.WORLD_WIDTH, -C.BORDER_WALL_THICKNESS, C.BORDER_WALL_THICKNESS, C.WORLD_HEIGHT + 2 * C.BORDER_WALL_THICKNESS)));