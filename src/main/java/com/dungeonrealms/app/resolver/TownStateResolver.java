package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.dungeonrealms.app.game.Navigation;
import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.speech.IntentNames;
import com.dungeonrealms.app.speech.Responses;
import java.util.Map;

public class TownStateResolver extends DungeonRealmsResolver {

    @Override
    protected Map<String, ActionHandler> getActions() {
        Map<String, ActionHandler> actions = super.getActions();

        actions.put(IntentNames.GOTO_SHOP, mGotoShopHandler);
        actions.put(IntentNames.LOOK, mLookHandler);

        return actions;
    }

    private ActionHandler mGotoShopHandler = (Session session, DungeonUser user, Intent intent) -> {
        StringBuilder response = new StringBuilder();
        Navigation.moveToShop(user.getGameSession());
        response.append(Responses.GOTO_SHOP).append(Responses.SHOP_DESCRIPTION);
        return getAskResponse(CardTitle.SHOP, response.toString());
    };

    private ActionHandler mLookHandler = (Session session, DungeonUser user, Intent intent) ->
            getAskResponse(CardTitle.DUNGEON_REALMS, Responses.TOWN_DESCRIPTION);

}
