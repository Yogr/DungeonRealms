package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
@DynamoDBDocument
public class Hero {

    @DynamoDBAttribute(attributeName = "Name")
    private String mName;

    @DynamoDBAttribute(attributeName = "Exp")
    private Integer mExperience;

    @DynamoDBAttribute(attributeName = "Hp")
    private Integer mCurrentHP;

    @DynamoDBAttribute(attributeName = "Mp")
    private Integer mCurrentMana;

    public Hero() {
        mName = "Adventurer";
        mExperience = 0;
        mCurrentHP = 10;
        mCurrentMana = 10;
    }

    public Hero(LinkedHashMap<String, Object> dataMap) {
        mName = (String) dataMap.get("name");
        mExperience = (Integer) dataMap.get("experience");
        mCurrentHP = (Integer) dataMap.get("currentHP");
        mCurrentMana = (Integer) dataMap.get("currentMana");
    }
}
