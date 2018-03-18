package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@DynamoDBDocument
public class Hero {

    @DynamoDBAttribute(attributeName = "name")
    private String mName;

    @DynamoDBAttribute(attributeName = "experience")
    private Integer mExperience;

    @DynamoDBAttribute(attributeName = "hitpointBase")
    private Integer mHitpointBase;

    @DynamoDBAttribute(attributeName = "manaBase")
    private Integer mManaBase;

    @DynamoDBAttribute(attributeName = "inventoryMap")
    private Map<String, Integer> mInventoryMap;

    public Hero() {
        mName = "Adventurer";
        mExperience = 0;
        mHitpointBase = 10;
        mManaBase = 10;
        mInventoryMap = new LinkedHashMap<>();
    }

    @SuppressWarnings("unchecked")
    public Hero(LinkedHashMap<String, Object> dataMap) {
        mName = (String) dataMap.get("name");
        mExperience = (Integer) dataMap.get("experience");
        mHitpointBase = (Integer) dataMap.get("hitpointBase");
        mManaBase = (Integer) dataMap.get("manaBase");
        mInventoryMap = (LinkedHashMap<String, Integer>) dataMap.get("inventory");
    }

    /*
    public void addItem(Item item) {
        mItems.add(item);
        modifyAttributesFromItem(item, 1);
    }

    public void removeItem(Item item) {
        if (mItems.contains(item)) {
            mItems.remove(item);
            modifyAttributesFromItem(item, -1);
        }
    }

    private void modifyAttributesFromItem(Item item, int modifier) {
        mAttack += item.getAttack() * modifier;
        mDefense += item.getDefense() * modifier;
        // TODO: hp / mana / other stats ?
    }
    */
}
