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
    private final WearSlot mSlot;
    private final Integer mCost;
    private final Integer mAttack;
    private final Integer mDefense;
    private final Integer mSpellpower;
    private final Integer mHealth;
    private final Integer mMana;

    public Item(@JsonProperty("ID") String id,
                @JsonProperty("Name") String name,
                @JsonProperty("Alias") String alias,
                @JsonProperty("Description") String description,
                @JsonProperty("ItemType") ItemType type,
                @JsonProperty("WearSlot") WearSlot wearSlot,
                @JsonProperty("Worth") Integer cost,
                @JsonProperty("Attack") Integer attack,
                @JsonProperty("Defense") Integer defense,
                @JsonProperty("Spellpower") Integer spellpower,
                @JsonProperty("Health") Integer health,
                @JsonProperty("Mana") Integer mana) {
        mId = id;
        mName = name;
        mAlias = alias;
        mDescription = description;
        mType = type;
        mSlot = wearSlot;
        mCost = cost;
        mAttack = attack;
        mDefense = defense;
        mSpellpower = spellpower;
        mHealth = health;
        mMana = mana;
    }

    public enum ItemType {
        WEAPON,
        ARMOR,
        CONSUMABLE,
        OTHER
    }

    public enum WearSlot {
        NONE,
        WEAPON,
        ARMOR,
        HEAD,
        SHIELD
    }
}
