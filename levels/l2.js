var Player = Java.type("engine.entities.Player");
var MovableOneWayWall = Java.type("engine.entities.MovableOneWayWall");
var InvincibleLaser = Java.type("engine.entities.InvincibleLaser");
var Wall = Java.type("engine.entities.Wall");
var Laser = Java.type("engine.entities.Laser");
var Direction = Java.type("engine.utils.Direction");

LASER_WIDTH = 50;
LASER_HEIGHT = 5;
FRACTION_CLOSED = 1 / 5;
SPAWN_RATE = 0.15;
HEIGHT_INC = PLAYER_JUMP_HEIGHT - PLAYER_HEIGHT;

var player = new Player(WORLD_WIDTH / 2, WORLD_HEIGHT * FRACTION_CLOSED, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_SPEED, PLAYER_JUMP_HEIGHT, GRAVITY);
manager.spawn(player);
// manager.spawn(new InvincibleLaser(0, WORLD_HEIGHT * FRACTION_CLOSED / 2, WORLD_WIDTH, 10, 0, 70, Direction.NONE));
manager.spawn(new InvincibleLaser(0, WORLD_WIDTH * FRACTION_CLOSED / 2 + 2 * PLAYER_JUMP_HEIGHT, WORLD_WIDTH, 10, 0, 70, Direction.NONE));

load("levels/_outer_wall.js");

manager.spawn(new Wall(0, 0, WORLD_WIDTH, WORLD_HEIGHT * FRACTION_CLOSED));
manager.spawn(new MovableOneWayWall(1 / 4 * WORLD_WIDTH, WORLD_HEIGHT * FRACTION_CLOSED + HEIGHT_INC, 1 / 5 * WORLD_WIDTH, 10, PLAYER_SPEED, 0, 1 / 5 * WORLD_WIDTH, 0, 3 / 5 * WORLD_WIDTH, WORLD_HEIGHT, Direction.DOWN));
manager.spawn(new MovableOneWayWall(4 / 7 * WORLD_WIDTH, WORLD_HEIGHT * FRACTION_CLOSED + 2 * HEIGHT_INC, 1 / 5 * WORLD_WIDTH, 10, PLAYER_SPEED, 0, 1 / 5 * WORLD_WIDTH, 0, 3 / 5 * WORLD_WIDTH, WORLD_HEIGHT, Direction.DOWN));
manager.spawn(new MovableOneWayWall(8 / 21 * WORLD_WIDTH, WORLD_HEIGHT * FRACTION_CLOSED + 3 * HEIGHT_INC, 1 / 5 * WORLD_WIDTH, 10, PLAYER_SPEED, 0, 1 / 5 * WORLD_WIDTH, 0, 3 / 5 * WORLD_WIDTH, WORLD_HEIGHT, Direction.DOWN));
manager.spawn(new MovableOneWayWall(1 / 5 * WORLD_WIDTH, WORLD_HEIGHT * FRACTION_CLOSED + 4 * HEIGHT_INC, 1 / 5 * WORLD_WIDTH, 10, PLAYER_SPEED, 0, 1 / 5 * WORLD_WIDTH, 0, 3 / 5 * WORLD_WIDTH, WORLD_HEIGHT, Direction.DOWN));

timer.addAction(0, Number.POSITIVE_INFINITY, SPAWN_RATE, function() {
    if (Math.random() < 0.5) {
        manager.spawn(new Laser(WORLD_WIDTH - 0.01, Math.random() * 2 * PLAYER_JUMP_HEIGHT - PLAYER_JUMP_HEIGHT + player.getY(), LASER_WIDTH, LASER_HEIGHT, -PLAYER_SPEED, 0));
    } else {
        manager.spawn(new Laser(0, Math.random() * 2 * PLAYER_JUMP_HEIGHT - PLAYER_HEIGHT + player.getY(), LASER_WIDTH, LASER_HEIGHT, PLAYER_SPEED, 0));
    }
});

level.setIsLevelOver(function() {
    return player.getY() >= WORLD_HEIGHT - PLAYER_HEIGHT;
});