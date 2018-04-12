package com.dungeonrealms.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Area {
    private final String mId;
    private final String mName;
    private final int mRoomCount;
    private final List<Room> mRooms;

    public Area(@JsonProperty("ID") String id,
                   @JsonProperty("Name") String name,
                   @JsonProperty("RoomCount") int roomCount,
                   @JsonProperty("Rooms") List<Room> rooms) {
        mId = id;
        mName = name;
        mRoomCount = roomCount;
        mRooms = rooms;
    }

    public Room getRoomById(String id) {
        for (Room r : mRooms) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }
}
