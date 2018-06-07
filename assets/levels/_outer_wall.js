var Wall = Java.type("engine.entities.Wall");
var C = Java.type("game.Constants");
var Stationary = Java.type("engine.entities.components.Stationary");
var Rectangle = Java.type("engine.entities.components.Rectangle");

manager.spawn(new Wall(
    new Rectangle(-C.BORDER_WALL_THICKNESS, -C.BORDER_WALL_THICKNESS, C.BORDER_WALL_THICKNESS, C.WORLD_HEIGHT + 2 * C.BORDER_WALL_THICKNESS),
    new Stationary()));
manager.spawn(new Wall(
    new Rectangle(0, -C.BORDER_WALL_THICKNESS, C.WORLD_WIDTH, C.BORDER_WALL_THICKNESS),
    new Stationary()));
manager.spawn(new Wall(
    new Rectangle(0, C.WORLD_HEIGHT, C.WORLD_WIDTH, C.BORDER_WALL_THICKNESS),
    new Stationary()));
manager.spawn(new Wall(
    new Rectangle(C.WORLD_WIDTH, -C.BORDER_WALL_THICKNESS, C.BORDER_WALL_THICKNESS, C.WORLD_HEIGHT + 2 * C.BORDER_WALL_THICKNESS),
    new Stationary()));