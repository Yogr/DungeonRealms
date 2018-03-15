package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@DynamoDBDocument
public class Hero {

    @DynamoDBAttribute(attributeName = "Name")
    private String mName;

    @DynamoDBAttribute(attributeName = "Exp")
    private Integer mExperience;

    @Getter(AccessLevel.PRIVATE)
    @DynamoDBAttribute(attributeName = "Hp")
    private Integer mHitpointBase;

    @Getter(AccessLevel.PRIVATE)
    @DynamoDBAttribute(attributeName = "Mp")
    private Integer mManaBase;

    @Getter(AccessLevel.PRIVATE)
    @DynamoDBAttribute(attributeName = "Inventory")
    private Map<String, Integer> mInventoryMap;

/*    // TODO: Write/read only from Session
    private Integer mCurrentHp;

    // TODO: Write/read only from Session
    private Integer mCurrentMana;

    // TODO: Do not write this to dynamoDB or Session, loaded from raw json
    private List<Item> mItems;

    // TODO: Do not write this to dynamoDB or Session it is calculated
    private Integer mAttack;
    // TODO: Do not write this to dynamoDB or Session it is calculated
    private Integer mDefense; */

    public Hero() {
        mName = "Adventurer";
        mExperience = 0;
        mHitpointBase = 10;
        mManaBase = 10;
        //mCurrentHp = mHitpointBase;
        //mCurrentMana = mManaBase;
        //mAttack = 1;
        //mDefense = 1;
        mInventoryMap = new LinkedHashMap<>();
        //mItems = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public Hero(LinkedHashMap<String, Object> dataMap) {
        mName = (String) dataMap.get("name");
        mExperience = (Integer) dataMap.get("experience");
        mHitpointBase = (Integer) dataMap.get("hitpointBase");
        mManaBase = (Integer) dataMap.get("manaBase");
        mInventoryMap = (LinkedHashMap<String, Integer>) dataMap.get("inventory");
        //mItems = new ArrayList<>();
        // TODO: iterate inventory map and pull items from raw json
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
