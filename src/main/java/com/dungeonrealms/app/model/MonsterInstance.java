package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
@DynamoDBDocument
public class MonsterInstance extends FighterInstance {

    public static final String INVALID = "-1";

    @DynamoDBAttribute
    private String mMonsterId;

    public MonsterInstance() {
        mMonsterId = INVALID;
    }

    public MonsterInstance(Monster monster) {
        mMonsterId = monster.getId();
        setCurrentHP(monster.getHealth());
    }

    public MonsterInstance(LinkedHashMap<String, Object> dataMap) {
        mMonsterId = (String) dataMap.get("monsterId");
        setCurrentHP((Integer) dataMap.get("currentHP"));
        setCurrentMana((Integer) dataMap.get("currentMana"));
    }
}
