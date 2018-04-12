package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.Hero;
import com.dungeonrealms.app.model.HeroInstance;
import com.dungeonrealms.app.model.Item;
import com.dungeonrealms.app.model.Item.ItemType;

import java.util.Iterator;
import java.util.Map;

public class Inventory {

    public static final int ITEM_NOT_FOUND = -1;

    public static void addItemToBackpack(String itemId, Hero hero) {
        int quantity = 1;
        if (hero.getBackpack().containsKey(itemId)) {
            quantity += hero.getBackpack().get(itemId);
        }
        hero.getBackpack().put(itemId, quantity);
    }

    public static boolean removeItemFromBackpack(String itemId, Hero hero) {
        return removeItemFromBackpack(itemId, hero, 1);
    }

    public static boolean removeItemFromBackpack(String itemId, Hero hero, int quantity) {
        if (hero.getBackpack().containsKey(itemId)) {
            int newQuantity = hero.getBackpack().get(itemId) - quantity;
            if (newQuantity <= 0) {
                hero.getBackpack().remove(itemId);
            } else {
                hero.getBackpack().put(itemId, newQuantity);
            }
            return true;
        }
        return false;
    }

    public static boolean equipItem(Item item, Hero hero, HeroInstance heroInstance) {
        ItemType slot = item.getType();
        if (slot != ItemType.WEAPON &&
                slot != ItemType.ARMOR) {
            return false;
        }

        if (removeItemFromBackpack(item.getId(), hero)) {
            unequipByType(slot, hero, heroInstance);
            hero.getEquipment().add(item.getId());
            heroInstance.calculateValuesFromHeroItems(hero);
            return true;
        }
        return false;
    }

    public static boolean unequipByType(ItemType type, Hero hero, HeroInstance heroInstance) {
        Iterator<String> itemIds = hero.getEquipment().iterator();
        while (itemIds.hasNext()) {
            Item curItem = GameResources.getInstance().getItems().get(itemIds.next());
            if (type == curItem.getType()) {
                itemIds.remove();
                addItemToBackpack(curItem.getId(), hero);
                heroInstance.calculateValuesFromHeroItems(hero);
                return true;
            }
        }
        return false;
    }

    public static boolean unequipByName(String itemName, Hero hero, HeroInstance heroInstance) {
        Iterator<String> itemIds = hero.getEquipment().iterator();
        while (itemIds.hasNext()) {
            Item curItem = GameResources.getInstance().getItems().get(itemIds.next());
            if (curItem.getName().equals(itemName)) {
                itemIds.remove();
                addItemToBackpack(curItem.getId(), hero);
                heroInstance.calculateValuesFromHeroItems(hero);
                return true;
            }
        }
        return false;
    }

    public static int getItemQuantity(String itemId, Hero hero) {
        if (hero.getBackpack().containsKey(itemId)) {
            return hero.getBackpack().get(itemId);
        }
        return ITEM_NOT_FOUND;
    }

    public static int useItemByName(String itemName, Hero hero, HeroInstance heroInstance) {
        Item itemFound = Inventory.getItemFromBackpackByName(itemName, hero);
        if (itemFound != null) {
            if (Inventory.removeItemFromBackpack(itemFound.getId(), hero)) {
                // TODO: Implement item effect IDs to map to spells
                int newHp = Math.min(hero.getHitpointBase(), heroInstance.getCurrentHP() + itemFound.getSpellpower());
                heroInstance.setCurrentHP(newHp);
                return getItemQuantity(itemFound.getId(), hero);
            }
        }
        return ITEM_NOT_FOUND;
    }

    public static Item getItemFromBackpackByName(String itemName, Hero hero) {
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
        return foundItem;
    }

    public static void buildEquipmentString(StringBuilder sb, Hero hero) {
        for (String itemId : hero.getEquipment()) {
            Item item = GameResources.getInstance().getItems().get(itemId);
            sb.append("a ").append(item.getName()).append("; ");
        }
    }

    public static void buildBackpackString(StringBuilder sb, Hero hero) {
        for (Map.Entry<String, Integer> itemEntry : hero.getBackpack().entrySet()) {
            Item item = GameResources.getInstance().getItems().get(itemEntry.getKey());
            if (itemEntry.getValue() == 1) {
                sb.append("a ");
            } else {
                sb.append(String.format("%1$s ", itemEntry.getValue()));
            }
            sb.append(item.getName()).append("; ");
        }
    }

}
