package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.Area;
import com.dungeonrealms.app.model.Dungeon;
import com.dungeonrealms.app.model.Item;
import com.dungeonrealms.app.model.Monster;
import com.dungeonrealms.app.model.RandomTable.LootTable;
import com.dungeonrealms.app.model.RandomTable.SpawnTable;
import com.dungeonrealms.app.util.Config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


@Getter
public class GameResources {

    @Getter(lazy = true)
    private final static GameResources sInstance = new GameResources();

    private final Map<String, Area> mAreas;
    private final Map<String, Monster> mMonsters;
    private final Map<String, Item> mItems;
    private final Map<String, LootTable> mLootTables;
    private final Map<String, SpawnTable> mSpawnTables;

    private GameResources() {
        mAreas = loadResourceMap("data/areas.json", Area.class);
        mMonsters = loadResourceMap("data/monsters.json", Monster.class);
        mItems = loadResourceMap("data/items.json", Item.class);
        mLootTables = loadResourceMap("data/loottables.json", LootTable.class);
        mSpawnTables = loadResourceMap("data/spawntables.json", SpawnTable.class);
    }

    @SneakyThrows
    private <T> T loadResourceMap(String fileName, Class<?> clazz) {
        InputStream resource = getClass().getClassLoader().getResourceAsStream(fileName);
        ObjectMapper mapper = Config.getInstance().getJacksonMapper();
        JavaType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, clazz);
        return mapper.readValue(resource, type);
    }

}
