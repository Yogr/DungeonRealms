package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.Hero;
import com.dungeonrealms.app.model.Monster;
import com.dungeonrealms.app.model.MonsterInstance;

import java.util.Random;

public class Combat {

    public static int AttackMonster(Hero hero, Monster monster, MonsterInstance monsterInstance) {
        int attack = new Random().nextInt(3); //hero.getAttack();
        int defense = new Random().nextInt(monster.getDefense());

        // TODO: Come up with better attack / defense calculations
        int damage = Math.max(0, attack-defense);
        // TODO: Make a "take damage" method, create FigherSession base class
        monsterInstance.setCurrentHP(monsterInstance.getCurrentHP() - damage);
        return damage;
    }
}
