package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hero {

    @DynamoDBAttribute(attributeName = "Name")
    private String mName;

    @DynamoDBAttribute(attributeName = "Exp")
    private Integer mExperience;
}
