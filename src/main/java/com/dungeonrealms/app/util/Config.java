package com.dungeonrealms.app.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class Config {
    @Getter(lazy=true)
    private final static Config sInstance = new Config();

    private AmazonDynamoDB mDbClient = AmazonDynamoDBClientBuilder.standard().build();
    private DynamoDBMapper mDbmapper =  new DynamoDBMapper(mDbClient);
    private ObjectMapper mJacksonMapper = new ObjectMapper();

    public DynamoDBMapper getDBMapper() {
        return mDbmapper;
    }

    public ObjectMapper getJacksonMapper() {
        return mJacksonMapper;
    }

}
