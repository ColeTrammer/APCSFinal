var ArrayEntityManager = Java.type("engine.utils.ArrayEntityManager");
manager = manager || new ArrayEntityManager();
var Timer = Java.type("engine.utils.Timer");
timer = timer || new Timer();
var Level = Java.type("game.Level");
level = level || new Level(__FILE__);
var BitmapFont = Java.type("com.badlogic.gdx.graphics.g2d.BitmapFont");
font = font || new BitmapFont();

var Gdx = Java.type("com.badlogic.gdx.Gdx");
var Player = Java.type("engine.entities.Player");
var InvincibleLaser = Java.type("engine.entities.InvincibleLaser");
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("game.Constants");
var Wall = Java.type("engine.entities.Wall");
var OneWayWall = Java.type("engine.entities.OneWayWall");
var Pulse = Java.type("engine.entities.Pulse");
var Laser = Java.type("engine.entities.Laser");
var Velocity = Java.type("engine.entities.components.Velocity");
var SimpleHarmonicOscillation = Java.type("engine.entities.components.SimpleHarmonicOscillation");
var Rectangle = Java.type("engine.entities.components.Rectangle");

var k = 2;
var T = 2 * Math.PI * Math.sqrt(1 / k);

var iLRect = new Rectangle(-60 * (T + 0.2) - 20 + 1 / 6 * C.WORLD_WIDTH - C.PLAYER_WIDTH, 0, 20, C.WORLD_HEIGHT);
manager.spawn(new InvincibleLaser(
    iLRect,
    new Velocity(60, 0),
    Direction.NONE));
var player = new Player(
    new Rectangle(50, 0, C.PLAYER_WIDTH, C.PLAYER_HEIGHT),
    C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY);
manager.spawn(player);
manager.spawn(new Wall(
    new Rectangle(0, 0, 50, C.WORLD_HEIGHT)));

// noinspection JSUnresolvedFunction
load({ script: Gdx.files.internal("assets/levels/_outer_wall.js").readString(), name: "_outer_wall.js" });
manager.spawn(new Wall(
    new Rectangle(1 / 6 * C.WORLD_WIDTH, 0, 20, C.WORLD_HEIGHT * 3 / 5)));
manager.spawn(new Wall(
    new Rectangle(2 / 6 * C.WORLD_WIDTH, C.WORLD_HEIGHT * 2 / 5, 20, C.WORLD_HEIGHT * 3 / 5)));
manager.spawn(new Wall(
    new Rectangle(C.WORLD_WIDTH * 1 / 6 + 20 + 2 * player.getWidth(), C.WORLD_HEIGHT * 2 / 5 - 20, C.WORLD_WIDTH * 2 / 6 - (C.WORLD_WIDTH * 1 / 6 + 20 + 2 * player.getWidth()) + 20, 20)));
manager.spawn(new Wall(
    new Rectangle(C.WORLD_WIDTH * 1 / 6 + 20 + 2 * player.getWidth(), C.PLAYER_HEIGHT, 20, C.WORLD_HEIGHT * 2 / 5 - 20 - (C.PLAYER_HEIGHT))));
var w1Rect = new Rectangle(1 / 6 * C.WORLD_WIDTH - C.PLAYER_WIDTH, C.WORLD_HEIGHT * 3 / 5 - C.PLAYER_HEIGHT, C.PLAYER_WIDTH, C.PLAYER_HEIGHT);
manager.spawn(new Wall(
    w1Rect,
    new SimpleHarmonicOscillation(1 / 6 * C.WORLD_WIDTH - C.PLAYER_WIDTH / 2, C.WORLD_HEIGHT / 2, 0, C.WORLD_HEIGHT / 2 - 2 * C.PLAYER_HEIGHT, k)));
var w2Rect = new Rectangle(2 / 6 * C.WORLD_WIDTH + 20, C.PLAYER_JUMP_HEIGHT - C.PLAYER_HEIGHT, 100, 20);
manager.spawn(new OneWayWall(
    w2Rect,
    new SimpleHarmonicOscillation(2 / 6 * C.WORLD_WIDTH - 20 + (C.WORLD_WIDTH - 100 - (2 / 6 * C.WORLD_WIDTH - 20)) / 2, C.PLAYER_JUMP_HEIGHT - C.PLAYER_HEIGHT + 10, (C.WORLD_WIDTH - 100 - (2 / 6 * C.WORLD_WIDTH - 20)) / 2, 0, 0.91375),
    Direction.DOWN));

timer.addAction(T - 0.5, T, 0, function() {
    manager.spawn(new Pulse(
        new Rectangle(0, 3 / 5 * C.WORLD_HEIGHT + 5, C.WORLD_WIDTH, 13),
        0.5, 0.08, Direction.UP));
});

timer.addAction(0.25, Number.POSITIVE_INFINITY, 0.75, function() {
    var l1Rect = new Rectangle(C.WORLD_WIDTH - 0.01, Math.random() * (C.PLAYER_JUMP_HEIGHT - C.PLAYER_HEIGHT - 20) + C.PLAYER_HEIGHT, 30, 5);
    manager.spawn(new Laser(
        l1Rect,
        new Velocity(-150, 0),
        Direction.LEFT));
});

timer.addAction(T + 1.2, Number.POSITIVE_INFINITY, 1, function() {
    manager.spawn(new Pulse(
        new Rectangle(C.WORLD_WIDTH * 1 / 6 + 20, 0, C.WORLD_WIDTH * 5 / 6 - 20, 10),
        0.5, 0.08, Direction.UP));
});

level.setIsLevelOver(function() {
    return player.getX() + player.getWidth() >= C.WORLD_WIDTH;
});