package com.dungeonrealms.app.resolver;

import com.dungeonrealms.app.speech.IntentNames;

import java.util.HashMap;
import java.util.Map;

public class AcademyStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, DungeonAction> getActions() {
        Map<String, DungeonAction> actions = new HashMap<>();
        actions.put(IntentNames.MOVE_ROOM, new DungeonAction("go", mMoveRoomHandler));
        actions.put(IntentNames.LOOK, new DungeonAction("look", mLookHandler));
        actions.put(IntentNames.GOLD_COUNT, new DungeonAction("wealth", mGoldCountHandler));
        actions.put(IntentNames.HERO_DESCRIPTION, new DungeonAction("character", mDescribeHeroHandler));
        actions.put(IntentNames.ITEM_DESCRIPTION, new DungeonAction("look at item", mDescribeItemHandler));
        actions.put(IntentNames.CHECK_INVENTORY, new DungeonAction("inventory", mBackpackHandler));
        return actions;
    }
}
