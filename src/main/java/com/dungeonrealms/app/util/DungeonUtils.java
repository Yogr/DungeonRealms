package com.dungeonrealms.app.util;

import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.dummy.GetDummy;
import com.dungeonrealms.app.model.*;

public class DungeonUtils {

    public static String constructFullRoomMessage(GameSession gameSession) {
        return constructFullRoomMessage("", gameSession);
    }

    public static String constructFullRoomMessage(String prefix, GameSession gameSession) {
        StringBuilder messageBuilder = new StringBuilder();
        if (!StringUtils.isNullOrEmpty(prefix)) {
            messageBuilder.append(prefix).append(". ");
        }

        if (gameSession.getGameState() == GameState.DUNGEON || gameSession.getGameState() == GameState.COMBAT) {
            Dungeon dungeon = GetDummy.dummyDungeon();
            Integer roomId = gameSession.getRoomId();
            Room room = GetDummy.dummyRoom1();
            if (roomId != null && roomId != -1) {
                switch (roomId) {
                    default:
                    case 1: break;
                    case 2: room = GetDummy.dummyRoom2(); break;
                    case 3: room = GetDummy.dummyRoom3(); break;
                }
            }
            messageBuilder.append(room.getDescription()).append(". ");
        }

        if (!gameSession.getMonsters().isEmpty()) {
            messageBuilder.append("There's also ");
            int count = gameSession.getMonsters().size();
            for (int i = 0; i < count; ++i) {
                if (i != 0 && i + 1 == count) {
                    messageBuilder.append("and ");
                }
                messageBuilder.append("a ").append(GetDummy.dummyGoblin().getName()); // TODO: monster static lookup: gameSession.getMonsters().get(i).getMonsterId();
            }
            messageBuilder.append(" here");
        }

        return messageBuilder.toString();
    }
}
