package com.dungeonrealms.app.dummy;

import com.dungeonrealms.app.model.*;

import java.util.HashMap;
import java.util.Map;

public class GetDummy {

    public static Dungeon dummyDungeon() {
        Map<Integer, Room> rooms = new HashMap<>();
        rooms.put(dummyRoom1().getId(), dummyRoom1());
        rooms.put(dummyRoom2().getId(), dummyRoom2());
        rooms.put(dummyRoom3().getId(), dummyRoom3());

        return Dungeon.builder()
                .id(1)
                .name("Goblin Dungeon")
                .rooms(rooms)
                .build();
    }

    public static Room dummyRoom1() {
        Map<String, Integer> exits = new HashMap<>();
        exits.put("corridor", 2);

        return Room.builder()
                .id(1)
                .title("Goblin Dungeon")
                .description("You wake up in a dark corner of a dungeon. You can't remember how you got here. The" +
                        " only way forward is a dimly lit corridor.")
                .exits(exits)
                .build();
    }

    public static Room dummyRoom2() {
        Map<String, Integer> exits = new HashMap<>();
        exits.put("door", 3);
        exits.put("stairs", -1);
        return Room.builder()
                .id(2)
                .title("Goblin Dungeon")
                .description("You move through the dimly lit corridor and see several prison cells on your left and your right." +
                        " One of these must have been for you. A wooden door lies ahead, with a set of stairs leading up next to it.")
                .exits(exits)
                .build();
    }

    public static Room dummyRoom3() {
        Map<String, Integer> exits = new HashMap<>();
        exits.put("stairs", -1);
        return Room.builder()
                .id(3)
                .title("Goblin King Room")
                .description("The room you are in appears to be decorated with the finest goblin jewels and tapestries." +
                        " Directly in the back of the room there is a goblin-sized throne with skulls on it. " +
                        " This must be the Goblin King's Throne Room.")
                .exits(exits)
                .build();
    }

    public static Monster dummyGoblin() {
        return Monster.builder()
                .id(1)
                .name("goblin")
                .attack(3)
                .defense(1)
                .hitPoints(3)
                .build();
    }

    public static Item dummySword() {
        return Item.builder()
                .id(1)
                .name("short sword")
                .type(ItemType.WEAPON)
                .build();
    }

    public static Item dummyArmor() {
        return Item.builder()
                .id(2)
                .name("leather tunic")
                .type(ItemType.ARMOR)
                .build();
    }

    public static Item dummyGold(int amount) {
        return Item.builder()
                .id(3)
                .name("gold coins")
                .type(ItemType.GOLD)
                .build();
    }

    public static Item dummyHealPotion() {
        return Item.builder()
                .id(4)
                .name("healing potion")
                .type(ItemType.CONSUMABLE)
                .build();
    }
}
