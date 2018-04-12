package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Slot;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.game.GameResources;
import com.dungeonrealms.app.game.GameSessionManager;
import com.dungeonrealms.app.game.Navigation;
import com.dungeonrealms.app.model.*;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.speech.IntentNames;
import com.dungeonrealms.app.speech.SlotNames;
import com.dungeonrealms.app.util.DungeonUtils;

import java.util.Map;

public class CreateStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, ActionHandler> getActions() {
        Map<String, ActionHandler> actions = super.getActions();
        actions.put(IntentNames.CREATE_HERO, mCreateHeroHandler);
        return actions;
    }

    private ActionHandler mCreateHeroHandler = (session, user, intent) -> {
        Slot heroNameSlot = intent.getSlot(SlotNames.HERO);
        if (heroNameSlot != null) {
            String heroName = heroNameSlot.getValue();
            if (!StringUtils.isNullOrEmpty(heroName)) {
                if (user == null) {
                    return getAskResponse(CardTitle.CREATE_HERO, "Something went wrong creating your hero");
                }
                Hero hero = Hero.createNewHero(heroName);
                user.getHeroes().add(hero);
                GameSession gameSession = user.getGameSession();
                Area startArea = GameResources.getInstance().getAreas().get("1");
                Navigation.moveToArea(user, startArea);

                GameSessionManager.SaveGameSession(session, user);

                String speechText = String.format(Responses.HELLO_NEW_HERO, heroName);

                speechText = DungeonUtils.constructFullRoomMessage(speechText, gameSession);

                return getPromptedAskResponse(CardTitle.GOBLIN_PRISON, speechText);
            }
        }
        return getAskResponse(CardTitle.CREATE_HERO, "No name provided");
    };
}
