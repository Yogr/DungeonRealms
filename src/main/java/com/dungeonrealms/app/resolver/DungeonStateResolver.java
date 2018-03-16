package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.game.Navigation;
import com.dungeonrealms.app.model.Dungeon;
import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.Room;
import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.speech.IntentNames;
import com.dungeonrealms.app.speech.SlotNames;
import com.dungeonrealms.app.util.DungeonUtils;

import java.util.Map;

public class DungeonStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, ActionHandler> getActions() {
        Map<String, ActionHandler> actions = super.getActions();
        actions.put(IntentNames.SEARCH_FOR_TRAPS, mSearchForTrapsHandler);
        actions.put(IntentNames.MOVE_ROOM, mMoveRoomHandler);
        return actions;
    }

    private ActionHandler mMoveRoomHandler = (Session session, DungeonUser user, Intent intent) -> {
        Dungeon dungeon = getDungeon(user.getGameSession().getDungeonId());
        Room room = getDungeonRoom(dungeon, user.getGameSession().getRoomId());

        boolean moveSuccessful = false;
        if (dungeon != null && room != null) {
            Slot locationSlot = intent.getSlot(SlotNames.LOCATION);
            if (locationSlot != null) {
                String moveLocation = locationSlot.getValue();
                if (!StringUtils.isNullOrEmpty(moveLocation)) {
                    Integer newRoomId = room.getExits().get(moveLocation);
                    if (newRoomId != null) {
                        if (newRoomId == -1) {
                            // TODO: Exit Dungeon a.k.a. return to Town
                            moveSuccessful = true;
                        } else {
                            moveSuccessful = Navigation.moveToRoom(user, dungeon, newRoomId);
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

        Room currentRoom = getDungeonRoom(dungeon, user.getGameSession().getRoomId());
        return getAskResponse(currentRoom != null ? currentRoom.getTitle() : CardTitle.DUNGEON_REALMS, speechText);
    };

    private ActionHandler mSearchForTrapsHandler = (Session session, DungeonUser user, Intent intent) -> {
        String speechText = "You do not find any traps";
        return getAskResponse(CardTitle.DUNGEON_REALMS, speechText);
    };
}
