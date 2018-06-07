var EntityManager = Java.type("engine.utils.EntityManager");
manager = manager || new EntityManager();
var Timer = Java.type("engine.utils.Timer");
timer = timer || new Timer();
var Level = Java.type("game.Level");
level = level || new Level(__FILE__);

var Player = Java.type("engine.entities.Player");
var Wall = Java.type("engine.entities.Wall");
var Pulse = Java.type("engine.entities.Pulse");
var Laser = Java.type("engine.entities.Laser");
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("game.Constants");
var Text = Java.type("engine.entities.Text");
var Rectangle = Java.type("engine.entities.components.Rectangle");
var Stationary = Java.type("engine.entities.components.Stationary");
var Velocity = Java.type("engine.entities.components.Velocity");

var LASER_WIDTH = 50;
var LASER_HEIGHT = 5;
var FRACTION_CLOSED = 1 / 5;
var SPAWN_RATE = 0.5;
var END = 15;

var player = new Player(
    new Rectangle(0, C.WORLD_HEIGHT * FRACTION_CLOSED, C.PLAYER_WIDTH, C.PLAYER_HEIGHT),
    C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY);
manager.spawn(player);

load("assets/levels/_outer_wall.js");

manager.spawn(new Wall(
    new Rectangle(0, 0, C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED),
    new Stationary()));
manager.spawn(new Wall(
    new Rectangle(0, 1 / 5 * C.WORLD_HEIGHT + 2 * C.PLAYER_JUMP_HEIGHT, C.WORLD_WIDTH, C.WORLD_HEIGHT),
    new Stationary()));

manager.spawn(new Text("Go right! Avoid the lasers and stuff!", font, 50, 700));

timer.addAction(0, Number.POSITIVE_INFINITY, 1.632, function() {
    var lRect = new Rectangle(C.WORLD_WIDTH - 0.01, FRACTION_CLOSED * C.WORLD_HEIGHT + 20, 50, 5);
    manager.spawn(new Laser(
        lRect,
        new Velocity(lRect, -C.PLAYER_SPEED, 0),
        Direction.LEFT));
});

timer.addAction(0, Number.POSITIVE_INFINITY, 1, function() {
    manager.spawn(new Pulse(
        new Rectangle(3 / 5 * C.WORLD_WIDTH, 0, 20, C.WORLD_HEIGHT),
        0.5, 0.1, Direction.RIGHT));
});

level.setIsLevelOver(function() {
    return player.getX() + player.getWidth() >= C.WORLD_WIDTH;
});