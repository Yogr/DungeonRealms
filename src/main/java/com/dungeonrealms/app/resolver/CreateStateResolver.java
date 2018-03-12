package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.GameConstants;
import com.dungeonrealms.app.speech.*;

import java.util.HashMap;
import java.util.Map;

public class CreateStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, ActionHandler> getActions() {
        Map<String, ActionHandler> actions = new HashMap<>();
        actions.put(Actions.CREATE_HERO.getIntentName(), mCreateHeroHandler);
        return actions;
    }

    private ActionHandler mCreateHeroHandler = (Session session, String intentName, Intent intent) -> {
        Slot heroNameSlot = intent.getSlot(SlotNames.HERO);
        if (heroNameSlot != null) {
            String heroName = heroNameSlot.getValue();
            if (!StringUtils.isNullOrEmpty(heroName)) {
                //CharProfile.setHeroName(heroName);
                session.getAttributes().put(GameConstants.NAME, heroName);
                String speechText = String.format(Responses.HELLO_NEW_HERO, heroName);
                return getPromptedAskResponse(CardTitle.GOBLIN_PRISON, speechText);
            }
        }
        return getAskResponse(CardTitle.CREATE_HERO, "No name provided");
    };
}
