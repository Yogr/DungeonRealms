package com.dungeonrealms.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Monster {
    private final Integer mId;
    private final String mName;
    private final Integer mHitPoints;
    private final Integer mAttack;
    private final Integer mDefense;
}
