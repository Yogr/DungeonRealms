package com.dungeonrealms.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Monster {
    private final String mId;
    private final String mName;
    private final Integer mHitPoints;
    private final Integer mAttack;
    private final Integer mDefense;

    public Monster(@JsonProperty("id") String id,
                   @JsonProperty("name") String name,
                   @JsonProperty("hitPoints") Integer hitPoints,
                   @JsonProperty("attack") Integer attack,
                   @JsonProperty("defense") Integer defense) {
        mId = id;
        mName = name;
        mHitPoints = hitPoints;
        mAttack = attack;
        mDefense = defense;
    }
}
