package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.Hero;
import com.dungeonrealms.app.model.HeroInstance;
import com.dungeonrealms.app.model.Item;
import com.dungeonrealms.app.model.Item.ItemType;
import com.dungeonrealms.app.util.DungeonUtils;

import java.util.Iterator;
import java.util.Map;

public class Inventory {

    public static final int ITEM_NOT_FOUND = -1;

    public static void addItemToBackpack(String itemId, DungeonUser user) {
        int quantity = 1;
        if (user.getBackpack().containsKey(itemId)) {
            quantity += user.getBackpack().get(itemId);
        }
        user.getBackpack().put(itemId, quantity);
    }

    public static boolean removeItemFromBackpack(String itemId, DungeonUser user) {
        return removeItemFromBackpack(itemId, user, 1);
    }

    public static boolean removeItemFromBackpack(String itemId, DungeonUser user, int quantity) {
        if (user.getBackpack().containsKey(itemId)) {
            int newQuantity = user.getBackpack().get(itemId) - quantity;
            if (newQuantity <= 0) {
                user.getBackpack().remove(itemId);
            } else {
                user.getBackpack().put(itemId, newQuantity);
            }
            return true;
        }
        return false;
    }

    public static boolean equipItem(Item item, DungeonUser user, Hero hero, HeroInstance heroInstance) {
        ItemType slot = item.getType();
        if (slot != ItemType.WEAPON &&
                slot != ItemType.ARMOR) {
            return false;
        }

        if (removeItemFromBackpack(item.getId(), user)) {
            unequipByType(slot, user, hero, heroInstance);
            hero.getEquipment().add(item.getId());
            heroInstance.calculateValuesFromHeroItems(hero);
            return true;
        }
        return false;
    }

    public static boolean unequipByType(ItemType type, DungeonUser user, Hero hero, HeroInstance heroInstance) {
        Iterator<String> itemIds = hero.getEquipment().iterator();
        while (itemIds.hasNext()) {
            Item curItem = GameResources.getInstance().getItems().get(itemIds.next());
            if (type == curItem.getType()) {
                itemIds.remove();
                addItemToBackpack(curItem.getId(), user);
                heroInstance.calculateValuesFromHeroItems(hero);
                return true;
            }
        }
        return false;
    }

    public static boolean unequipByName(String itemName, DungeonUser user, Hero hero, HeroInstance heroInstance) {
        Iterator<String> itemIds = hero.getEquipment().iterator();
        while (itemIds.hasNext()) {
            Item curItem = GameResources.getInstance().getItems().get(itemIds.next());
            if (curItem.getName().equals(itemName)) {
                itemIds.remove();
                addItemToBackpack(curItem.getId(), user);
                heroInstance.calculateValuesFromHeroItems(hero);
                return true;
            }
        }
        return false;
    }

    public static int getItemQuantity(String itemId, DungeonUser user) {
        if (user.getBackpack().containsKey(itemId)) {
            return user.getBackpack().get(itemId);
        }
        return ITEM_NOT_FOUND;
    }

    public static int useItemByName(String itemName, DungeonUser user, Hero hero, HeroInstance heroInstance) {
        Item itemFound = Inventory.getItemFromBackpackByName(itemName, user);
        if (itemFound != null) {
            if (Inventory.removeItemFromBackpack(itemFound.getId(), user)) {
                // TODO: Implement item effect IDs to map to spells
                int newHp = Math.min(hero.getHitpointBase(), heroInstance.getCurrentHP() + itemFound.getSpellpower());
                heroInstance.setCurrentHP(newHp);
                return getItemQuantity(itemFound.getId(), user);
            }
        }
        return ITEM_NOT_FOUND;
    }

    public static Item getItemFromBackpackByName(String itemName, DungeonUser user) {
        Item foundItem = null;
        for(String bagItemId : user.getBackpack().keySet()) {
            Item bagItem = GameResources.getInstance().getItems().get(bagItemId);
            if (bagItem.getName().equals(itemName)) {
                foundItem = bagItem;
                break;
            }
        }

        if (foundItem == null) {
            for(String bagItemId : user.getBackpack().keySet()) {
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
            String article = DungeonUtils.startsWithVowel(item.getName()) ? "an " : "a ";
            sb.append(article).append(item.getName()).append("; ");
        }
    }

    public static void buildBackpackString(StringBuilder sb, DungeonUser user) {
        boolean isEmpty = true;
        for (Map.Entry<String, Integer> itemEntry : user.getBackpack().entrySet()) {
            isEmpty = false;
            Item item = GameResources.getInstance().getItems().get(itemEntry.getKey());
            if (itemEntry.getValue() == 1) {
                String article = DungeonUtils.startsWithVowel(item.getName()) ? "an " : "a ";
                sb.append(article);
            } else {
                sb.append(String.format("%1$s ", itemEntry.getValue()));
            }
            sb.append(item.getName()).append("; ");
        }
        if (isEmpty) {
            sb.append("nothing. ");
        }
    }

}
