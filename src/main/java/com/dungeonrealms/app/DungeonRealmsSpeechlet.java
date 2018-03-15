package com.dungeonrealms.app;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import com.dungeonrealms.app.dummy.GetDummy;
import com.dungeonrealms.app.game.GameSessionManager;
import com.dungeonrealms.app.model.*;
import com.dungeonrealms.app.resolver.DungeonRealmsResolver;
import com.dungeonrealms.app.resolver.GameStateResolver;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.util.SaveLoad;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class DungeonRealmsSpeechlet implements SpeechletV2 {
    private static void log(String message) {
        System.out.println(message);
    }

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        // any initialization logic goes here
        Session session = requestEnvelope.getSession();

        GameSessionManager gameSessionManager = GameSessionManager.getInstance();
        if (session.isNew()) {
            gameSessionManager.StartGameSession(session);
        } else {
            gameSessionManager.RestoreGameSession(session);
        }
    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {

        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        IntentRequest request = requestEnvelope.getRequest();
        Session session = requestEnvelope.getSession();

        // Restore game state
        GameSessionManager.getInstance().RestoreGameSession(session);
        GameState state = GameSessionManager.getInstance().getGameSession().getGameState();

        GameStateResolver resolver = state.getResolver();

        return resolver.resolveIntent(session, request);
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
        // any cleanup logic goes here
        DungeonUser user = GameSessionManager.getInstance().getUser();
        if (user != null) {
            SaveLoad.SaveUser(user);
        }
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {
        String speechText = Responses.WELCOME_NEW;
        if (!GameSessionManager.getInstance().getHeroes().isEmpty()) {
            Hero hero = GameSessionManager.getInstance().getHeroes().get(0);
            speechText = String.format(Responses.WELCOME_BACK, hero.getName());
        }
        return new DungeonRealmsResolver().getAskResponse(GameConstants.SKILL_ID, speechText);
    }

}
