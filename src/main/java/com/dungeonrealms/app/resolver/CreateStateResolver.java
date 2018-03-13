package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.GameConstants;
import com.dungeonrealms.app.game.CreateHero;
import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.GameSession;
import com.dungeonrealms.app.model.GameState;
import com.dungeonrealms.app.model.Hero;
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
                DungeonUser user = (DungeonUser) session.getAttribute(GameConstants.USER);

                Hero hero = CreateHero.create(heroName);
                user.getHeroes().add(hero);

                GameSession gameSession = user.getGameSession();
                gameSession.setGameState(GameState.DUNGEON);

                //session.setAttribute(GameConstants.USER, user);
                //session.setAttribute(GameConstants.GAME_SESSION, gameSession);
                String speechText = String.format(Responses.HELLO_NEW_HERO, heroName);

                return getPromptedAskResponse(CardTitle.GOBLIN_PRISON, speechText);
            }
        }
        return getAskResponse(CardTitle.CREATE_HERO, "No name provided");
    };
}
