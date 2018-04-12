package com.dungeonrealms.app.model;

import com.dungeonrealms.app.game.GameResources;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Data
public abstract class RandomTable<T> {
    private final String mId;
    private final String mName;
    private final List<Integer> mItemChances;
    private final List<String> mItemIds;
    private final Integer mNumberOfTries;

    public RandomTable(
            @JsonProperty("ID") String id,
            @JsonProperty("Name") String name,
            @JsonProperty("ItemChances") List<Integer> itemChances,
            @JsonProperty("ItemIDs") List<String> itemIds,
            @JsonProperty("NumberOfTries") Integer numberOfTries) {
        mId = id;
        mName = name;
        mItemChances = itemChances;
        mItemIds = itemIds;
        mNumberOfTries = numberOfTries;
    }

    public abstract List<T> draw();

    protected List<T> draw(Map<String, T> dataMap) {
        List<T> retList = new ArrayList<>();
        for (String itemId : drawRandomIds()) {
            T item = dataMap.get(itemId);
            if (item != null) {
                retList.add(item);
            }
        }
        return retList;
    }

    private List<String> drawRandomIds() {
        List<String> idList = new ArrayList<>();

        for (int i = 0; i < mNumberOfTries; ++i) {
            int roll = new Random().nextInt(100);
            int chanceRange = 0;
            for (int j = 0; j < mItemChances.size(); ++j) {
                chanceRange += mItemChances.get(j);
                if (roll <= chanceRange) {
                    idList.add(mItemIds.get(j));
                    break;
                }
            }
        }

        return idList;
    }

    public static class SpawnTable extends RandomTable<Monster> {
        public SpawnTable(@JsonProperty("ID") String id,
                          @JsonProperty("Name") String name,
                          @JsonProperty("ItemChances") List<Integer> itemChances,
                          @JsonProperty("ItemIDs") List<String> itemIds,
                          @JsonProperty("NumberOfTries") Integer numberOfTries) {
            super(id, name, itemChances, itemIds, numberOfTries);
        }

        @Override
        public List<Monster> draw() {
            return draw(GameResources.getInstance().getMonsters());
        }
    }

    public static class LootTable extends RandomTable<Item> {
        public LootTable(@JsonProperty("ID") String id,
                         @JsonProperty("Name") String name,
                         @JsonProperty("ItemChances") List<Integer> itemChances,
                         @JsonProperty("ItemIDs") List<String> itemIds,
                         @JsonProperty("NumberOfTries") Integer numberOfTries) {
            super(id, name, itemChances, itemIds, numberOfTries);
        }

        @Override
        public List<Item> draw() {
            return draw(GameResources.getInstance().getItems());
        }
    }
}
