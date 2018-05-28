var Player = Java.type("engine.entities.Player");
var InvincibleLaser = Java.type("engine.entities.InvincibleLaser");
var Direction = Java.type("engine.utils.Direction");

var FRACTION_CLOSED = 0;

manager.spawn(new Player(WORLD_WIDTH / 2, WORLD_HEIGHT * FRACTION_CLOSED, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_SPEED, PLAYER_JUMP_HEIGHT, GRAVITY));
manager.spawn(new InvincibleLaser(0, PLAYER_JUMP_HEIGHT, WORLD_WIDTH, 20, 0, 0, Direction.NONE));

load("levels/_outer_wall.js");

level.setIsLevelOver(function() {
    return false;
});