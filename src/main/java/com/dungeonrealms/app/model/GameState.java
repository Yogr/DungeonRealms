package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import com.dungeonrealms.app.resolver.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
public enum GameState {
    CREATE(new CreateStateResolver()),
    TOWN(new TownStateResolver()),
    SHOP(new ShopStateResolver()),
    DUNGEON(new DungeonStateResolver()),
    COMBAT(new CombatStateResolver()),
    ACADEMY(new AcademyStateResolver());

    private final GameStateResolver mResolver;
}
