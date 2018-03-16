package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.UUID;

@Getter
@Setter
@DynamoDBDocument
public class MonsterInstance {

    @DynamoDBAttribute(attributeName = "instanceId")
    private String mInstanceId;

    @DynamoDBAttribute(attributeName = "monsterId")
    private Integer mMonsterId;

    @DynamoDBAttribute(attributeName = "currentHP")
    private Integer mCurrentHP;

    public MonsterInstance() {
        mInstanceId = "";
        mMonsterId = -1;
        mCurrentHP = 3;
    }

    public MonsterInstance(Monster monster) {
        mInstanceId = UUID.randomUUID().toString();
        mMonsterId = monster.getId();
        mCurrentHP = monster.getHitPoints();
    }

    public MonsterInstance(LinkedHashMap<String, Object> dataMap) {
        mInstanceId = (String) dataMap.get("instanceId");
        mMonsterId = (Integer) dataMap.get("monsterId");
        mCurrentHP = (Integer) dataMap.get("currentHP");
    }
}
