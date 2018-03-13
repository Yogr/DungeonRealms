package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "DungeonRealms.Sessions")
public class GameSession extends DungeonBaseModel {

    @DynamoDBHashKey(attributeName = "SessionId")
    public String getId() { return mId; }
    public void setId(String id) { mId = id; }

    @DynamoDBAttribute(attributeName = "GameState")
    private GameState mGameState;
}
