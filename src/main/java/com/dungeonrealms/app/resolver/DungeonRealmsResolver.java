package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.speech.HelpActionMap;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.util.SaveLoad;

import com.dungeonrealms.app.model.Dungeon;
import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.Room;
import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.speech.IntentNames;
import com.dungeonrealms.app.game.Navigation;
import com.dungeonrealms.app.util.DungeonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DungeonRealmsResolver extends GameStateResolver {

    @Override
    public SpeechletResponse resolveIntent(Session session, DungeonUser user, Intent intent) {
        String intentName = (intent != null) ? intent.getName() : null;

        Map<String, ActionHandler> actions = getActions();
        if (actions.containsKey(intentName)) {
            return actions.get(intentName).handleIntent(session, user, intent);
        }

        return getInvalidActionResponse();
    }

    protected Map<String, ActionHandler> getActions() {
        Map<String, ActionHandler> actions = new HashMap<>();
        actions.put(IntentNames.LOOK, mLookHandler);
        actions.put(IntentNames.GOLD_COUNT, mGoldCountHandler);
        actions.put(IntentNames.AMAZON_HELP, mHelpActionHandler);
        actions.put(IntentNames.AMAZON_CANCEL, mStopActionHandler);
        actions.put(IntentNames.AMAZON_STOP, mStopActionHandler);
        return actions;
    }

    private ActionHandler mHelpActionHandler = (Session session, DungeonUser user, Intent intent) -> {
        StringBuilder actionsText = new StringBuilder();

        for (String name : getActions().keySet()) {
            if (IntentNames.AMAZON_CANCEL.equals(name) ||
                IntentNames.AMAZON_HELP.equals(name) ||
                IntentNames.AMAZON_STOP.equals(name)) {
                continue;
            }
            if (IntentNames.MOVE_ROOM.equals(name)) {
                Set<String> roomExits = Navigation.getRoomExits(user);
                if (roomExits != null) {
                    for (String exit : roomExits) {
                        actionsText.append(", go " + exit);
                    }
                }
            } else {
                actionsText.append(", " + HelpActionMap.getIntentFriendlyName().get(name));
            }
        }

        String speechText;
        if (!StringUtils.isNullOrEmpty(actionsText.toString())) {
            speechText = "You may " + actionsText;
        } else {
            speechText = "There are no available actions";
        }

        return getAskResponse(CardTitle.DUNGEON_REALMS, speechText);
    };

    private ActionHandler mStopActionHandler = (Session session, DungeonUser user, Intent intent) -> {
        SaveLoad.saveUser(user);
        PlainTextOutputSpeech speech = getPlainTextOutputSpeech(Responses.GOODBYE);
        SpeechletResponse response = SpeechletResponse.newTellResponse(speech);
        response.setNullableShouldEndSession(true);
        return response;
    };

    private ActionHandler mLookHandler = (Session session, DungeonUser user, Intent intent) -> {
        String speechText = DungeonUtils.constructFullRoomMessage(user.getGameSession());
        Dungeon dungeon = Navigation.getDungeon(user.getGameSession().getDungeonId());
        Room room = null;
        if (dungeon != null) {
            room = Navigation.getDungeonRoom(dungeon, user.getGameSession().getRoomId());
        }
        return getAskResponse(room != null ? room.getTitle() : CardTitle.DUNGEON_REALMS, speechText);
    };

    private ActionHandler mGoldCountHandler = (Session session, DungeonUser user, Intent intent) -> {
        int gold = user.getGold();
        String speechText = String.format(Responses.GOLD_COUNT, gold);
        return getAskResponse(CardTitle.DUNGEON_REALMS, speechText);
    };

    protected SpeechletResponse getInvalidActionResponse() {
        return getAskResponse(CardTitle.DUNGEON_REALMS, Responses.INVALID_ACTION_RESPONSE);
    }

    /**
     * Basic return speech with Dungeon Realms card
     * @param speechText Your message to user
     * @return Speechlet with Dungeon Realms card and custom text
     */
    protected SpeechletResponse getPromptedAskResponse(String cardTitle, String speechText) {
        String doNextAppendedString = speechText + Responses.PROMPT_ACTION;
        return getAskResponse(cardTitle, doNextAppendedString);
    }

}
