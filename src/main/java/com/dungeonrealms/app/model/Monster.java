package com.dungeonrealms.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class Monster {
    private final String mId;
    private final String mName;
    private final String mAlias;
    private final Integer mLevel;
    private final Integer mHealth;
    private final Integer mMana;
    private final Integer mAttack;
    private final Integer mDefense;
    private final Integer mSpellpower;
    private final Integer mLootTableId;
    private final boolean mIsHostile;
    private final boolean mIsNPC;

    public Monster(@JsonProperty("ID") String id,
                   @JsonProperty("Name") String name,
                   @JsonProperty("Alias") String alias,
                   @JsonProperty("Level") Integer level,
                   @JsonProperty("Health") Integer health,
                   @JsonProperty("Mana") Integer mana,
                   @JsonProperty("Attack") Integer attack,
                   @JsonProperty("Defense") Integer defense,
                   @JsonProperty("Spellpower") Integer spellpower,
                   @JsonProperty("LootTableId") Integer lootTableId,
                   @JsonProperty("IsHostile") boolean isHostile,
                   @JsonProperty("IsNPC") boolean isNPC) {
        mId = id;
        mName = name;
        mAlias = alias;
        mLevel = level;
        mHealth = health;
        mMana = mana;
        mAttack = attack;
        mDefense = defense;
        mSpellpower = spellpower;
        mLootTableId = lootTableId;
        mIsHostile = isHostile;
        mIsNPC = isNPC;
    }
}
