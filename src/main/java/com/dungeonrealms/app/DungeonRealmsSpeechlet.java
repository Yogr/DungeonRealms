package com.dungeonrealms.app;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import com.dungeonrealms.app.model.GameState;
import com.dungeonrealms.app.resolver.DungeonRealmsResolver;
import com.dungeonrealms.app.resolver.GameStateResolver;
import com.dungeonrealms.app.speech.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DungeonRealmsSpeechlet implements SpeechletV2 {
    private static final Logger log = LoggerFactory.getLogger(DungeonRealmsSpeechlet.class);

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        log.info("onSessionStarted requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());
        // any initialization logic goes here
    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        log.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        IntentRequest request = requestEnvelope.getRequest();
        Session session = requestEnvelope.getSession();
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());

        // Restore game state
        GameState state = (GameState) session.getAttribute(GameConstants.GAME_STATE);
        if (state == null) {
            // New game, check for existing characters against user ID
            // TODO: Do some dynamoDB magic to pull user data and throw that in the session info
            session.getUser().getUserId();

            //else
            state = GameState.CREATE;
            session.setAttribute(GameConstants.GAME_STATE, state);
        }

        GameStateResolver resolver = state.getResolver();

        return resolver.resolveIntent(session, request);
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
        log.info("onSessionEnded requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());
        // any cleanup logic goes here
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {
        String speechText = Responses.WELCOME_NEW;
        return new DungeonRealmsResolver().getAskResponse(GameConstants.SKILL_ID, speechText);
    }
}
