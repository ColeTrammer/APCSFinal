var EntityManager = Java.type("engine.utils.EntityManager");
manager = manager || new EntityManager();
var Timer = Java.type("engine.utils.Timer");
timer = timer || new Timer();
var Level = Java.type("components.Level");
level = level || new Level(__FILE__);

var Player = Java.type("engine.entities.Player");
var Wall = Java.type("engine.entities.Wall");
var Pulse = Java.type("engine.entities.Pulse");var LASER_WIDTH = 50;
var Laser = Java.type("engine.entities.Laser");
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("components.Constants");


var LASER_HEIGHT = 5;
var FRACTION_CLOSED = 1 / 5;
var SPAWN_RATE = 0.5;
var END = 15;

var player = new Player(0, C.WORLD_HEIGHT * FRACTION_CLOSED, C.PLAYER_WIDTH, C.PLAYER_HEIGHT, C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY);
manager.spawn(player);

load("assets/levels/_outer_wall.js");

manager.spawn(new Wall(0, 0, C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED));
manager.spawn(new Wall(0, 1 / 5 * C.WORLD_HEIGHT + 2 * C.PLAYER_JUMP_HEIGHT, C.WORLD_WIDTH, C.WORLD_HEIGHT))

timer.addAction(0, Number.POSITIVE_INFINITY, 1.632, function() {
    manager.spawn(new Laser(C.WORLD_WIDTH - 0.01, FRACTION_CLOSED * C.WORLD_HEIGHT + 20, 50, 5, -C.PLAYER_SPEED, 0, Direction.LEFT));
});

timer.addAction(0, Number.POSITIVE_INFINITY, 1, function() {
    manager.spawn(new Pulse(3 / 5 * C.WORLD_WIDTH, 0, 20, C.WORLD_HEIGHT, 0.5, 0.1, Direction.RIGHT));
});

level.setIsLevelOver(function() {
    return player.getX() + player.getWidth() >= C.WORLD_WIDTH;
});