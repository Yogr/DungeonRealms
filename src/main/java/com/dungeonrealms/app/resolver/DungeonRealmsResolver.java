package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.speech.Actions;
import com.dungeonrealms.app.speech.CardTitle;
import com.dungeonrealms.app.speech.Responses;

import java.util.HashMap;
import java.util.Map;

public class DungeonRealmsResolver extends GameStateResolver {

    @Override
    public SpeechletResponse resolveIntent(Session session, String intentName, Intent intent) {
        Map<String, ActionHandler> actions = getActions();

        if (actions.containsKey(intentName)) {
            return actions.get(intentName).handleIntent(session, intentName, intent);
        }

        return getInvalidActionResponse();
    }

    protected Map<String, ActionHandler> getActions() {
        Map<String, ActionHandler> actions = new HashMap<>();
        actions.put(Actions.HELP.getIntentName(), mHelpActionHandler);
        actions.put(Actions.CANCEL.getIntentName(), mStopActionHandler);
        actions.put(Actions.QUIT.getIntentName(), mStopActionHandler);
        return actions;
    }

    private ActionHandler mHelpActionHandler = (Session session, String intentName, Intent intent) -> {
        StringBuilder actionsText = new StringBuilder();

        for (String name : getActions().keySet()) {
            Actions action = Actions.valueOf(name);
            actionsText.append(" " + action.getActionName());
        }

        String speechText;
        if (!StringUtils.isNullOrEmpty(actionsText.toString())) {
            speechText = "You may " + actionsText;
        } else {
            speechText = "There are no available actions";
        }

        return getAskResponse(CardTitle.DUNGEON_REALMS, speechText);
    };

    private ActionHandler mStopActionHandler = (Session session, String intentName, Intent intent) -> {
        // TODO: Save game
        PlainTextOutputSpeech speech = getPlainTextOutputSpeech(Responses.GOODBYE);
        return SpeechletResponse.newTellResponse(speech);
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
