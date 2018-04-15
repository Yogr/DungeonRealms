package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.*;
import com.dungeonrealms.app.speech.Responses;

import com.dungeonrealms.app.util.DungeonUtils;
import lombok.Getter;

import java.util.List;
import java.util.Random;

public class Combat {



    public static String doCombatTurn(CombatAction action, DungeonUser user, HeroInstance heroInstance, MonsterInstance monsterInstance) {
        GameSession gameSession = user.getGameSession();
        StringBuilder combatResult = new StringBuilder();

        int result = action.doAction(gameSession, heroInstance, monsterInstance);
        Monster monster = GameResources.getInstance().getMonsters().get(monsterInstance.getMonsterId());
        if (result > 0) {
            combatResult.append(String.format(Responses.DEALT_DAMAGE, monster.getName(), result));
        } else {
            combatResult.append(String.format(Responses.ATTACK_MISS, monster.getName()));
        }

        if (monsterInstance.getCurrentHP() <= 0) {
            // Monster die, add text
            gameSession.getMonsters().remove(monsterInstance);
            // If no more monsters, change state from COMBAT -> DUNGEON
            if (gameSession.getMonsters().isEmpty()) {
                gameSession.setGameState(GameState.DUNGEON);
                gameSession.setCurrentRoomCleared();
            }

            combatResult.append(String.format(Responses.ENEMY_DEFEATED, monster.getName()));

            RandomTable.LootTable lootTable = GameResources.getInstance().getLootTables().get(monster.getLootTableId());
            if (lootTable != null) {
                List<Item> loot = lootTable.draw();
                int numItemsFound = loot.size();
                int goldMax = 3 + (monster.getLevel() * monster.getLevel());
                int goldFound = new Random().nextInt(goldMax);
                user.modifyGold(goldFound);
                if (numItemsFound > 0 || goldFound > 0) {
                    combatResult.append("You found ");
                }
                for (int i = 0; i < numItemsFound; ++i) {
                    Item item = loot.get(i);
                    Inventory.addItemToBackpack(item.getId(), user);

                    if (i != 0) {
                        if (i + 1 == loot.size() && goldFound == 0) {
                            combatResult.append(" and ");
                        } else {
                            combatResult.append(", ");
                        }
                    }
                    String article = DungeonUtils.startsWithVowel(item.getName()) ? "an " : "a ";
                    combatResult.append(article).append(item.getName());
                }
                if (goldFound > 0) {
                    if (numItemsFound > 0) {
                        combatResult.append(" and ");
                    }
                    combatResult.append(goldFound).append(" gold coins. ");
                } else if (numItemsFound > 0) {
                    combatResult.append(". ");
                }
            }
        }

        Hero hero = user.findHeroByName(heroInstance.getName());
        int experienceGain = Level.values()[monster.getLevel()-1].getExpRewardForMonster();
        String gainExpString = String.format(Responses.GAIN_EXPERIENCE, hero.getName(), experienceGain);
        combatResult.append(gainExpString);
        if (hero.gainExperience(experienceGain)) {
            String levelUpString = String.format(Responses.LEVEL_UP, hero.getName(), hero.getLevel());
            combatResult.append(levelUpString);
        }

        List<HeroInstance> heroes = gameSession.getHeroInstances();
        int i = 0;
        for (; i < heroes.size(); ++i) {
            if (heroInstance == heroes.get(i)) {
                i++;
                break;
            }
        }
        boolean heroesTurnDone = i == heroes.size();
        if (heroesTurnDone) {
            String enemyCombatResult = doEnemyCombat(gameSession);
            combatResult.append(enemyCombatResult);
        } else {
            combatResult.append(String.format(Responses.NEXT_HERO_UP, heroes.get(i).getName()));
        }

        return combatResult.toString();
    }

    private static String doEnemyCombat(GameSession gameSession) {
        StringBuilder enemyResult = new StringBuilder();

        List<MonsterInstance> monsters = gameSession.getMonsters();
        for (MonsterInstance monster : monsters) {
            Monster monsterMeta = GameResources.getInstance().getMonsters().get(monster.getMonsterId());
            int randomHeroIndex = new Random().nextInt(gameSession.getHeroInstances().size());
            HeroInstance targetHero = gameSession.getHeroInstances().get(randomHeroIndex);
            int result = getAttack().doAction(gameSession, monster, targetHero);
            if (result > 0) {
                enemyResult.append(String.format(Responses.ENEMY_HIT_HERO, monsterMeta.getName(), targetHero.getName(), result));
            } else {
                enemyResult.append(String.format(Responses.ENEMY_MISS_HERO, monsterMeta.getName(), targetHero.getName()));
            }
        }

        return enemyResult.toString();
    }

    @Getter
    private static CombatAction mAttack = (GameSession gameSession, FighterInstance actor, FighterInstance target) -> {
        int attack = 0, defense = 0;

        // Get attack value for either hero or monster
        if (actor instanceof HeroInstance) {
            attack = new Random().nextInt(((HeroInstance) actor).getAttack()+1);
        }
        if (actor instanceof MonsterInstance) {
            String monsterId = ((MonsterInstance) actor).getMonsterId();
            Monster monster = GameResources.getInstance().getMonsters().get(monsterId);
            attack = new Random().nextInt(monster.getAttack()+1);
        }

        // Get defense value for either hero or monster
        if (target instanceof HeroInstance) {
            defense = new Random().nextInt(((HeroInstance) target).getDefense()+1);
        }
        if (target instanceof MonsterInstance) {
            String monsterId = ((MonsterInstance) target).getMonsterId();
            Monster monster = GameResources.getInstance().getMonsters().get(monsterId);
            defense = new Random().nextInt(monster.getDefense()+1);
        }

        // TODO: Come up with better attack / defense calculations
        int damage = Math.max(0, attack-defense);
        target.takeDamage(damage);
        return damage;
    };

    public interface CombatAction {
        int doAction(GameSession gameSession, FighterInstance actor, FighterInstance target);
    }
}
