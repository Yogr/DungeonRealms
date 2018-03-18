package com.dungeonrealms.app.util;

import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.model.*;
import com.dungeonrealms.app.game.GameResources;
import com.dungeonrealms.app.game.Navigation;

public class DungeonUtils {

    public static String constructFullRoomMessage(GameSession gameSession) {
        return constructFullRoomMessage("", gameSession);
    }

    public static String constructFullRoomMessage(String prefix, GameSession gameSession) {
        StringBuilder messageBuilder = new StringBuilder();
        if (!StringUtils.isNullOrEmpty(prefix)) {
            messageBuilder.append(prefix);
        }

        if (gameSession.getGameState() == GameState.DUNGEON || gameSession.getGameState() == GameState.COMBAT) {
            Dungeon dungeon = Navigation.getDungeon(gameSession.getDungeonId());
            Room room = Navigation.getDungeonRoom(dungeon, gameSession.getRoomId());
            messageBuilder.append(room.getDescription());
        }

        if (!gameSession.getMonsters().isEmpty()) {
            messageBuilder.append(" There's also ");
            int count = gameSession.getMonsters().size();
            for (int i = 0; i < count; ++i) {
                if (i != 0 && i + 1 == count) {
                    messageBuilder.append(" and ");
                }
                Monster monster = GameResources.getInstance().getMonsters().get(gameSession.getMonsters().get(i).getMonsterId());
                messageBuilder.append("a ").append(monster.getName());
            }
            messageBuilder.append(" here");
        }

        return messageBuilder.toString();
    }
}
