package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.Dungeon;
import com.dungeonrealms.app.model.Item;
import com.dungeonrealms.app.model.Monster;
import com.dungeonrealms.app.util.Config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Map;

import static java.lang.ClassLoader.getSystemResources;

@Getter
public class GameResources {

    @Getter(lazy = true)
    private final static GameResources sInstance = new GameResources();

    private final Map<String, Dungeon> mDungeons;
    private final Map<String, Monster> mMonsters;
    private final Map<String, Item> mItems;

    private GameResources() {
        mDungeons = loadResourceMap("data/dungeons.json", Dungeon.class);
        mMonsters = loadResourceMap("data/monsters.json", Monster.class);
        mItems = loadResourceMap("data/items.json", Item.class);
    }

    @SneakyThrows
    private <T> T loadResourceMap(String fileName, Class<?> clazz) {
        InputStream resource = getClass().getClassLoader().getResourceAsStream(fileName);
        System.out.println("res1 is " + resource);

        if (resource == null) {
            resource = getClass().getClassLoader().getResourceAsStream("/" + fileName);
            System.out.println("res2 is " + resource);
        }

        if (resource == null) {
            resource = getClass().getClassLoader().getResourceAsStream("./" + fileName);
            System.out.println("res3 is " + resource);
        }

        //if (resource == null) {
            Enumeration<URL> enumeration = getSystemResources("./");
            while (enumeration.hasMoreElements()) {
                URL url = enumeration.nextElement();
                System.out.println("Resource: " + url.getFile());
            }

            Enumeration<URL> enumeration2 = getClass().getClassLoader().getResources("./");
            while (enumeration2.hasMoreElements()) {
                URL url = enumeration2.nextElement();
                System.out.println("2Resource: " + url.getFile());
            }
        //}

        if (resource == null) {
            Path path = Paths.get(getClass().getResource("/").toURI());//.resolve(fileName);
            System.out.println("Path is " + path);
            resource = Files.newInputStream(path.resolve("resources/" + fileName));
            System.out.println("Resource is " + resource);
        }

        ObjectMapper mapper = Config.getInstance().getJacksonMapper();
        JavaType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, clazz);

        return mapper.readValue(resource, type);
    }

}
