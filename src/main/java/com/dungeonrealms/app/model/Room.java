package com.dungeonrealms.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class Room {
    private final String mId;
    private final String mTitle;
    private final String mDescription;
    private final List<String> mTrapIds;
    private final List<String> mMonsterIds;
    private final List<String> mTreasureIds;
    private final Map<String, String> mExits;

    public Room(@JsonProperty("id") String id,
                @JsonProperty("title") String title,
                @JsonProperty("description") String description,
                @JsonProperty("trapIds") List<String> trapIds,
                @JsonProperty("monsterIds") List<String> monsterIds,
                @JsonProperty("treasureIds") List<String> treasureIds,
                @JsonProperty("exits") Map<String, String> exits) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mTrapIds = trapIds;
        mMonsterIds = monsterIds;
        mTreasureIds = treasureIds;
        mExits = exits;
    }
}
