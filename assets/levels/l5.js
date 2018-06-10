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
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("game.Constants");
var Wall = Java.type("engine.entities.Wall");
var Rectangle = Java.type("engine.entities.components.Rectangle");
var OneWayWall = Java.type("engine.entities.OneWayWall");
var SimpleHarmonicOscillation = Java.type("engine.entities.components.SimpleHarmonicOscillation");

var FRACTION_CLOSED = 0;

manager.spawn(new Wall(
   new Rectangle(0, C.WORLD_HEIGHT / 2, C.WORLD_WIDTH, C.WORLD_HEIGHT / 2)
));

manager.spawn(new Player(
    new Rectangle(C.WORLD_WIDTH / 2, C.WORLD_HEIGHT * FRACTION_CLOSED, C.PLAYER_WIDTH, C.PLAYER_HEIGHT),
    C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY));
var w1Rect = new Rectangle(C.WORLD_WIDTH / 2 - 200, C.WORLD_HEIGHT / 6 - 10, 400, 20);
manager.spawn(new OneWayWall(
    w1Rect,
    new SimpleHarmonicOscillation(C.WORLD_WIDTH / 2, C.WORLD_HEIGHT / 6, 200, 0, 6),
    Direction.DOWN));

// noinspection JSUnresolvedFunction
load({ script: Gdx.files.internal("assets/levels/_outer_wall.js").readString(), name: "_outer_wall.js" });

level.setIsLevelOver(function() {
    return true;
});