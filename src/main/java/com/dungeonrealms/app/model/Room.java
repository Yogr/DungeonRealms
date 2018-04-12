package com.dungeonrealms.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Room {
    private final String mId;
    private final String mTitle;
    private final String mDescription;
    private final RoomType mRoomType;
    private final List<String> mExitIds;
    private final Map<String, String> mExits;
    private final String mLinkAreaId;
    private final String mLinkRoomId;
    private final String mSpawnTableId;
    //private final List<String> mTrapIds;
    //private final List<String> mTreasureIds;

    public Room(@JsonProperty("ID") String id,
                @JsonProperty("Name") String title,
                @JsonProperty("Description") String description,
                @JsonProperty("RoomType") RoomType roomType,
                @JsonProperty("ExitDirections") List<String> exitIds,
                @JsonProperty("Exits") Map<String, String> exits,
                @JsonProperty("LinkID") String linkAreaId,
                @JsonProperty("LinkStartRoomID") String linkRoomId,
                @JsonProperty("SpawnTableID") String spawnTableId) {
        mId = id;
        mTitle = title;
        mDescription = description;
        //mTrapIds = trapIds;
        //mTreasureIds = treasureIds;
        mExitIds = exitIds;
        mExits = exits;
        mLinkAreaId = linkAreaId;
        mLinkRoomId = linkRoomId;
        mRoomType = roomType;
        mSpawnTableId = spawnTableId;
    }

    public enum RoomType {
        DUNGEON,
        TOWN,
        SHOP,
        LINK_TO_AREA,
        ACADEMY
    }

}

