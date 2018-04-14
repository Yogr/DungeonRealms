package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Slot;
import com.dungeonrealms.app.game.GameResources;
import com.dungeonrealms.app.game.Navigation;
import com.dungeonrealms.app.model.Area;
import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.speech.IntentNames;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.speech.SlotNames;
import com.dungeonrealms.app.util.DungeonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class TownStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, DungeonAction> getActions() {
        Map<String, DungeonAction> actions = super.getActions();

        actions.put(IntentNames.MOVE_ROOM, new DungeonAction("go", mStartDungeonHandler, false));

        return actions;
    }

    private ActionHandler mStartDungeonHandler = (session, user, intent) -> {
        Slot locationSlot = intent.getSlot(SlotNames.LOCATION);
        String location = locationSlot != null ? locationSlot.getValue() : null;

        if (StringUtils.isNotEmpty(location)) {
            Area dungeon = DungeonUtils.findAreaByName(location);
            if (dungeon != null) {
                Navigation.moveToArea(user, dungeon);
                String response = DungeonUtils.constructFullRoomMessage(user.getGameSession());
                return getAskResponse(dungeon.getName(), response);
            }
        }
        return mMoveRoomHandler.handleIntent(session, user, intent);
    };
}
