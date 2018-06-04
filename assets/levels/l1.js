var EntityManager = Java.type("engine.utils.EntityManager");
manager = manager || new EntityManager();
var Timer = Java.type("engine.utils.Timer");
timer = timer || new Timer();
var Level = Java.type("components.Level");
level = level || new Level(__FILE__);

var Player = Java.type("engine.entities.Player");
var Wall = Java.type("engine.entities.Wall");
var Pulse = Java.type("engine.entities.Pulse");
var Laser = Java.type("engine.entities.Laser");
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("components.Constants");

var LASER_WIDTH = 50;
var LASER_HEIGHT = 5;
var FRACTION_CLOSED = 1 / 3;
var SPAWN_RATE = 0.5;
var END = 15;

manager.spawn(new Player(C.WORLD_WIDTH / 2, C.WORLD_HEIGHT * FRACTION_CLOSED, C.PLAYER_WIDTH, C.PLAYER_HEIGHT, C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY));

load("assets/levels/_outer_wall.js");

manager.spawn(new Wall(0, 0, C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED));
manager.spawn(new Wall(0, C.WORLD_HEIGHT * (1 - FRACTION_CLOSED), C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED));

timer.addAction(0.3, END, 1, function() {
    manager.spawn(new Pulse(0, C.WORLD_HEIGHT * FRACTION_CLOSED + (C.PLAYER_HEIGHT / 2), C.WORLD_WIDTH, LASER_HEIGHT * 2, 0.5, 0.1, Direction.UP));
});
timer.addAction(0.0, END, SPAWN_RATE, function() {
    if (Math.random() < 0.5) {
        manager.spawn(new Laser(C.WORLD_WIDTH - 0.01, C.WORLD_HEIGHT * (Math.random() * ((C.WORLD_HEIGHT * FRACTION_CLOSED - LASER_HEIGHT) / C.WORLD_HEIGHT) + FRACTION_CLOSED), LASER_WIDTH, LASER_HEIGHT, -C.PLAYER_SPEED, 0));
    } else {
        manager.spawn(new Laser(0.01, C.WORLD_HEIGHT * (Math.random() * ((C.WORLD_HEIGHT * FRACTION_CLOSED - LASER_HEIGHT) / C.WORLD_HEIGHT) + FRACTION_CLOSED), LASER_WIDTH, LASER_HEIGHT, C.PLAYER_SPEED, 0));
    }
});

level.setIsLevelOver(function() {
    return timer.getElapsedTime() > END + ((C.WORLD_WIDTH + LASER_WIDTH) / C.PLAYER_SPEED);
});