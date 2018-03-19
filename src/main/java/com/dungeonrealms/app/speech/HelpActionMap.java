package com.dungeonrealms.app.speech;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class HelpActionMap {

    @Getter
    private static Map<String, String> mIntentFriendlyName = new HashMap<>();
    static {
        mIntentFriendlyName.put(IntentNames.LOOK, HelpActionMap.LOOK);
        mIntentFriendlyName.put(IntentNames.GOLD_COUNT, HelpActionMap.GOLD_COUNT);
        mIntentFriendlyName.put(IntentNames.MOVE_ROOM, HelpActionMap.GOTO_ROOM);
        mIntentFriendlyName.put(IntentNames.SEARCH_FOR_TRAPS, HelpActionMap.SEARCH_FOR_TRAPS);
        mIntentFriendlyName.put(IntentNames.SEARCH_FOR_TREASURE, HelpActionMap.SEARCH_FOR_TREASURE);
        mIntentFriendlyName.put(IntentNames.DISARM_TRAPS, HelpActionMap.DISARM_TRAPS);
        mIntentFriendlyName.put(IntentNames.AMAZON_CANCEL, HelpActionMap.AMAZON_CANCEL);
        mIntentFriendlyName.put(IntentNames.AMAZON_HELP, HelpActionMap.AMAZON_HELP);
        mIntentFriendlyName.put(IntentNames.AMAZON_STOP, HelpActionMap.AMAZON_STOP);
        mIntentFriendlyName.put(IntentNames.ATTACK, HelpActionMap.ATTACK);
        mIntentFriendlyName.put(IntentNames.CAST_SPELL, HelpActionMap.CAST_SPELL);
        mIntentFriendlyName.put(IntentNames.USE_ITEM, HelpActionMap.USE_ITEM);
        mIntentFriendlyName.put(IntentNames.STATUS, HelpActionMap.STATUS);
        mIntentFriendlyName.put(IntentNames.BROWSE_ITEMS, HelpActionMap.BROWSE_ITEMS);
        mIntentFriendlyName.put(IntentNames.BUY_ITEM, HelpActionMap.BUY_ITEM);
        mIntentFriendlyName.put(IntentNames.SELL_ITEM, HelpActionMap.SELL_ITEM);
        mIntentFriendlyName.put(IntentNames.CREATE_HERO, HelpActionMap.CREATE_HERO);
        mIntentFriendlyName.put(IntentNames.ESCAPE, HelpActionMap.ESCAPE);
        mIntentFriendlyName.put(IntentNames.HERO_DESCRIPTION, HelpActionMap.HERO_DESCRIPTION);
        mIntentFriendlyName.put(IntentNames.ITEM_DESCRIPTION, HelpActionMap.ITEM_DESCRIPTION);
        mIntentFriendlyName.put(IntentNames.CHECK_INVENTORY, HelpActionMap.INVENTORY);
        mIntentFriendlyName.put(IntentNames.GOTO_ACADEMY, HelpActionMap.GOTO_ACADEMY);
        mIntentFriendlyName.put(IntentNames.GOTO_DUNGEON, HelpActionMap.GOTO_DUNGEON);
        mIntentFriendlyName.put(IntentNames.GOTO_SHOP, HelpActionMap.GOTO_SHOP);
        mIntentFriendlyName.put(IntentNames.GOTO_TOWN, HelpActionMap.GOTO_TOWN);
    }

    // General
    private static final String LOOK = "Look";
    private static final String GOLD_COUNT = "Check gold";
    private static final String STATUS = "Status";
    private static final String HERO_DESCRIPTION = "Character";
    private static final String ITEM_DESCRIPTION = "Look at Item";
    private static final String INVENTORY = "Equipment";

    // Go-To Area
    private static final String GOTO_SHOP = "Go to Shop";
    private static final String GOTO_DUNGEON = "Start Quest";
    private static final String GOTO_ACADEMY = "Go to Academy";
    private static final String GOTO_TOWN = "Go to Town";
    private static final String GOTO_ROOM = "go ";

    // Create Hero
    private static final String CREATE_HERO = "Create hero";

    // Shop
    private static final String BROWSE_ITEMS = "Browse items";
    private static final String BUY_ITEM = "Buy an item";
    private static final String SELL_ITEM = "Sell an item";

    // Combat
    private static final String ATTACK = "Attack";
    private static final String CAST_SPELL = "Cast a spell";
    private static final String ESCAPE = "Flee";
    private static final String USE_ITEM = "Use an item";

    // Dungeon (Non-Combat)
    private static final String SEARCH_FOR_TRAPS = "Search for traps";
    private static final String SEARCH_FOR_TREASURE = "Search for treasure";
    private static final String DISARM_TRAPS = "Disarm traps";

    // Amazon defined intents
    private static final String AMAZON_HELP = "Ask for help";
    private static final String AMAZON_STOP = "Exit game";
    private static final String AMAZON_CANCEL = "Cancel action";
}
