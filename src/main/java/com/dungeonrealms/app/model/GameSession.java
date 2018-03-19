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

    @DynamoDBAttribute
    private GameState mGameState;

    @DynamoDBAttribute
    private String mDungeonId;

    @DynamoDBAttribute
    private String mRoomId;

    @DynamoDBAttribute
    private List<MonsterInstance> mMonsters;

    @DynamoDBAttribute
    private List<HeroInstance> mHeroInstances;

    @DynamoDBAttribute
    private Integer mCurrentHeroTurn;

    public GameSession() {
        mGameState = GameState.TOWN;
        mDungeonId = BaseModel.INVALID;
        mRoomId = BaseModel.INVALID;

        mMonsters = new ArrayList<>();
        mHeroInstances = new ArrayList<>();

        mCurrentHeroTurn = 0;
    }

    @SuppressWarnings("unchecked")
    public GameSession(LinkedHashMap<String, Object> dataMap) {
        mGameState = GameState.valueOf((String) dataMap.get("gameState"));
        mDungeonId = (String) dataMap.get("dungeonId");
        mRoomId = (String) dataMap.get("roomId");
        mCurrentHeroTurn = (Integer) dataMap.get("currentHeroTurn");

        mMonsters = new ArrayList<>();
        List<Object> tempMonsterList = (List<Object>) dataMap.get("monsters");
        for (Object o : tempMonsterList) {
            MonsterInstance monster = new MonsterInstance((LinkedHashMap<String, Object>) o);
            mMonsters.add(monster);
        }

        mHeroInstances = new ArrayList<>();
        List<Object> tempHeroList = (List<Object>) dataMap.get("heroInstances");
        for (Object o : tempHeroList) {
            HeroInstance hero = new HeroInstance((LinkedHashMap<String, Object>) o);
            mHeroInstances.add(hero);
        }
    }

    public void clearDungeonVars() {
        mGameState = GameState.TOWN;
        mDungeonId = BaseModel.INVALID;
        mRoomId = BaseModel.INVALID;
        mCurrentHeroTurn = 0;
        mMonsters.clear();
        mHeroInstances.clear();
    }
}
