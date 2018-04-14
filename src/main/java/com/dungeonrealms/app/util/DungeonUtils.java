package com.dungeonrealms.app.util;

import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.model.*;
import com.dungeonrealms.app.game.GameResources;
import com.dungeonrealms.app.game.Navigation;
import com.dungeonrealms.app.resolver.DungeonAction;
import com.dungeonrealms.app.speech.Responses;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DungeonUtils {

    public static String constructFullRoomMessage(GameSession gameSession) {
        return constructFullRoomMessage("", gameSession);
    }

    public static String constructFullRoomMessage(String prefix, GameSession gameSession) {
        StringBuilder messageBuilder = new StringBuilder();
        if (!StringUtils.isNullOrEmpty(prefix)) {
            messageBuilder.append(prefix);
        }

        Area area = Navigation.getArea(gameSession.getAreaId());
        Room room = Navigation.getAreaRoom(area, gameSession.getRoomId());
        messageBuilder.append(room.getDescription());

        if (!gameSession.getMonsters().isEmpty()) {
            messageBuilder.append(" There's also ");
            int count = gameSession.getMonsters().size();
            for (int i = 0; i < count; ++i) {
                if (i != 0) {
                    if (i + 1 == count) {
                        messageBuilder.append(" and ");
                    } else {
                        messageBuilder.append(", ");
                    }
                }
                Monster monster = GameResources.getInstance().getMonsters().get(gameSession.getMonsters().get(i).getMonsterId());
                messageBuilder.append("a ").append(monster.getName());
            }
            messageBuilder.append(" here");
        }

        return messageBuilder.toString();
    }

    public static Area findAreaByName(String areaName) {
        for (Area area : GameResources.getInstance().getAreas().values()) {
            String normalizedName = area.getName().toLowerCase();
            if (areaName.equals(normalizedName)) {
                return area;
            }
        }
        return null;
    }

    public static List<DungeonAction> filterHiddenActions(Collection<DungeonAction> actions) {
        return actions.stream().filter(x -> !x.isHidden()).collect(Collectors.toList());
    }
}
