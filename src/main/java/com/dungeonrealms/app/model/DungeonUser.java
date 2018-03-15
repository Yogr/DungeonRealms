package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@DynamoDBTable(tableName = "DungeonRealms.Users")
public class DungeonUser extends BaseModel {

    @DynamoDBHashKey(attributeName = "UserId")
    public String getId() { return mId; }
    public void setId(String id) { mId = id; }

    @DynamoDBAttribute(attributeName = "GameSession")
    private GameSession mGameSession;

    @DynamoDBAttribute(attributeName = "Heroes")
    private List<Hero> mHeroes;

    public DungeonUser() {
        mId = "";
        mGameSession = new GameSession();
        mHeroes = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public DungeonUser(LinkedHashMap<String, Object> dataMap) {
        mId = (String) dataMap.get("id");
        mGameSession = new GameSession((LinkedHashMap<String, Object>) dataMap.get("gameSession"));
        mHeroes = new ArrayList<>();

        List<Object> tempList = (List<Object>) dataMap.get("heroes");
        for (Object o : tempList) {
            Hero hero = new Hero((LinkedHashMap<String, Object>) o);
            mHeroes.add(hero);
        }
    }
}
