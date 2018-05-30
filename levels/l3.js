var Player = Java.type("engine.entities.Player");
var InvincibleLaser = Java.type("engine.entities.InvincibleLaser");
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("components.Constants");
var Wall = Java.type("engine.entities.Wall");
var SimpleHarmonicOscillatingWall = Java.type("engine.entities.SimpleHarmonicOscillatingWall");
var Pulse = Java.type("engine.entities.Pulse");

var k = 2;
var T = 2 * Math.PI * Math.sqrt(1 / k);

manager.add(new InvincibleLaser(-60 * T - 20 + 1 / 6 * C.WORLD_WIDTH - C.PLAYER_WIDTH, 0, 20, C.WORLD_HEIGHT, 60, 0, Direction.NONE));
manager.add(new Player(0, 0, C.PLAYER_WIDTH, C.PLAYER_HEIGHT, C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY));

load("levels/_outer_wall.js");
manager.add(new Wall(1 / 6 * C.WORLD_WIDTH, 0, 20, C.WORLD_HEIGHT * 3 / 5));
manager.add(new SimpleHarmonicOscillatingWall(1 / 6 * C.WORLD_WIDTH - C.PLAYER_WIDTH, C.WORLD_HEIGHT * 3 / 5 - C.PLAYER_HEIGHT, C.PLAYER_WIDTH, C.PLAYER_HEIGHT, 1 / 6 * C.WORLD_WIDTH - C.PLAYER_WIDTH / 2, C.WORLD_HEIGHT / 2, 0, C.WORLD_HEIGHT / 2 - 2 * C.PLAYER_HEIGHT, k));

timer.addAction(T - 0.5, T, 0, function() {
    manager.add(new Pulse(0, 3 / 5 * C.WORLD_HEIGHT, C.WORLD_WIDTH, 10, 0.5, 0.1, Direction.UP))
});

level.setIsLevelOver(function() {
    return false;
});