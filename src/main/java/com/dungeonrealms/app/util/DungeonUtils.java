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
        messageBuilder = messageBuilder.append(prefix).append(". ");

        if (gameSession.getGameState() == GameState.DUNGEON) {
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
            messageBuilder = messageBuilder.append(room.getDescription()).append(". ");
        }

        if (!gameSession.getMonsters().isEmpty()) {
            messageBuilder = messageBuilder.append("There's also ");
            int count = gameSession.getMonsters().size();
            for (int i = 0; i < count; ++i) {
                if (i != 0 && i + 1 == count) {
                    messageBuilder = messageBuilder.append("and ");
                }
                messageBuilder = messageBuilder.append("a ").append(GetDummy.dummyGoblin().getName()); // TODO: monster static lookup: gameSession.getMonsters().get(i).getMonsterId();
            }
        }

        return messageBuilder.toString();
    }
}