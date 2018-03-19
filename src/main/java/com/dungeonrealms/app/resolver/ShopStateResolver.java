package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.game.GameResources;
import com.dungeonrealms.app.game.Navigation;
import com.dungeonrealms.app.game.Shop;
import com.dungeonrealms.app.model.*;
import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.speech.IntentNames;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.speech.SlotNames;

import java.util.Map;

public class ShopStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, ActionHandler> getActions() {
        Map<String, ActionHandler> actions = super.getActions();

        actions.put(IntentNames.GOTO_TOWN, mGotoTownHandler);
        actions.put(IntentNames.BROWSE_ITEMS, mBrowseItemsHandler);
        actions.put(IntentNames.BUY_ITEM, mBuyItemHandler);
        actions.put(IntentNames.SELL_ITEM, mSellItemHandler);
        actions.put(IntentNames.LOOK, mLookHandler);

        return actions;
    }

    private ActionHandler mLookHandler = (Session session, DungeonUser user, Intent intent) ->
            getAskResponse(CardTitle.DUNGEON_REALMS, Responses.SHOP_DESCRIPTION);

    private ActionHandler mGotoTownHandler = (Session session, DungeonUser user, Intent intent) -> {
        StringBuilder response = new StringBuilder();
        Navigation.moveToTown(user.getGameSession());
        response.append(Responses.GOTO_TOWN).append(Responses.TOWN_DESCRIPTION);
        return getAskResponse(CardTitle.DUNGEON_REALMS, response.toString());
    };

    private ActionHandler mBrowseItemsHandler = (Session session, DungeonUser user, Intent intent) -> {
        StringBuilder response = new StringBuilder();
        // TODO: Add additional param to browse by ItemType if provided
        //Slot itemTypeFilterSlot = intent.getSlot(SlotNames.ITEMTYPE);
        //ItemType itemTypeFilter = itemTypeFilterSlot != null ? ItemType.valueOf(itemTypeFilterSlot.getValue()) : null;
        response.append("You can buy ");
        for (Item item : GameResources.getInstance().getItems().values()) {
            //if (itemTypeFilter != null && item.getType() == itemTypeFilter)
            response.append("a ").append(item.getName()).append(" for ").append(item.getCost()).append( " coins; ");
        }
        response.append(" Ask to look at an item for more details");

        return getAskResponse(CardTitle.SHOP, response.toString());
    };

    private ActionHandler mBuyItemHandler = (Session session, DungeonUser user, Intent intent) -> {
        // TODO: Implement multi-hero suppoort for buying and items
        // TODO: Slot heroNameSlot = intent.getSlot(SlotNames.HERO);
        // TODO: String heroName = heroNameSlot != null ? heroNameSlot.getValue() : null;
        // TODO: Hero hero = user.findHeroByName(heroName);
        Hero hero = user.getHeroes().get(0);
        Slot itemNameSlot = intent.getSlot(SlotNames.ITEM);
        String itemName = itemNameSlot != null ? itemNameSlot.getValue() : null;
        if (!StringUtils.isNullOrEmpty(itemName)) {
            Item boughtItem = Shop.buyItem(itemName, user, hero);
            if (boughtItem != null) {
                return getAskResponse(CardTitle.SHOP, String.format(Responses.BOUGHT_ITEM, boughtItem.getName(), boughtItem.getCost()));
            }
        }
        return getAskResponse(CardTitle.SHOP, Responses.CANNOT_BUY);
    };

    private ActionHandler mSellItemHandler = (Session session, DungeonUser user, Intent intent) -> {
        // TODO: Implement multi-hero suppoort for buying and items
        // TODO: Slot heroNameSlot = intent.getSlot(SlotNames.HERO);
        // TODO: String heroName = heroNameSlot != null ? heroNameSlot.getValue() : null;
        // TODO: Hero hero = user.findHeroByName(heroName);
        Hero hero = user.getHeroes().get(0);
        Slot itemNameSlot = intent.getSlot(SlotNames.ITEM);
        String itemName = itemNameSlot != null ? itemNameSlot.getValue() : null;
        if (!StringUtils.isNullOrEmpty(itemName)) {
            Item soldItem = Shop.sellItem(itemName, user, hero);
            if (soldItem != null) {
                return getAskResponse(CardTitle.SHOP, String.format(Responses.SOLD_ITEM, soldItem.getName(), soldItem.getCost()));
            }
        }
        return getAskResponse(CardTitle.SHOP, Responses.CANNOT_SELL);
    };

}
