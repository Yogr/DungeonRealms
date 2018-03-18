package com.dungeonrealms.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private final String mId;
    private final String mName;
    private final String mAlias;
    private final String mDescription;
    private final ItemType mType;
    private final Integer mCost;
    private final Integer mAttack;
    private final Integer mDefense;
    private final Integer mSpellpower;

    public Item(@JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("alias") String alias,
                @JsonProperty("description") String description,
                @JsonProperty("type") ItemType type,
                @JsonProperty("cost") Integer cost,
                @JsonProperty("attack") Integer attack,
                @JsonProperty("defense") Integer defense,
                @JsonProperty("spellpower") Integer spellpower) {
        mId = id;
        mName = name;
        mAlias = alias;
        mDescription = description;
        mType = type;
        mCost = cost;
        mAttack = attack;
        mDefense = defense;
        mSpellpower = spellpower;
    }
}
