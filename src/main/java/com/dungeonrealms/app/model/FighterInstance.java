package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBDocument
public abstract class FighterInstance {

    @DynamoDBAttribute(attributeName = "currentHP")
    private Integer mCurrentHP;

    @DynamoDBAttribute(attributeName = "currentMana")
    private Integer mCurrentMana;

    public FighterInstance() {
        mCurrentHP = 0;
        mCurrentMana = 0;
    }

    public void takeDamage(int amount) {
        mCurrentHP -= amount;
    }

    public void useMana(int amount) {
        mCurrentMana -= amount;
    }
}
