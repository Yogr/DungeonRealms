package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.GameConstants;
import com.dungeonrealms.app.game.CreateHero;
import com.dungeonrealms.app.model.*;
import com.dungeonrealms.app.speech.*;

import java.util.HashMap;
import java.util.Map;

public class CreateStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, ActionHandler> getActions() {
        Map<String, ActionHandler> actions = super.getActions();
        actions.put(Actions.CREATE_HERO.getIntentName(), mCreateHeroHandler);
        return actions;
    }

    private ActionHandler mCreateHeroHandler = (Session session, String intentName, Intent intent) -> {
        Slot heroNameSlot = intent.getSlot(SlotNames.HERO);
        if (heroNameSlot != null) {
            String heroName = heroNameSlot.getValue();
            if (!StringUtils.isNullOrEmpty(heroName)) {
                DungeonUser user = (DungeonUser) session.getAttribute(GameConstants.USER);
                if (user == null) {
                    return getAskResponse(CardTitle.CREATE_HERO, "Something went wrong creating your hero");
                }
                Hero hero = CreateHero.create(heroName);
                user.getHeroes().add(hero);

                GameSession gameSession = user.getGameSession();
                gameSession.getHeroData().add(new HeroSessionData());
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
