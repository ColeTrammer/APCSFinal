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
var Rectangle = Java.type("engine.entities.components.Rectangle");
var InvincibleLaser = Java.type("engine.entities.InvincibleLaser");
var SimpleHarmonicOscillation = Java.type("engine.entities.components.SimpleHarmonicOscillation");
var Laser = Java.type("engine.entities.Laser");
var Velocity = Java.type("engine.entities.components.Velocity");

var FRACTION_CLOSED = 0;
var LASER_WIDTH = 10;
var LASER_HEIGHT = 5;

manager.spawn(new InvincibleLaser(
    new Rectangle(C.WORLD_WIDTH - 20, 0, 20, C.WORLD_HEIGHT),
    new SimpleHarmonicOscillation(3 / 4 * C.WORLD_WIDTH, C.WORLD_HEIGHT / 2, 1 / 4 * C.WORLD_WIDTH, 0, 2.312),
    Direction.NONE
));

manager.spawn(new InvincibleLaser(
    new Rectangle(1 / 2 * C.WORLD_WIDTH, 0, 20, C.WORLD_HEIGHT),
    new SimpleHarmonicOscillation(1 / 4 * C.WORLD_WIDTH, C.WORLD_HEIGHT / 2, 1 / 4 * C.WORLD_WIDTH, 0, 2.312),
    Direction.NONE
));

var player = new Player(
    new Rectangle(3 / 4 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED, C.PLAYER_WIDTH, C.PLAYER_HEIGHT),
    C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY);
manager.spawn(player);
for (var i = 0; i < C.WORLD_HEIGHT / (C.PLAYER_JUMP_HEIGHT - player.getHeight() - 20) - 1; i++) {
    manager.spawn(new Wall(
        new Rectangle(i % 2 === 0 ? 0 : 1 / 5 * C.WORLD_WIDTH, (i + 1) * (C.PLAYER_JUMP_HEIGHT - player.getHeight() - 20), 4 / 5 * C.WORLD_WIDTH, 20)));
}

load("assets/levels/_outer_wall.js");

timer.addAction(0.0, Number.POSITIVE_INFINITY, 0.4, function() {
    var lRect;
    var h = Math.floor(Math.random() * (C.WORLD_HEIGHT / (C.PLAYER_JUMP_HEIGHT - player.getHeight() - 20) - 1)) * (C.PLAYER_JUMP_HEIGHT - player.getHeight() - 20) + 30;
    if (Math.random() < 0.5) {
        lRect = new Rectangle(C.WORLD_WIDTH - 0.01, h, LASER_WIDTH, LASER_HEIGHT);
        manager.spawn(new Laser(
            lRect,
            new Velocity(-C.PLAYER_SPEED, 0),
            Direction.LEFT));
    } else {
        lRect = new Rectangle(0.01, h, LASER_WIDTH, LASER_HEIGHT);
        manager.spawn(new Laser(
            lRect,
            new Velocity(C.PLAYER_SPEED, 0),
            Direction.RIGHT));
    }
});

level.setIsLevelOver(function() {
    return player.getY() + player.getHeight() >= C.WORLD_HEIGHT;
});