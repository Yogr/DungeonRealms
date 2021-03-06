package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.dialog.directives.DialogIntent;
import com.amazon.speech.speechlet.dialog.directives.DialogSlot;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.game.GameResources;
import com.dungeonrealms.app.game.Shop;
import com.dungeonrealms.app.model.*;
import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.speech.IntentNames;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.speech.SlotNames;
import com.dungeonrealms.app.util.DungeonUtils;
import com.dungeonrealms.app.util.ItemUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShopStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, DungeonAction> getActions() {
        Map<String, DungeonAction> actions = new LinkedHashMap<>();

        actions.put(IntentNames.BROWSE_ITEMS, new DungeonAction("browse items", mBrowseItemsHandler));
        actions.put(IntentNames.BUY_ITEM, new DungeonAction("buy item", mBuyItemHandler));
        actions.put(IntentNames.SELL_ITEM, new DungeonAction("sell item", mSellItemHandler));
        actions.put(IntentNames.MOVE_ROOM, new DungeonAction("go", mMoveRoomHandler));
        actions.put(IntentNames.LOOK, new DungeonAction("look", mLookHandler));
        actions.put(IntentNames.GOLD_COUNT, new DungeonAction("wealth", mGoldCountHandler));
        actions.put(IntentNames.HERO_DESCRIPTION, new DungeonAction("character", mDescribeHeroHandler));
        actions.put(IntentNames.ITEM_DESCRIPTION, new DungeonAction("look at item", mDescribeItemHandler));
        actions.put(IntentNames.CHECK_INVENTORY, new DungeonAction("inventory", mBackpackHandler));

        return actions;
    }

    @Override
    public SpeechletResponse resolveIncompleteIntent(Session session, DungeonUser user, Intent intent) {
        Slot itemNameSlot = intent.getSlot(SlotNames.ITEM);
        String itemName = itemNameSlot != null ? itemNameSlot.getValue() : null;
        if (!StringUtils.isNullOrEmpty(itemName)) {
            Item item = ItemUtils.getItemByName(itemName);
            if (item != null) {
                DialogSlot priceSlot = new DialogSlot();
                priceSlot.setName(SlotNames.PRICE);
                int itemPrice = item.getCost();
                if (IntentNames.SELL_ITEM.equals(intent.getName())) {
                    itemPrice = itemPrice / 2;
                }
                priceSlot.setValue(String.valueOf(itemPrice));

                DialogIntent dialogIntent = new DialogIntent(intent);
                dialogIntent.getSlots().put(SlotNames.PRICE, priceSlot);
                return getDelegateResponseWithDialogIntent(dialogIntent);
            }
        }
        return super.resolveIncompleteIntent(session, user, intent);
    }

    private ActionHandler mBrowseItemsHandler = (Session session, DungeonUser user, Intent intent) -> {
        StringBuilder response = new StringBuilder();
        // TODO: Add additional param to browse by ItemType if provided
        //Slot itemTypeFilterSlot = intent.getSlot(SlotNames.ITEMTYPE);
        //ItemType itemTypeFilter = itemTypeFilterSlot != null ? ItemType.valueOf(itemTypeFilterSlot.getValue()) : null;
        response.append("You can buy ");
        for (Item item : GameResources.getInstance().getItems().values()) {
            //if (itemTypeFilter != null && item.getType() == itemTypeFilter)
            String article = DungeonUtils.startsWithVowel(item.getName()) ? "an " : "a ";
            response.append(article).append(item.getName()).append(" for ").append(item.getCost()).append( " coins; ");
        }
        response.append(" Ask to look at an item for more details");

        return getAskResponse(CardTitle.SHOP, response.toString());
    };

    private ActionHandler mBuyItemHandler = (Session session, DungeonUser user, Intent intent) -> {
        Slot itemNameSlot = intent.getSlot(SlotNames.ITEM);
        String itemName = itemNameSlot != null ? itemNameSlot.getValue() : null;
        if (!StringUtils.isNullOrEmpty(itemName)) {
            Item boughtItem = Shop.buyItem(itemName, user);
            if (boughtItem != null) {
                return getAskResponse(CardTitle.SHOP, String.format(Responses.BOUGHT_ITEM, boughtItem.getName(), boughtItem.getCost()));
            }
        }
        return getAskResponse(CardTitle.SHOP, Responses.CANNOT_BUY);
    };

    private ActionHandler mSellItemHandler = (Session session, DungeonUser user, Intent intent) -> {
        Slot itemNameSlot = intent.getSlot(SlotNames.ITEM);
        String itemName = itemNameSlot != null ? itemNameSlot.getValue() : null;
        if (!StringUtils.isNullOrEmpty(itemName)) {
            Item soldItem = Shop.sellItem(itemName, user);
            if (soldItem != null) {
                return getAskResponse(CardTitle.SHOP, String.format(Responses.SOLD_ITEM, soldItem.getName(), soldItem.getCost()));
            }
        }
        return getAskResponse(CardTitle.SHOP, Responses.CANNOT_SELL);
    };

}
