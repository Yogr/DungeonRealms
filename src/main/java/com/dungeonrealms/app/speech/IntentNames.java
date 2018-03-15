package com.dungeonrealms.app.speech;

public class IntentNames {

    // General
    public static final String LOOK = "LookIntent";
    public static final String GOLD_COUNT = "GoldCountIntent";
    public static final String STATUS = "StatusIntent";
    public static final String HERO_DESCRIPTION = "HeroDescriptionIntent";
    public static final String ITEM_DESCRIPTION = "ItemDescriptionIntent";
    public static final String EXIT_AREA = "ExitAreaIntent";

    // Go-To Area
    public static final String GOTO_SHOP = "ShopIntent";
    public static final String GOTO_DUNGEON = "DungeonIntent";
    public static final String GOTO_ABILITIES = "AbilitiesIntent";
    public static final String GOTO_INVENTORY = "InventoryIntent";

    // Create Hero
    public static final String CREATE_HERO = "CreateHeroIntent";

    // Shop
    public static final String BUY_ITEM = "BuyItemIntent";
    public static final String SELL_ITEM = "SellItemIntent";

    // Combat
    public static final String ATTACK = "AttackIntent";
    public static final String CAST_SPELL = "CastSpellIntent";
    public static final String ESCAPE = "EscapeIntent";
    public static final String USE_ITEM = "UseItemIntent";

    // Dungeon (Non-Combat)
    public static final String MOVE_ROOM = "MoveRoomIntent";
    public static final String SEARCH_FOR_TRAPS = "SearchTrapsIntent";
    public static final String SEARCH_FOR_TREASURE = "SearchTreasureIntent";
    public static final String DISARM_TRAPS = "DisarmTrapsIntent";

    // Amazon defined intents
    public static final String AMAZON_HELP = "AMAZON.HelpIntent";
    public static final String AMAZON_STOP = "AMAZON.StopIntent";
    public static final String AMAZON_CANCEL = "AMAZON.CancelIntent";
}
