package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.model.*;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.speech.SlotNames;

import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.game.Navigation;
import com.dungeonrealms.app.speech.IntentNames;
import com.dungeonrealms.app.util.DungeonUtils;

import java.util.Map;

public class DungeonStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, ActionHandler> getActions() {
        Map<String, ActionHandler> actions = super.getActions();
        actions.put(IntentNames.STATUS, mStatusHandler);
        actions.put(IntentNames.SEARCH_FOR_TRAPS, mSearchForTrapsHandler);
        actions.put(IntentNames.MOVE_ROOM, mMoveRoomHandler);
        actions.put(IntentNames.LOOK, mLookHandler);
        return actions;
    }

    private ActionHandler mMoveRoomHandler = (Session session, DungeonUser user, Intent intent) -> {
        Dungeon dungeon = Navigation.getDungeon(user.getGameSession().getDungeonId());
        Room room = Navigation.getDungeonRoom(dungeon, user.getGameSession().getRoomId());

        boolean moveSuccessful = false;
        if (dungeon != null && room != null) {
            Slot locationSlot = intent.getSlot(SlotNames.LOCATION);
            if (locationSlot != null) {
                String moveLocation = locationSlot.getValue();
                if (!StringUtils.isNullOrEmpty(moveLocation)) {
                    String newRoomId = room.getExits().get(moveLocation);
                    if (newRoomId != null) {
                        if (newRoomId.equals(BaseModel.INVALID)) {
                            moveSuccessful = Navigation.moveToTown(user.getGameSession());
                        } else {
                            moveSuccessful = Navigation.moveToRoom(user.getGameSession(), dungeon, newRoomId);
                        }
                    }
                }
            }
        }

        String speechText;
        if (moveSuccessful) {
            speechText = DungeonUtils.constructFullRoomMessage(user.getGameSession());
        } else {
            speechText = "You cannot move in that direction";
        }

        Room currentRoom = Navigation.getDungeonRoom(dungeon, user.getGameSession().getRoomId());
        return getAskResponse(currentRoom != null ? currentRoom.getTitle() : CardTitle.DUNGEON_REALMS, speechText);
    };

    private ActionHandler mStatusHandler = (Session session, DungeonUser user, Intent intent) -> {
        StringBuilder speechText = new StringBuilder();
        for (HeroInstance hero : user.getGameSession().getHeroInstances()) {
            speechText.append(String.format(Responses.HERO_STATUS, hero.getName(), hero.getCurrentHP(), hero.getCurrentMana()));
        }
        return getAskResponse(CardTitle.DUNGEON_REALMS, speechText.toString());
    };

    private ActionHandler mSearchForTrapsHandler = (Session session, DungeonUser user, Intent intent) -> {
        String speechText = "You do not find any traps";
        return getAskResponse(CardTitle.DUNGEON_REALMS, speechText);
    };

    private ActionHandler mLookHandler = (Session session, DungeonUser user, Intent intent) -> {
        String speechText = DungeonUtils.constructFullRoomMessage(user.getGameSession());
        Dungeon dungeon = Navigation.getDungeon(user.getGameSession().getDungeonId());
        Room room = null;
        if (dungeon != null) {
            room = Navigation.getDungeonRoom(dungeon, user.getGameSession().getRoomId());
        }
        return getAskResponse(room != null ? room.getTitle() : CardTitle.DUNGEON_REALMS, speechText);
    };
}
