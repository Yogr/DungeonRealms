package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.speech.IntentNames;

import java.util.HashMap;
import java.util.Map;

public class DungeonStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, ActionHandler> getActions() {
        Map<String, ActionHandler> actions = super.getActions();
        actions.put(IntentNames.SEARCH_FOR_TRAPS, mSearchForTrapsHandler);
        return actions;
    }

    private ActionHandler mSearchForTrapsHandler = (Session session, String intentName, Intent intent) -> {
        String speechText = "You do not find any traps";
        return getAskResponse(CardTitle.DUNGEON_REALMS, speechText);
    };
}
