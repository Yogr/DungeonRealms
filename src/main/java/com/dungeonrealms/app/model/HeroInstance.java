package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.dungeonrealms.app.game.GameResources;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
@DynamoDBDocument
public class HeroInstance extends FighterInstance {

    @DynamoDBAttribute
    private String mName;

    @DynamoDBAttribute
    private Integer mAttack;

    @DynamoDBAttribute
    private Integer mDefense;

    @DynamoDBAttribute
    private Integer mSpellpower;

    public HeroInstance() {
        mName = "";
        mAttack = 1;
        mDefense = 0;
        mSpellpower = 1;
    }

    public HeroInstance(Hero hero) {
        mName = hero.getName();
        setCurrentHP(hero.getHitpointBase()); // TODO: Calculate hp/mp bonus for equipment as well
        setCurrentMana(hero.getManaBase());
        calculateValuesFromHeroItems(hero);
    }

    public HeroInstance(LinkedHashMap<String, Object> dataMap) {
        mName = (String) dataMap.get("name");
        mAttack = (Integer) dataMap.get("attack");
        mDefense = (Integer) dataMap.get("defense");
        mSpellpower = (Integer) dataMap.get("spellpower");

        setCurrentHP((Integer) dataMap.get("currentHP"));
        setCurrentMana((Integer) dataMap.get("currentMana"));
    }

    public void calculateValuesFromHeroItems(Hero hero) {
        mAttack = 1;
        mDefense = 1;
        mSpellpower = 1;
        for (String itemId : hero.getEquipment()) {
            if (GameResources.getInstance().getItems().containsKey(itemId)) {
                Item item = GameResources.getInstance().getItems().get(itemId);
                mAttack += item.getAttack();
                mDefense += item.getDefense();
                mSpellpower += item.getSpellpower();
                // TODO: Add to hp/mana
            }
        }
    }
}
