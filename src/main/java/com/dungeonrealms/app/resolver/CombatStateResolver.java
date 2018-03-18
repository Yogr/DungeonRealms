package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.game.Combat;
import com.dungeonrealms.app.speech.SlotNames;

import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.speech.IntentNames;
import com.dungeonrealms.app.game.GameResources;
import com.dungeonrealms.app.model.Monster;
import com.dungeonrealms.app.model.MonsterInstance;

import java.util.Map;

public class CombatStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, ActionHandler> getActions() {
        Map<String, ActionHandler> actions = super.getActions();

        actions.put(IntentNames.ATTACK, mAttackHandler);

        return actions;
    }

    private ActionHandler mAttackHandler = (Session session, DungeonUser user, Intent intent) -> {
        Slot monsterSlot = intent.getSlot(SlotNames.MONSTER);
        String monsterName = monsterSlot == null ? "" : monsterSlot.getValue();
        for (MonsterInstance monsterInstance : user.getGameSession().getMonsters()) {
            // TODO : Replace with static lookup of monsterInstance
            Monster monster = GameResources.getInstance().getMonsters().get(monsterInstance.getMonsterId());
            if (StringUtils.isNullOrEmpty(monsterName) || monsterName.equals(monster.getName())) {
                // Had no specific monster, or found the monster requested, now do the attack
                String speechText = Combat.doCombat(Combat.getAttackMonster(), user.getHeroes().get(0), monster, monsterInstance);
                return getAskResponse(CardTitle.DUNGEON_REALMS, speechText);
            }
        }
        return getInvalidActionResponse();
    };
}
