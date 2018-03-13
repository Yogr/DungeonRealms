package com.dungeonrealms.app.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@DynamoDBTable(tableName = "DungeonRealms.Users")
public class DungeonUser extends DungeonBaseModel {

    @DynamoDBHashKey(attributeName = "UserId")
    public String getId() { return mId; }
    public void setId(String id) { mId = id; }

    @DynamoDBAttribute(attributeName = "SessionId")
    private String mSessionId;

    @DynamoDBAttribute(attributeName = "HeroIdList")
    private List<String> mHeroIds;
}
