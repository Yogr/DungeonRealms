package com.dungeonrealms.app.speech;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Actions {
    // General
    LOOK(ActionText.LOOK, IntentNames.LOOK),
    GOLD_COUNT(ActionText.GOLD_COUNT, IntentNames.GOLD_COUNT),
    STATUS(ActionText.STATUS, IntentNames.STATUS),
    DESCRIBE_HERO(ActionText.HERO_DESCRIPTION, IntentNames.HERO_DESCRIPTION),
    DESCRIBE_ITEM(ActionText.ITEM_DESCRIPTION, IntentNames.ITEM_DESCRIPTION),

    // Travel
    EXIT_AREA(ActionText.EXIT_AREA, IntentNames.EXIT_AREA),
    GOTO_SHOP(ActionText.GOTO_SHOP, IntentNames.GOTO_SHOP),
    GOTO_DUNGEON(ActionText.GOTO_DUNGEON, IntentNames.GOTO_DUNGEON),
    GOTO_ABILITIES(ActionText.GOTO_ABILITIES, IntentNames.GOTO_ABILITIES),
    GOTO_INVENTORY(ActionText.GOTO_INVENTORY, IntentNames.GOTO_INVENTORY),

    // Create hero
    CREATE_HERO(ActionText.CREATE_HERO, IntentNames.CREATE_HERO),

    // Shop
    BUY_ITEM(ActionText.BUY_ITEM, IntentNames.BUY_ITEM),
    SELL_ITEM(ActionText.SELL_ITEM, IntentNames.SELL_ITEM),

    // Combat
    ATTACK(ActionText.ATTACK, IntentNames.ATTACK),
    CAST_SPELL(ActionText.CAST_SPELL, IntentNames.CAST_SPELL),
    ESCAPE(ActionText.ESCAPE, IntentNames.ESCAPE),
    USE_ITEM(ActionText.USE_ITEM, IntentNames.USE_ITEM),

    // Dungeon (Non-Combat)
    SEARCH_FOR_TRAPS(ActionText.SEARCH_FOR_TRAPS, IntentNames.SEARCH_FOR_TRAPS),
    SEARCH_FOR_TREASURE(ActionText.SEARCH_FOR_TREASURE, IntentNames.SEARCH_FOR_TREASURE),
    DISARM_TRAPS(ActionText.DISARM_TRAPS, IntentNames.DISARM_TRAPS),

    // Amazon defined
    HELP(ActionText.AMAZON_HELP, IntentNames.AMAZON_HELP),
    QUIT(ActionText.AMAZON_STOP, IntentNames.AMAZON_STOP),
    CANCEL(ActionText.AMAZON_CANCEL, IntentNames.AMAZON_CANCEL);

    private final String mActionName;
    private final String mIntentName;
}