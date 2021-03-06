package net.simon987.cubotplugin.event;

import net.simon987.cubotplugin.Cubot;
import net.simon987.server.GameServer;
import net.simon987.server.event.GameEvent;
import net.simon987.server.event.GameEventListener;
import net.simon987.server.event.UserCreationEvent;
import net.simon987.server.logging.LogManager;
import net.simon987.server.user.User;

import java.awt.*;
import java.util.Random;

public class UserCreationListener implements GameEventListener {
    @Override
    public Class getListenedEventType() {
        return UserCreationEvent.class;
    }

    @Override
    public void handle(GameEvent event) {

        User user = (User) event.getSource();

        LogManager.LOGGER.fine("(Plugin) Handled User creation event (Cubot Plugin)");

        Cubot cubot = new Cubot();

        Random random = new Random();
        int spawnX = GameServer.INSTANCE.getConfig().getInt("new_user_worldX") + random.nextInt(5);
        int spawnY = GameServer.INSTANCE.getConfig().getInt("new_user_worldY") + random.nextInt(5);

        cubot.setWorld(GameServer.INSTANCE.getGameUniverse().getWorld(spawnX, spawnY, true));
        cubot.getWorld().getGameObjects().add(cubot);
        cubot.getWorld().incUpdatable();

        cubot.setObjectId(GameServer.INSTANCE.getGameUniverse().getNextObjectId());

        cubot.setHeldItem(GameServer.INSTANCE.getConfig().getInt("new_user_item"));

        cubot.setEnergy(GameServer.INSTANCE.getConfig().getInt("battery_max_energy"));
        cubot.setMaxEnergy(GameServer.INSTANCE.getConfig().getInt("battery_max_energy"));

        cubot.setParent(user);

        Point point = cubot.getWorld().getRandomPassableTile();

        cubot.setX(point.x);
        cubot.setY(point.y);

        user.setControlledUnit(cubot);

    }
}
