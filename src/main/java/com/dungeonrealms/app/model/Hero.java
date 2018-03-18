package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.dungeonrealms.app.game.GameResources;
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

    @DynamoDBAttribute(attributeName = "backpack")
    private Map<String, Integer> mBackpack;

    @DynamoDBAttribute(attributeName = "equipment")
    private List<String> mEquipment;

    public Hero() {

    }

    public Hero(String name) {
        mName = name;
        mExperience = 0;
        mHitpointBase = 10;
        mManaBase = 10;
        mBackpack = new LinkedHashMap<>();
        mEquipment = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public Hero(LinkedHashMap<String, Object> dataMap) {
        mName = (String) dataMap.get("name");
        mExperience = (Integer) dataMap.get("experience");
        mHitpointBase = (Integer) dataMap.get("hitpointBase");
        mManaBase = (Integer) dataMap.get("manaBase");
        mBackpack = (LinkedHashMap<String, Integer>) dataMap.get("backpack");
        mEquipment = (List<String>) dataMap.get("equipment");
    }

    public static Hero createNewHero(String name) {
        Hero hero = new Hero(name);

        hero.getEquipment().add("1"); // SHORT SWORD
        hero.getEquipment().add("2"); // LEATHER TUNIC
        hero.getBackpack().put("3", 2); // HEALING POTION

        return hero;
    }
}
