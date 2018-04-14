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
    protected Map<String, DungeonAction> getActions() {
        Map<String, DungeonAction> actions = super.getActions();
        actions.put(IntentNames.STATUS, new DungeonAction("check status", mStatusHandler, false));
        actions.put(IntentNames.SEARCH_FOR_TRAPS, new DungeonAction("search for traps", mSearchForTrapsHandler, false));
        return actions;
    }

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
}
