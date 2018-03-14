package com.dungeonrealms.app.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Dungeon {
    private final String mId;
    private final String mName;
    private final Map<String, Room> mRooms;
}
