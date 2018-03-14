package com.dungeonrealms.app;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.GameSession;
import com.dungeonrealms.app.model.GameState;
import com.dungeonrealms.app.resolver.DungeonRealmsResolver;
import com.dungeonrealms.app.resolver.GameStateResolver;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.util.SaveLoad;

public class DungeonRealmsSpeechlet implements SpeechletV2 {
    private static void log(String message) {
        System.out.println(message);
    }

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        // any initialization logic goes here
        Session session = requestEnvelope.getSession();
        // Find user

        log("Attempting to LoadUser");
        DungeonUser user = SaveLoad.LoadUser(session.getUser().getUserId());
        log("dhartnett user is null?" + (user==null?"yes":"no"));
        if (user == null) {
            user = new DungeonUser();
        }

        /*
        List<Hero> heroes = user.getHeroes();

        GameSession gameSession = user.getGameSession();
        if (heroes != null && heroes.size() == 0) {
            gameSession = new GameSession();
            gameSession.setGameState(GameState.CREATE);
            user.setGameSession(gameSession);
        } else if (gameSession == null) {
            gameSession = new GameSession();
            gameSession.setGameState(GameState.TOWN);
            user.setGameSession(gameSession);
        }
*/
        //session.setAttribute(GameConstants.USER, user);
        //session.setAttribute(GameConstants.GAME_SESSION, gameSession);
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
        /*GameSession gameSession = (GameSession) session.getAttribute(GameConstants.GAME_SESSION);
        if (gameSession == null) {
            gameSession = new GameSession();
            gameSession.setGameState(GameState.TOWN);
            session.setAttribute(GameConstants.GAME_SESSION, gameSession);
        }*/
        GameState state = GameState.CREATE;//gameSession.getGameState();

        GameStateResolver resolver = state.getResolver();

        return resolver.resolveIntent(session, request);
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
        // any cleanup logic goes here
        Session session = requestEnvelope.getSession();
        GameSession gameSession = (GameSession) session.getAttribute(GameConstants.GAME_SESSION);
        DungeonUser user = (DungeonUser) session.getAttribute(GameConstants.USER);

        if (gameSession != null) {
            GameState state = gameSession.getGameState();

            // Clear game session if not in combat or dungeon
            if (state != GameState.COMBAT && state != GameState.DUNGEON) {
                //user.setGameSession(null);
                // TODO: Set up right now to always save, so clearing session may not make much difference
            }
        }
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
        return new DungeonRealmsResolver().getAskResponse(GameConstants.SKILL_ID, speechText);
    }
}
