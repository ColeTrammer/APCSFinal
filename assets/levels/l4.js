var ArrayEntityManager = Java.type("engine.utils.ArrayEntityManager");
manager = manager || new ArrayEntityManager();
var Timer = Java.type("engine.utils.Timer");
timer = timer || new Timer();
var Level = Java.type("game.Level");
level = level || new Level(__FILE__);
var BitmapFont = Java.type("com.badlogic.gdx.graphics.g2d.BitmapFont");
font = font || new BitmapFont();

var Player = Java.type("engine.entities.Player");
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("game.Constants");
var Wall = Java.type("engine.entities.Wall");
var Stationary = Java.type("engine.entities.components.Stationary");
var Rectangle = Java.type("engine.entities.components.Rectangle");

var FRACTION_CLOSED = 0;

var player = new Player(
    new Rectangle(0, C.WORLD_HEIGHT * FRACTION_CLOSED, C.PLAYER_WIDTH, C.PLAYER_HEIGHT),
    C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY);
manager.spawn(player);
for (var i = 0; i < C.WORLD_HEIGHT / (C.PLAYER_JUMP_HEIGHT - player.getHeight() - 20) - 1; i++) {
    manager.spawn(new Wall(
        new Rectangle(i % 2 === 0 ? 0 : 1 / 5 * C.WORLD_WIDTH, (i + 1) * (C.PLAYER_JUMP_HEIGHT - player.getHeight() - 20), 4 / 5 * C.WORLD_WIDTH, 20),
        new Stationary()));
}

load("assets/levels/_outer_wall.js");

level.setIsLevelOver(function() {
    return false;
});