package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.game.Combat;
import com.dungeonrealms.app.model.*;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.speech.SlotNames;

import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.speech.IntentNames;
import com.dungeonrealms.app.game.GameResources;

import java.util.Map;

public class CombatStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, ActionHandler> getActions() {
        Map<String, ActionHandler> actions = super.getActions();

        actions.put(IntentNames.ATTACK, mAttackHandler);
        actions.put(IntentNames.STATUS, mStatusHandler);

        return actions;
    }

    private ActionHandler mAttackHandler = (Session session, DungeonUser user, Intent intent) -> {
        Slot monsterSlot = intent.getSlot(SlotNames.MONSTER);
        String monsterName = monsterSlot == null ? "" : monsterSlot.getValue();
        for (MonsterInstance monsterInstance : user.getGameSession().getMonsters()) {
            Monster monster = GameResources.getInstance().getMonsters().get(monsterInstance.getMonsterId());
            if (StringUtils.isNullOrEmpty(monsterName) || monsterName.equals(monster.getName())) {
                // Had no specific monster, or found the monster requested, now do the attack
                Combat.CombatAction action = Combat.getAttack();
                GameSession gameSession = user.getGameSession();
                HeroInstance attackingHero = gameSession.getHeroInstances().get(0);
                String speechText = Combat.doCombatTurn(action, gameSession, attackingHero, monsterInstance);

                return getAskResponse(CardTitle.DUNGEON_REALMS, speechText);
            }
        }
        return getInvalidActionResponse();
    };

    private ActionHandler mStatusHandler = (Session session, DungeonUser user, Intent intent) -> {
        StringBuilder speechText = new StringBuilder();
        for (HeroInstance hero : user.getGameSession().getHeroInstances()) {
            speechText.append(String.format(Responses.HERO_STATUS, hero.getName(), hero.getCurrentHP(), hero.getCurrentMana()));
        }
        return getAskResponse(CardTitle.DUNGEON_REALMS, speechText.toString());
    };
}
