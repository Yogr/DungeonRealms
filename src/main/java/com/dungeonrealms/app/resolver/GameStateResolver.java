package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Directive;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.dialog.directives.DelegateDirective;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.dialog.directives.DialogIntent;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.dungeonrealms.app.model.DungeonUser;

import java.util.ArrayList;
import java.util.List;

public abstract class GameStateResolver {

    public SpeechletResponse resolveIntent(Session session, DungeonUser user, IntentRequest request) {

        // Delegate all requests back to Alexa until they are completed
        if (request.getDialogState() != IntentRequest.DialogState.COMPLETED) {
            DialogIntent dialogIntent = new DialogIntent(request.getIntent());

            DelegateDirective directive = new DelegateDirective();
            directive.setUpdatedIntent(dialogIntent);

            List<Directive> directiveList = new ArrayList<>();
            directiveList.add(directive);
            SpeechletResponse response = new SpeechletResponse();
            response.setDirectives(directiveList);
            response.setNullableShouldEndSession(false);
            return response;
        }

        return resolveIntent(session, user, request.getIntent());
    }

    public abstract SpeechletResponse resolveIntent(Session session, DungeonUser user, Intent intent);

    /**
     * Helper method for retrieving an Ask response with a simple card and reprompt included.
     * @param cardTitle Title of the card that you want displayed.
     * @param speechText speech text that will be spoken to the user.
     * @return the resulting card and speech text.
     */
    public SpeechletResponse getAskResponse(String cardTitle, String speechText) {
        SimpleCard card = getSimpleCard(cardTitle, speechText);
        PlainTextOutputSpeech speech = getPlainTextOutputSpeech(speechText);
        Reprompt reprompt = getReprompt(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    /**
     * Helper method that creates a card object.
     * @param title title of the card
     * @param content body of the card
     * @return SimpleCard the display card to be sent along with the voice response.
     */
    SimpleCard getSimpleCard(String title, String content) {
        SimpleCard card = new SimpleCard();
        card.setTitle(title);
        card.setContent(content);
        return card;
    }

    /**
     * Helper method for retrieving an OutputSpeech object when given a string of TTS.
     * @param speechText the text that should be spoken out to the user.
     * @return an instance of SpeechOutput.
     */
    PlainTextOutputSpeech getPlainTextOutputSpeech(String speechText) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        return speech;
    }

    /**
     * Helper method that returns a reprompt object. This is used in Ask responses where you want
     * the user to be able to respond to your speech.
     * @param outputSpeech The OutputSpeech object that will be said once and repeated if necessary.
     * @return Reprompt instance.
     */
    private Reprompt getReprompt(OutputSpeech outputSpeech) {
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(outputSpeech);
        return reprompt;
    }
}
