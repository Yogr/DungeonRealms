package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "DungeonRealms.Heroes")
public class Hero extends DungeonBaseModel {

    @DynamoDBHashKey(attributeName = "HeroId")
    public String getId() { return mId; }
    public void setId(String id) { mId = id; }

    @DynamoDBHashKey(attributeName = "Name")
    private String mName;

    @DynamoDBHashKey(attributeName = "Experience")
    private Integer mExperience;
}
