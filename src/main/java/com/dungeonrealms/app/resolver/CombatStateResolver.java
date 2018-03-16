package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.dungeonrealms.app.dummy.GetDummy;
import com.dungeonrealms.app.game.Combat;
import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.Monster;
import com.dungeonrealms.app.model.MonsterInstance;
import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.speech.IntentNames;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.speech.SlotNames;

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
        if (monsterSlot != null) {
            String monsterName = monsterSlot.getValue();
            for (MonsterInstance monsterInstance : user.getGameSession().getMonsters()) {
                // TODO : Replace with static lookup of monsterInstance
                Monster monster = GetDummy.dummyGoblin();
                if (monsterName.equals(monster.getName())) {
                    // Found monster requested, now do the attack
                    int result = Combat.AttackMonster(user.getHeroes().get(0), monster, monsterInstance);
                    String speechText;
                    if (result > 0) {
                        speechText = String.format(Responses.DEALT_DAMAGE, monster.getName(), result);
                    } else {
                        speechText = String.format(Responses.ATTACK_MISS, monster.getName());
                    }

                    if (monsterInstance.getCurrentHP() <= 0) {
                        // Monster die, add text
                        speechText += String.format(Responses.ENEMY_DEFEATED, monster.getName());
                        // TODO: Add Treasure roll & speech for slain monster
                    }

                    // TODO: If no Heroes have next turn, do enemy turn for all (living) monsters in room
                    // TODO: and add their result to the return dialog. IF there IS another Hero up
                    // TODO: add text to tell them it is their turn.

                    return getAskResponse(CardTitle.DUNGEON_REALMS, speechText);
                }
            }
        }
        return getInvalidActionResponse();
    };
}
