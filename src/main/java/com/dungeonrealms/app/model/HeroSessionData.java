package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeroSessionData {

    @DynamoDBAttribute(attributeName = "Hp")
    private Integer mCurrentHP;

    @DynamoDBAttribute(attributeName = "Mp")
    private Integer mCurrentMana;
}
