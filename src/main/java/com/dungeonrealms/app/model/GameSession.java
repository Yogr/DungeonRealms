package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
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
    private String mAreaId;

    @DynamoDBAttribute
    private String mRoomId;

    @DynamoDBAttribute
    private List<MonsterInstance> mMonsters;

    @DynamoDBAttribute
    private List<HeroInstance> mHeroInstances;

    @DynamoDBAttribute
    private List<String> mClearedRoomIds;

    @DynamoDBAttribute
    private Integer mCurrentHeroTurn;

    public GameSession() {
        mGameState = GameState.TOWN;
        mAreaId = BaseModel.INVALID;
        mRoomId = BaseModel.INVALID;

        mMonsters = new ArrayList<>();
        mHeroInstances = new ArrayList<>();
        mClearedRoomIds = new ArrayList<>();

        mCurrentHeroTurn = 0;
    }

    @SuppressWarnings("unchecked")
    public GameSession(LinkedHashMap<String, Object> dataMap) {
        mGameState = GameState.valueOf((String) dataMap.get("gameState"));
        mAreaId = (String) dataMap.get("areaId");
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

        mClearedRoomIds = new ArrayList<>();
        List<Object> tempClearedRoomList = (List<Object>) dataMap.get("clearedRoomIds");
        for (Object o : tempClearedRoomList) {
            String clearedRoomId = (String) o;
            mClearedRoomIds.add(clearedRoomId);
        }
    }

    public void clearDungeonSession() {
        mCurrentHeroTurn = 0;
        mMonsters.clear();
        mClearedRoomIds.clear();
        mHeroInstances.clear();
    }

    public void startDungeonSession(List<Hero> heroes) {
        for (Hero hero : heroes) {
            mHeroInstances.add(new HeroInstance(hero));
        }
        mCurrentHeroTurn = 0;
    }

    public void setCurrentRoomCleared() {
        mClearedRoomIds.add(mRoomId);
    }

    public void setGameStateByRoomType(Room.RoomType roomType) {
        switch(roomType) {
            case SHOP: mGameState = GameState.SHOP; break;
            case TOWN: mGameState = GameState.TOWN; break;
            case DUNGEON: mGameState = GameState.DUNGEON; break;
            default: break;
        }
    }
}
