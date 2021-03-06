package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@DynamoDBTable(tableName = "DungeonRealms.Users")
public class DungeonUser extends BaseModel {

    @DynamoDBHashKey(attributeName = "UserId")
    public String getId() { return mId; }
    public void setId(String id) { mId = id; }

    @DynamoDBAttribute
    private GameSession mGameSession;

    @DynamoDBAttribute
    private List<Hero> mHeroes;

    @DynamoDBAttribute
    private Integer mGold;

    @DynamoDBAttribute(attributeName = "backpack")
    private Map<String, Integer> mBackpack;

    public DungeonUser() {
        mId = "";
        mGameSession = new GameSession();
        mHeroes = new ArrayList<>();
        mGold = 100;
        mBackpack = new LinkedHashMap<>();
    }

    @SuppressWarnings("unchecked")
    public DungeonUser(LinkedHashMap<String, Object> dataMap) {
        mId = (String) dataMap.get("id");
        mGameSession = new GameSession((LinkedHashMap<String, Object>) dataMap.get("gameSession"));
        mGold = (Integer) dataMap.get("gold");

        mHeroes = new ArrayList<>();
        List<Object> tempList = (List<Object>) dataMap.get("heroes");
        for (Object o : tempList) {
            Hero hero = new Hero((LinkedHashMap<String, Object>) o);
            mHeroes.add(hero);
        }

        mBackpack = (LinkedHashMap<String, Integer>) dataMap.get("backpack");
    }

    public Hero findHeroByName(String heroName) {
        for (Hero hero : getHeroes()) {
            if (hero.getName().equals(heroName)) {
                return hero;
            }
        }
        return null;
    }

    public void modifyGold(int amount) {
        mGold += amount;
    }
}
