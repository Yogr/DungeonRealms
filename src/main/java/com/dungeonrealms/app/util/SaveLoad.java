package com.dungeonrealms.app.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.dungeonrealms.app.model.DungeonBaseModel;
import com.dungeonrealms.app.model.DungeonUser;
import java.util.List;

public class SaveLoad {

    public static DungeonUser LoadUser(String userId) {
        //return new SaveLoadInternal<DungeonUser>().load(DungeonUser.class, new DungeonUser(), userId);
        DungeonUser partitionKey = new DungeonUser();
        partitionKey.setId(userId);
        DynamoDBQueryExpression<DungeonUser> query = new DynamoDBQueryExpression<DungeonUser>().withHashKeyValues(partitionKey);

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDBMapper mapper =  new DynamoDBMapper(client);
        List<DungeonUser> dataList = mapper.query(DungeonUser.class, query);

        if (dataList != null && dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public static void SaveUser(DungeonUser dungeonUser) {
        //new SaveLoadInternal<DungeonUser>().save(dungeonUser);
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDBMapper mapper =  new DynamoDBMapper(client);
        mapper.save(dungeonUser);
    }

    private static class SaveLoadInternal<T extends DungeonBaseModel> {

        @SuppressWarnings("unchecked")
        T load(Class clazz, T partitionKey, String id) {
            partitionKey.setId(id);
            DynamoDBQueryExpression<T> query = new DynamoDBQueryExpression<T>().withHashKeyValues(partitionKey);

            List<T> dataList = getMapper().query(clazz, query);

            if (dataList != null && dataList.size() > 0) {
                return dataList.get(0);
            }
            return null;
        }

        void save(T data) {
            getMapper().save(data);
        }

        private DynamoDBMapper getMapper() {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
            return new DynamoDBMapper(client);
        }
    }
}
