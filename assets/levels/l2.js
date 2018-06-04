var Player = Java.type("engine.entities.Player");
var SimpleHarmonicOscillatingOneWayWall = Java.type("engine.entities.SimpleHarmonicOscillatingOneWayWall");
var InvincibleLaser = Java.type("engine.entities.InvincibleLaser");
var Wall = Java.type("engine.entities.Wall");
var Laser = Java.type("engine.entities.Laser");
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("components.Constants");

var LASER_WIDTH = 50;
var LASER_HEIGHT = 5;
var FRACTION_CLOSED = 1 / 5;
var SPAWN_RATE = 0.2;
var HEIGHT_INC = C.PLAYER_JUMP_HEIGHT - C.PLAYER_HEIGHT;

var player = new Player(C.WORLD_WIDTH / 2, C.WORLD_HEIGHT * FRACTION_CLOSED, C.PLAYER_WIDTH, C.PLAYER_HEIGHT, C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY);
manager.spawn(player);
// manager.spawn(new InvincibleLaser(0, WORLD_HEIGHT * FRACTION_CLOSED / 2, WORLD_WIDTH, 10, 0, 70, Direction.NONE));
manager.spawn(new InvincibleLaser(0, C.WORLD_WIDTH * FRACTION_CLOSED / 2 + 2 * C.PLAYER_JUMP_HEIGHT, C.WORLD_WIDTH, 10, 0, 70, Direction.NONE));

load("assets/levels/_outer_wall.js");

manager.spawn(new Wall(0, 0, C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED));
manager.spawn(new SimpleHarmonicOscillatingOneWayWall(1 /  4 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED +     HEIGHT_INC - 5, 1 / 5 * C.WORLD_WIDTH, 10, 1 /  2 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED +     HEIGHT_INC, 1 / 5 * C.WORLD_WIDTH, 0, 5, Direction.DOWN));
manager.spawn(new SimpleHarmonicOscillatingOneWayWall(4 /  7 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED + 2 * HEIGHT_INC - 5, 1 / 5 * C.WORLD_WIDTH, 10, 1 /  2 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED + 2 * HEIGHT_INC, 1 / 5 * C.WORLD_WIDTH, 0, 5, Direction.DOWN));
manager.spawn(new SimpleHarmonicOscillatingOneWayWall(8 / 21 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED + 3 * HEIGHT_INC - 5, 1 / 5 * C.WORLD_WIDTH, 10, 1 /  2 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED + 3 * HEIGHT_INC, 1 / 5 * C.WORLD_WIDTH, 0, 5, Direction.DOWN));
manager.spawn(new SimpleHarmonicOscillatingOneWayWall(1 /  5 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED + 4 * HEIGHT_INC - 5, 1 / 5 * C.WORLD_WIDTH, 10, 1 /  2 * C.WORLD_WIDTH, C.WORLD_HEIGHT * FRACTION_CLOSED + 4 * HEIGHT_INC, 1 / 5 * C.WORLD_WIDTH, 0, 5, Direction.DOWN));

timer.addAction(0, Number.POSITIVE_INFINITY, SPAWN_RATE, function() {
    if (Math.random() < 0.5) {
        manager.spawn(new Laser(C.WORLD_WIDTH - 0.01, Math.random() * 2 * C.PLAYER_JUMP_HEIGHT - C.PLAYER_JUMP_HEIGHT + player.getY(), LASER_WIDTH, LASER_HEIGHT, -C.PLAYER_SPEED, 0));
    } else {
        manager.spawn(new Laser(0, Math.random() * 2 * C.PLAYER_JUMP_HEIGHT - C.PLAYER_HEIGHT + player.getY(), LASER_WIDTH, LASER_HEIGHT, C.PLAYER_SPEED, 0));
    }
});

level.setIsLevelOver(function() {
    return player.getY() >= C.WORLD_HEIGHT - C.PLAYER_HEIGHT;
});