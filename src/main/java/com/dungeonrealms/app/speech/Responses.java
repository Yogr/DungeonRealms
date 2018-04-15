package com.dungeonrealms.app.speech;

public class Responses {
    public static final String INVALID_ACTION_RESPONSE = "You cannot do that right now. Say 'Actions' for a list of available commands. ";

    // Greetings
    public static final String WELCOME_NEW = "Welcome to Dungeon Realms. I see you are new here. If you'd like to create a hero, say, Create Hero. ";
    public static final String WELCOME_BACK = "Welcome back, %1$s. ";
    public static final String GOODBYE = "Ok. Your game has been saved, see you next time. ";
    public static final String PROMPT_ACTION = " What would you like to do? ";
    public static final String HELLO_NEW_HERO = "Hello %1$s. ";

    // General responses
    public static final String GOLD_COUNT = "You have %1$s gold coins. ";
    public static final String HERO_STATUS = "%1$s has %2$s health and %3$s mana. ";
    public static final String THING_NOT_FOUND = "I could not find %1$s. ";
    public static final String NOT_FOUND = "I could not find what you were looking for. ";
    public static final String DESCRIBE_HERO = "You see %1$s, a level %2$s adventurer. ";
    public static final String DESCRIBE_HERO_EQUIP = "%1$s is wearing %2$s";
    public static final String DESCRIBE_ITEM = "You see %1$s. ";
    public static final String GAIN_EXPERIENCE = "%1$s gains %2$s experience. ";
    public static final String LEVEL_UP = "%1$s has leveled up! %1$s is now level %2$s";

    // Combat Responses
    public static final String DEALT_DAMAGE = "You strike a %1$s for %2$s damage. ";
    public static final String ATTACK_MISS = "You swing at a %1$s, but miss! ";
    public static final String ENEMY_DEFEATED = "The %1$s has been defeated! ";
    public static final String ENEMY_HIT_HERO = "a %1$s hits %2$s for %3$s damage. ";
    public static final String ENEMY_MISS_HERO = "a %1$s attacks %2$s, but misses! ";
    public static final String NEXT_HERO_UP = "%1$s, what would you like to do? ";

    // Dungeon
    public static final String LEAVE_DUNGEON = "You find the exit of the dungeon, and head back to town. ";
    public static final String ITEM_USED_REMAINING = "%1$s uses a %2$s, and has %3$s remaining. ";

    // Town
    public static final String TOWN_DESCRIPTION = "The Town's streets are full of citizens, adventurers, and wanderers " +
            "much like yourself. You see a blacksmith shop and the Wizard's Academy. ";
    public static final String GOTO_SHOP = "You travel to Griznak's Goods and Smithery. ";

    // Shop
    public static final String SHOP_DESCRIPTION = "Inside there are many " +
            "hanging weapons, armor, and other goods. A large, ogre-like shopkeeper greets you. You may ask him what is for sale, " +
            "or offer to buy or sell any items. ";
    public static final String GOTO_TOWN = "You leave Griznak's Goods and Smithery and head back to town. ";
    public static final String BOUGHT_ITEM = "You purchase a %1$s for %2$s gold coins. ";
    public static final String SOLD_ITEM = "You sell a %1$s for %2$s gold coins. ";
    public static final String CANNOT_BUY = "Sorry, you can't purchase that. ";
    public static final String CANNOT_SELL = "Sorry, you can't sell that. ";
}
