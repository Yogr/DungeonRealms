package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.Monster;
import com.dungeonrealms.app.model.MonsterInstance;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.model.Hero;

import lombok.Getter;

import java.util.Random;

public class Combat {

    public static String doCombat(CombatAction heroAction, Hero hero, Monster monster, MonsterInstance monsterInstance) {
        StringBuilder combatResult = new StringBuilder();

        int result = heroAction.doAction(hero, monster, monsterInstance);
        if (result > 0) {
            combatResult.append(String.format(Responses.DEALT_DAMAGE, monster.getName(), result));
        } else {
            combatResult.append(String.format(Responses.ATTACK_MISS, monster.getName()));
        }

        if (monsterInstance.getCurrentHP() <= 0) {
            // Monster die, add text
            combatResult.append(String.format(Responses.ENEMY_DEFEATED, monster.getName()));
            // TODO: Add Treasure roll & speech for slain monster
        }

        // TODO: If no Heroes have next turn, do enemy turn for all (living) monsters in room
        // TODO: and add their result to the return dialog. IF there IS another Hero up
        // TODO: add text to tell them it is their turn.

        return combatResult.toString();
    }

    @Getter
    private static CombatAction mAttackMonster = (Hero hero, Monster monster, MonsterInstance monsterInstance) -> {
        int attack = new Random().nextInt(3); //hero.getAttack();
        int defense = new Random().nextInt(monster.getDefense());

        // TODO: Come up with better attack / defense calculations
        int damage = Math.max(0, attack-defense);
        // TODO: Make a "take damage" method, create FigherSession base class
        monsterInstance.setCurrentHP(monsterInstance.getCurrentHP() - damage);
        return damage;
    };

    @Getter
    private static CombatAction mCastFireball = (Hero hero, Monster monster, MonsterInstance monsterInstance) -> {
        int power = new Random().nextInt(3 /*spellPower?*/);
        monsterInstance.setCurrentHP(monsterInstance.getCurrentHP() - power);
        return power;
    };

    public interface CombatAction {
        int doAction(Hero hero, Monster monster, MonsterInstance monsterInstance);
    }
}
