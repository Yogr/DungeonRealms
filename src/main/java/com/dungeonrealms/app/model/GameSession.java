package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
@DynamoDBDocument
public class GameSession {

    @DynamoDBAttribute(attributeName = "State")
    private GameState mGameState;

    public GameSession() {
        mGameState = GameState.TOWN;
    }

    public GameSession(LinkedHashMap<String, Object> dataMap) {
        mGameState = GameState.valueOf((String) dataMap.get("gameState"));
    }
}
