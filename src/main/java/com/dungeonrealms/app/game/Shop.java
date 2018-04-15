package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.Hero;
import com.dungeonrealms.app.model.Item;
import com.dungeonrealms.app.util.ItemUtils;

public class Shop {

    public static Item buyItem(String itemName, DungeonUser user) {
        Item foundItem = ItemUtils.getItemByName(itemName);
        if (foundItem != null) {
            return buyItem(foundItem, user);
        }
        return null;
    }

    public static Item buyItem(Item item, DungeonUser user) {
        if (user.getGold() >= item.getCost()) {
            user.setGold(user.getGold() - item.getCost());
            Inventory.addItemToBackpack(item.getId(), user);
            return item;
        } else {
            return null;
        }
    }

    public static Item sellItem(String itemName, DungeonUser user) {
        Item foundItem = Inventory.getItemFromBackpackByName(itemName, user);
        if (foundItem != null) {
            return sellItem(foundItem, user);
        }
        return null;
    }

    public static Item sellItem(Item item, DungeonUser user) {
        if (Inventory.removeItemFromBackpack(item.getId(), user)) {
            user.setGold(user.getGold() + (item.getCost()/2));
            return item;
        }
        return null;
    }
}
