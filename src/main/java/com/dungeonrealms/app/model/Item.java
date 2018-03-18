package com.dungeonrealms.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private final String mId;
    private final String mName;
    private final ItemType mType;
    private final Integer mAttack;
    private final Integer mDefense;
    private final Integer mSpellpower;

    public Item(@JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("type") ItemType type,
                @JsonProperty("attack") Integer attack,
                @JsonProperty("defense") Integer defense,
                @JsonProperty("spellpower") Integer spellpower) {
        mId = id;
        mName = name;
        mType = type;
        mAttack = attack;
        mDefense = defense;
        mSpellpower = spellpower;
    }
}
