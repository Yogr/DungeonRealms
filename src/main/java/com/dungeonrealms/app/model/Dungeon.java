package com.dungeonrealms.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Dungeon {
    private final String mId;
    private final String mName;
    private final Map<String, Room> mRooms;

    public Dungeon(@JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("rooms") Map<String, Room> rooms) {
        mId = id;
        mName = name;
        mRooms = rooms;
    }
}
