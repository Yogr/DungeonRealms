package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameSession {

    @DynamoDBAttribute(attributeName = "State")
    private GameState mGameState;

    @DynamoDBAttribute(attributeName = "HeroData")
    private List<HeroSessionData> mHeroData;
}
