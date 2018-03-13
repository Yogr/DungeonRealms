package com.dungeonrealms.app.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.dungeonrealms.app.model.DungeonBaseModel;
import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.GameSession;
import com.dungeonrealms.app.model.Hero;

import java.util.List;

public class SaveLoad {

    public static DungeonUser LoadUser(String userId) {
        return new SaveLoadInternal<DungeonUser>().load(DungeonUser.class, new DungeonUser(), userId);
    }

    public static void SaveUser(DungeonUser dungeonUser) {
        new SaveLoadInternal<DungeonUser>().save(dungeonUser);
    }

    public static GameSession LoadGameSession(String sessionId) {
        return new SaveLoadInternal<GameSession>().load(GameSession.class, new GameSession(), sessionId);
    }

    public static void SaveGameSession(GameSession session) {
        new SaveLoadInternal<GameSession>().save(session);
    }

    public static Hero LoadHero(String heroId) {
        return new SaveLoadInternal<Hero>().load(Hero.class, new Hero(), heroId);
    }

    public static void SaveHero(Hero hero) {
        new SaveLoadInternal<Hero>().save(hero);
    }

    private static class SaveLoadInternal<T extends DungeonBaseModel> {

        @SuppressWarnings("unchecked")
        private T load(Class clazz, T partitionKey, String id) {
            partitionKey.setId(id);
            DynamoDBQueryExpression<T> query = new DynamoDBQueryExpression<T>().withHashKeyValues(partitionKey);

            List<T> dataList = getMapper().query(clazz, query);

            if (dataList != null && dataList.size() > 0) {
                return dataList.get(0);
            }
            return null;
        }

        public void save(T data) {
            getMapper().save(data);
        }

        private static DynamoDBMapper getMapper() {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
            return new DynamoDBMapper(client);
        }
    }
}
