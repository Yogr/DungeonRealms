package com.dungeonrealms.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private final Integer mId;
    private final String mName;
    private final ItemType mType;
    private final Integer mAttack;
    private final Integer mDefense;
}
