package com.dungeonrealms.app.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Room {
    private final String mId;
    private final String mTitle;
    private final String mDescription;
    private final Map<String, String> mExits;
}
