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
var Wall = Java.type("engine.entities.Wall");
var Pulse = Java.type("engine.entities.Pulse");
var Laser = Java.type("engine.entities.Laser");
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("game.Constants");
var Text = Java.type("engine.entities.Text");
var Velocity = Java.type("engine.entities.components.Velocity");
var Rectangle = Java.type("engine.entities.components.Rectangle");

var LASER_WIDTH = 50;
var LASER_HEIGHT = 5;
var FRACTION_CLOSED = 1 / 3;
var SPAWN_RATE = 0.5;
var END = 15;

manager.spawn(new Player(
    new Rectangle(C.WORLD_WIDTH / 2, C.WORLD_HEIGHT * FRACTION_CLOSED, C.PLAYER_WIDTH, C.PLAYER_HEIGHT),
    C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY));

// noinspection JSUnresolvedFunction
load({ script: Gdx.files.internal("assets/levels/_outer_wall.js").readString(), name: "_outer_wall.js" });

manager.spawn(new Wall(
    new Rectangle(0, 0, C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED)));
manager.spawn(new Wall(
    new Rectangle(0, C.WORLD_HEIGHT * (1 - FRACTION_CLOSED), C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED)));

var timeText = new Text("Survive until the timer runs out: " + Math.round(END + ((C.WORLD_WIDTH + LASER_WIDTH) / C.PLAYER_SPEED)), font, 50, 4 / 5 * C.WORLD_HEIGHT);
manager.spawn(timeText);

timer.addAction(0.6, END, 1, function() {
    manager.spawn(new Pulse(
        new Rectangle(0, C.WORLD_HEIGHT * FRACTION_CLOSED + (C.PLAYER_HEIGHT / 2), C.WORLD_WIDTH, LASER_HEIGHT * 2),
        0.5, 0.1, Direction.UP));
});
timer.addAction(0.0, END, SPAWN_RATE, function() {
    var lRect;
    if (Math.random() < 0.5) {
        lRect = new Rectangle(C.WORLD_WIDTH - 0.01, C.WORLD_HEIGHT * (Math.random() * ((C.WORLD_HEIGHT * FRACTION_CLOSED - LASER_HEIGHT) / C.WORLD_HEIGHT) + FRACTION_CLOSED), LASER_WIDTH, LASER_HEIGHT);
        manager.spawn(new Laser(
            lRect,
            new Velocity(-C.PLAYER_SPEED, 0),
            Direction.LEFT));
    } else {
        lRect = new Rectangle(0.01, C.WORLD_HEIGHT * (Math.random() * ((C.WORLD_HEIGHT * FRACTION_CLOSED - LASER_HEIGHT) / C.WORLD_HEIGHT) + FRACTION_CLOSED), LASER_WIDTH, LASER_HEIGHT);
        manager.spawn(new Laser(
            lRect,
            new Velocity(C.PLAYER_SPEED, 0),
            Direction.RIGHT));
    }
});
timer.addAction(1.0, END + ((C.WORLD_WIDTH + LASER_WIDTH) / C.PLAYER_SPEED), 1, function() {
    timeText.setText("Survive until the timer runs out: " + Math.round(END + ((C.WORLD_WIDTH + LASER_WIDTH) / C.PLAYER_SPEED) - timer.getElapsedTime()));
});

level.setIsLevelOver(function() {
    return timer.getElapsedTime() > END + ((C.WORLD_WIDTH + LASER_WIDTH) / C.PLAYER_SPEED);
});