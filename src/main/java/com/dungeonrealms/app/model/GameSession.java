package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@DynamoDBDocument
public class GameSession {

    @DynamoDBAttribute(attributeName = "state")
    private GameState mGameState;

    @DynamoDBAttribute(attributeName = "dungeonId")
    private Integer mDungeonId;

    @DynamoDBAttribute(attributeName = "roomId")
    private Integer mRoomId;

    @DynamoDBAttribute(attributeName = "monsters")
    private List<MonsterInstance> mMonsters;

    public GameSession() {
        mGameState = GameState.TOWN;
        mDungeonId = -1;
        mRoomId = -1;

        mMonsters = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public GameSession(LinkedHashMap<String, Object> dataMap) {
        mGameState = GameState.valueOf((String) dataMap.get("gameState"));
        mDungeonId = (Integer) dataMap.get("dungeonId");
        mRoomId = (Integer) dataMap.get("roomId");

        mMonsters = new ArrayList<>();
        List<Object> tempMonsterList = (List<Object>) dataMap.get("monsters");
        for (Object o : tempMonsterList) {
            MonsterInstance monster = new MonsterInstance((LinkedHashMap<String, Object>) o);
            mMonsters.add(monster);
        }
    }
}
