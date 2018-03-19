package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.Hero;
import com.dungeonrealms.app.model.Item;

public class Shop {

    public static Item buyItem(String itemName, DungeonUser user, Hero hero) {
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

        if (foundItem != null) {
            return buyItem(foundItem, user, hero);
        }
        return null;
    }

    public static Item buyItem(Item item, DungeonUser user, Hero hero) {
        if (user.getGold() >= item.getCost()) {
            user.setGold(user.getGold() - item.getCost());
            Inventory.addItemToBackpack(item.getId(), hero);
            return item;
        } else {
            return null;
        }
    }

    public static Item sellItem(String itemName, DungeonUser user, Hero hero) {
        Item foundItem = null;
        for(String bagItemId : hero.getBackpack().keySet()) {
            Item bagItem = GameResources.getInstance().getItems().get(bagItemId);
            if (bagItem.getName().equals(itemName)) {
                foundItem = bagItem;
                break;
            }
        }

        if (foundItem == null) {
            for(String bagItemId : hero.getBackpack().keySet()) {
                Item bagItem = GameResources.getInstance().getItems().get(bagItemId);
                if (bagItem.getAlias().equals(itemName)) {
                    foundItem = bagItem;
                    break;
                }
            }
        }

        if (foundItem != null) {
            return sellItem(foundItem, user, hero);
        }
        return null;
    }

    public static Item sellItem(Item item, DungeonUser user, Hero hero) {
        if (Inventory.removeItemFromBackpack(item.getId(), hero)) {
            user.setGold(user.getGold() + item.getCost());
            return item;
        }
        return null;
    }
}
