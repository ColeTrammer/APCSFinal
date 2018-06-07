var Player = Java.type("engine.entities.Player");
var InvincibleLaser = Java.type("engine.entities.InvincibleLaser");
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("game.Constants");
var SimpleHarmonicOscillatingOneWayWall = Java.type("engine.entities.SimpleHarmonicOscillatingOneWayWall");
var Wall = Java.type("engine.entities.Wall");

var FRACTION_CLOSED = 0;

var player = new Player(0, C.WORLD_HEIGHT * FRACTION_CLOSED, C.PLAYER_WIDTH, C.PLAYER_HEIGHT, C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY);
manager.spawn(player);
for (var i = 0; i < C.WORLD_HEIGHT / (C.PLAYER_JUMP_HEIGHT - player.getHeight() - 20) - 1; i++) {
    manager.spawn(new Wall(i % 2 == 0 ? 0 : 1 / 5 * C.WORLD_WIDTH, (i + 1) * (C.PLAYER_JUMP_HEIGHT - player.getHeight() - 20), 4 / 5 * C.WORLD_WIDTH, 20));
}

load("assets/levels/_outer_wall.js");

level.setIsLevelOver(function() {
    return false;
});