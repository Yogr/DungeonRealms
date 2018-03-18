package com.dungeonrealms.app.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class Config {

    @Getter(lazy = true)
    private final static Config sInstance = new Config();

    private AmazonDynamoDB mDbClient;
    @Getter
    private DynamoDBMapper mDbMapper;
    @Getter
    private ObjectMapper mJacksonMapper;

    private Config() {
        mDbClient = AmazonDynamoDBClientBuilder.standard().build();
        mDbMapper =  new DynamoDBMapper(mDbClient);
        mJacksonMapper = new ObjectMapper();
    }

}
