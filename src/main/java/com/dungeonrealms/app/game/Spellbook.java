package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.FighterInstance;
import com.dungeonrealms.app.model.GameSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.dungeonrealms.app.game.Combat.CombatAction;

import java.util.Random;

@Getter
@RequiredArgsConstructor
public enum Spellbook {
    MAGIC_MISSILE("magic missile", new MagicMissile(1)),
    FIREBALL("fireball", new Fireball(1)),
    HEAL("heal", new Heal(1));

    private final String mName;
    private final Spell mSpell;

    private static class MagicMissile extends Spell {
        MagicMissile(int manaCost) { super(manaCost); }

        @Override
        protected int castSpell(GameSession gameSession, FighterInstance actor, FighterInstance target) {
            int power = new Random().nextInt(2);
            target.setCurrentHP(target.getCurrentHP() - power);
            return power;
        }
    }

    private static class Fireball extends Spell {
        Fireball(int manaCost) { super(manaCost); }

        @Override
        protected int castSpell(GameSession gameSession, FighterInstance actor, FighterInstance target) {
            int power = new Random().nextInt(3 /*spellPower?*/);
            target.setCurrentHP(target.getCurrentHP() - power);
            return power;
        }
    }

    private static class Heal extends Spell {
        Heal(int manaCost) { super(manaCost); }

        @Override
        protected int castSpell(GameSession gameSession, FighterInstance actor, FighterInstance target) {
            int power = new Random().nextInt(2);
            target.setCurrentHP(target.getCurrentHP() + power);
            return power;
        }
    }

    @Getter
    @RequiredArgsConstructor
    private static abstract class Spell implements CombatAction {
        private final int mManaCost;

        @Override
        public int doAction(GameSession gameSession, FighterInstance actor, FighterInstance target) {
            actor.useMana(mManaCost);
            return castSpell(gameSession, actor, target);
        }

        protected abstract int castSpell(GameSession gameSession, FighterInstance caster, FighterInstance target);
    }
}
