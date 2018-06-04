var Player = Java.type("engine.entities.Player");
var InvincibleLaser = Java.type("engine.entities.InvincibleLaser");
var Direction = Java.type("engine.utils.Direction");
var C = Java.type("components.Constants");
var Wall = Java.type("engine.entities.Wall");
var SimpleHarmonicOscillatingWall = Java.type("engine.entities.SimpleHarmonicOscillatingWall");
var SimpleHarmonicOscillatingOneWayWall = Java.type("engine.entities.SimpleHarmonicOscillatingOneWayWall");
var Pulse = Java.type("engine.entities.Pulse");
var Laser = Java.type("engine.entities.Laser");

var k = 2;
var T = 2 * Math.PI * Math.sqrt(1 / k);

manager.spawn(new InvincibleLaser(-60 * (T + 0.2) - 20 + 1 / 6 * C.WORLD_WIDTH - C.PLAYER_WIDTH, 0, 20, C.WORLD_HEIGHT, 60, 0, Direction.NONE));
var player = new Player(50, 0, C.PLAYER_WIDTH, C.PLAYER_HEIGHT, C.PLAYER_SPEED, C.PLAYER_JUMP_HEIGHT, C.GRAVITY);
manager.spawn(player);
manager.spawn(new Wall(0, 0, 50, C.WORLD_HEIGHT));

load("assets/levels/_outer_wall.js");manager.spawn(new Wall(1 / 6 * C.WORLD_WIDTH, 0, 20, C.WORLD_HEIGHT * 3 / 5));
manager.spawn(new Wall(2 / 6 * C.WORLD_WIDTH, C.WORLD_HEIGHT * 2 / 5, 20, C.WORLD_HEIGHT * 3 / 5));
manager.spawn(new Wall(C.WORLD_WIDTH * 1 / 6 + 20 + 2 * player.getWidth(), C.WORLD_HEIGHT * 2 / 5 - 20, C.WORLD_WIDTH * 2 / 6 - (C.WORLD_WIDTH * 1 / 6 + 20 + 2 * player.getWidth()) + 20, 20)) ;
manager.spawn(new Wall(C.WORLD_WIDTH * 1 / 6 + 20 + 2 * player.getWidth(), player.getHeight(), 20, C.WORLD_HEIGHT * 2 / 5 - 20 - (player.getHeight())));
manager.spawn(new SimpleHarmonicOscillatingWall(1 / 6 * C.WORLD_WIDTH - C.PLAYER_WIDTH, C.WORLD_HEIGHT * 3 / 5 - C.PLAYER_HEIGHT, C.PLAYER_WIDTH, C.PLAYER_HEIGHT, 1 / 6 * C.WORLD_WIDTH - C.PLAYER_WIDTH / 2, C.WORLD_HEIGHT / 2, 0, C.WORLD_HEIGHT / 2 - 2 * C.PLAYER_HEIGHT, k));
manager.spawn(new SimpleHarmonicOscillatingOneWayWall(2 / 6 * C.WORLD_WIDTH + 20, C.PLAYER_JUMP_HEIGHT - player.getHeight(), 100, 20, 2 / 6 * C.WORLD_WIDTH - 20 + (C.WORLD_WIDTH - 100 - (2 / 6 * C.WORLD_WIDTH - 20)) / 2, C.PLAYER_JUMP_HEIGHT - player.getHeight() + 10, (C.WORLD_WIDTH - 100 - (2 / 6 * C.WORLD_WIDTH - 20)) / 2, 0, 0.91375, Direction.DOWN));

timer.addAction(T - 0.5, T, 0, function() {
    manager.spawn(new Pulse(0, 3 / 5 * C.WORLD_HEIGHT + 5, C.WORLD_WIDTH, 13, 0.5, 0.08, Direction.UP))
});

timer.addAction(0.25, Number.POSITIVE_INFINITY, 0.75, function() {
    manager.spawn(new Laser(C.WORLD_WIDTH - 0.01, Math.random() * (C.PLAYER_JUMP_HEIGHT - player.getHeight() - 20) + player.getHeight(), 30, 5, -150, 0, Direction.LEFT));
});

timer.addAction(T + 1.2, Number.POSITIVE_INFINITY, 1, function() {
    manager.spawn(new Pulse(C.WORLD_WIDTH * 1 / 6 + 20, 0, C.WORLD_WIDTH * 5 / 6 - 20, 10, 0.5, 0.08, Direction.UP));
});

level.setIsLevelOver(function() {
    return player.getX() + player.getWidth() >= C.WORLD_WIDTH;
});