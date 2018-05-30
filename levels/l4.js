var Player = Java.type("engine.entities.Player");
var InvincibleLaser = Java.type("engine.entities.InvincibleLaser");
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("components.Constants");
var SimpleHarmonicOscillatingOneWayWall = Java.type("engine.entities.SimpleHarmonicOscillatingOneWayWall");

var FRACTION_CLOSED = 0;

manager.spawn(new Player(C.WORLD_WIDTH / 2, C.WORLD_HEIGHT * FRACTION_CLOSED, C.PLAYER_WIDTH, C.PLAYER_HEIGHT, C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY));
manager.spawn(new InvincibleLaser(0, C.PLAYER_JUMP_HEIGHT, C.WORLD_WIDTH, 20, 0, 0, Direction.NONE));
// manager.spawn(new SimpleHarmonicOscillatingOneWayWall(C.WORLD_WIDTH / 2 - 200, C.WORLD_HEIGHT / 6 - 10, 400, 20, C.WORLD_WIDTH / 2, C.WORLD_HEIGHT / 6, 200, 0, 40, Direction.DOWN))

load("levels/_outer_wall.js");

level.setIsLevelOver(function() {
    return false;
});