package com.dungeonrealms.app.util;

import com.dungeonrealms.app.game.GameResources;
import com.dungeonrealms.app.model.Item;

public class ItemUtils {

    /**
     * Looks through full list of items by exact name lookup first
     * Then looks through list of items by alias
     * @param itemName
     * @return
     */
    public static Item getItemByName(String itemName) {
        Item foundItem = null;
        for(Item item : GameResources.getInstance().getItems().values()) {
            if (item.getName().equals(itemName)) {
                foundItem = item;
                break;
            }
        }

        if (foundItem == null) {
            for(Item item : GameResources.getInstance().getItems().values()) {
                if (item.getAlias().equals(itemName)) {
                    foundItem = item;
                    break;
                }
            }
        }
        return foundItem;
    }

    public static Item getItemById(String itemId) {
        return GameResources.getInstance().getItems().get(itemId);
    }
}
