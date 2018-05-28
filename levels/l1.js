var Player = Java.type("engine.entities.Player");
var Wall = Java.type("engine.entities.Wall");
var Pulse = Java.type("engine.entities.Pulse");
var Laser = Java.type("engine.entities.Laser");
var Direction = Java.type("engine.utils.Direction");

var LASER_WIDTH = 50;
var LASER_HEIGHT = 5;
var FRACTION_CLOSED = 1 / 3;
var SPAWN_RATE = 0.5;
var END = 15;

manager.spawn(new Player(WORLD_WIDTH / 2, WORLD_HEIGHT * FRACTION_CLOSED, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_SPEED, PLAYER_JUMP_HEIGHT, GRAVITY));

load("levels/_outer_wall.js");

manager.spawn(new Wall(0, 0, WORLD_WIDTH, WORLD_HEIGHT * FRACTION_CLOSED));
manager.spawn(new Wall(0, WORLD_HEIGHT * (1 - FRACTION_CLOSED), WORLD_WIDTH, WORLD_HEIGHT * FRACTION_CLOSED));

timer.addAction(0.3, END, 1, function() {
    manager.spawn(new Pulse(0, WORLD_HEIGHT * FRACTION_CLOSED + (PLAYER_HEIGHT / 2), WORLD_WIDTH, LASER_HEIGHT * 2, 0.5, 0.1, Direction.RIGHT));
});
timer.addAction(0.0, END, SPAWN_RATE, function() {
    if (Math.random() < 0.5) {
        manager.spawn(new Laser(WORLD_WIDTH - 0.01, WORLD_HEIGHT * (Math.random() * ((WORLD_HEIGHT * FRACTION_CLOSED - LASER_HEIGHT) / WORLD_HEIGHT) + FRACTION_CLOSED), LASER_WIDTH, LASER_HEIGHT, -PLAYER_SPEED, 0));
    } else {
        manager.spawn(new Laser(0.01, WORLD_HEIGHT * (Math.random() * ((WORLD_HEIGHT * FRACTION_CLOSED - LASER_HEIGHT) / WORLD_HEIGHT) + FRACTION_CLOSED), LASER_WIDTH, LASER_HEIGHT, PLAYER_SPEED, 0));
    }
});

level.setIsLevelOver(function() {
    return timer.getElapsedTime() > END + ((WORLD_WIDTH + LASER_WIDTH) / PLAYER_SPEED);
});