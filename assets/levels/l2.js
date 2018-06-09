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
var OneWayWall = Java.type("engine.entities.OneWayWall");
var InvincibleLaser = Java.type("engine.entities.InvincibleLaser");
var Wall = Java.type("engine.entities.Wall");
var Laser = Java.type("engine.entities.Laser");
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("game.Constants");
var Velocity = Java.type("engine.entities.components.Velocity");
var SimpleHarmonicOscillation = Java.type("engine.entities.components.SimpleHarmonicOscillation");
var Rectangle = Java.type("engine.entities.components.Rectangle");

var LASER_WIDTH = 50;
var LASER_HEIGHT = 5;
var FRACTION_CLOSED = 1 / 5;
var SPAWN_RATE = 0.35;
var HEIGHT_INC = C.PLAYER_JUMP_HEIGHT - C.PLAYER_HEIGHT;

var player = new Player(
    new Rectangle(C.WORLD_WIDTH / 2, C.WORLD_HEIGHT * FRACTION_CLOSED, C.PLAYER_WIDTH, C.PLAYER_HEIGHT),
    C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY);
manager.spawn(player);
var iLRect = new Rectangle(0, C.WORLD_WIDTH * FRACTION_CLOSED / 2 + 2 * C.PLAYER_JUMP_HEIGHT, C.WORLD_WIDTH, 10);
manager.spawn(new InvincibleLaser(
    iLRect,
    new Velocity(0, 70),
    Direction.NONE));

// noinspection JSUnresolvedFunction
load({ script: Gdx.files.internal("assets/levels/_outer_wall.js").readString(), name: "_outer_wall.js" });

manager.spawn(new Wall(
    new Rectangle(0, 0, C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED)));

var p1Rect = new Rectangle(1 /  4 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED +     HEIGHT_INC - 5, 1 / 5 * C.WORLD_WIDTH, 10);
var p2Rect = new Rectangle(4 /  7 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED + 2 * HEIGHT_INC - 5, 1 / 5 * C.WORLD_WIDTH, 10);
var p3Rect = new Rectangle(8 / 21 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED + 3 * HEIGHT_INC - 5, 1 / 5 * C.WORLD_WIDTH, 10);
var p4Rect = new Rectangle(1 /  5 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED + 4 * HEIGHT_INC - 5, 1 / 5 * C.WORLD_WIDTH, 10);
manager.spawn(new OneWayWall(
    p1Rect,
    new SimpleHarmonicOscillation(1 /  2 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED +     HEIGHT_INC, 1 / 5 * C.WORLD_WIDTH, 0, 5),
    Direction.DOWN));
manager.spawn(new OneWayWall(
    p2Rect,
    new SimpleHarmonicOscillation(1 /  2 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED + 2 * HEIGHT_INC, 1 / 5 * C.WORLD_WIDTH, 0, 5),
    Direction.DOWN));
manager.spawn(new OneWayWall(
    p3Rect,
    new SimpleHarmonicOscillation(1 /  2 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED + 3 * HEIGHT_INC, 1 / 5 * C.WORLD_WIDTH, 0, 5),
    Direction.DOWN));
manager.spawn(new OneWayWall(
    p4Rect,
    new SimpleHarmonicOscillation(1 /  2 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED + 4 * HEIGHT_INC, 1 / 5 * C.WORLD_WIDTH, 0, 5),
    Direction.DOWN));

timer.addAction(0, Number.POSITIVE_INFINITY, SPAWN_RATE, function() {
    var lRect;
    if (Math.random() < 0.5) {
        lRect = new Rectangle(C.WORLD_WIDTH - 0.01, Math.random() * 2 * C.PLAYER_JUMP_HEIGHT - C.PLAYER_JUMP_HEIGHT + player.getY(), LASER_WIDTH, LASER_HEIGHT);
        manager.spawn(new Laser(
            lRect,
            new Velocity(-C.PLAYER_SPEED, 0),
            Direction.LEFT));
    } else {
        lRect = new Rectangle(0.01, Math.random() * 2 * C.PLAYER_JUMP_HEIGHT - C.PLAYER_HEIGHT + player.getY(), LASER_WIDTH, LASER_HEIGHT);
        manager.spawn(new Laser(
            lRect,
            new Velocity(C.PLAYER_SPEED, 0),
            Direction.RIGHT));
    }
});

level.setIsLevelOver(function() {
    return player.getY() >= C.WORLD_HEIGHT - C.PLAYER_HEIGHT;
});
