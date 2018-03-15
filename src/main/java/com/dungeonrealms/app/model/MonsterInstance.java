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

    @DynamoDBAttribute(attributeName = "InstanceId")
    private String mInstanceId;

    @DynamoDBAttribute(attributeName = "MonsterId")
    private Integer mMonsterId;

    @DynamoDBAttribute(attributeName = "Hp")
    private Integer mCurrentHP;

    // TODO: Figure out how to ignore this ref in dynamo DB and Session
    private Monster mMonster;

    public MonsterInstance(Monster monster) {
        mInstanceId = UUID.randomUUID().toString();
        mMonsterId = monster.getId();
        mCurrentHP = monster.getHitPoints();
        mMonster = monster;
    }

    public MonsterInstance(LinkedHashMap<String, Object> dataMap) {
        mInstanceId = (String) dataMap.get("instanceId");
        mMonsterId = (Integer) dataMap.get("monsterId");
        mCurrentHP = (Integer) dataMap.get("hp");

        // TODO: Implement lookup from raw Json to fetch static monster data
        // TODO: mMonster = SomeDataManger.getMonsterById(mMonsterId);
    }
}
