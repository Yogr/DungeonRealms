package com.dungeonrealms.app.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Room {
    private final Integer mId;
    private final String mTitle;
    private final String mDescription;
    private final int[] mTrapIds;
    private final int[] mMonsterIds;
    private final int[] mTreasureIds;
    private final Map<String, Integer> mExits;
}
