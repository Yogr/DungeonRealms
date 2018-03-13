package com.dungeonrealms.app;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.GameSession;
import com.dungeonrealms.app.model.GameState;
import com.dungeonrealms.app.model.Hero;
import com.dungeonrealms.app.resolver.DungeonRealmsResolver;
import com.dungeonrealms.app.resolver.GameStateResolver;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.util.SaveLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DungeonRealmsSpeechlet implements SpeechletV2 {
    private static final Logger log = LoggerFactory.getLogger(DungeonRealmsSpeechlet.class);

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        log.info("onSessionStarted requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());
        // any initialization logic goes here
        Session session = requestEnvelope.getSession();
        // Find user
        DungeonUser user = SaveLoad.LoadUser(session.getUser().getUserId());
        // Find hero(es)
        List<Hero> heroes = new ArrayList<>();
        for (String heroId : user.getHeroIds()) {
            Hero hero = SaveLoad.LoadHero(heroId);
            if (hero != null) {
                heroes.add(hero);
            }
        }

        GameSession gameSession;
        if (heroes.size() == 0) {
            gameSession = new GameSession();
            gameSession.setGameState(GameState.CREATE);
        } else if (!StringUtils.isNullOrEmpty(user.getSessionId())){
            gameSession = SaveLoad.LoadGameSession(user.getSessionId());
        } else {
            String newSessionId = UUID.randomUUID().toString();
            gameSession = new GameSession();
            gameSession.setGameState(GameState.TOWN);
            gameSession.setId(newSessionId);
            user.setSessionId(newSessionId);
        }

        session.setAttribute(GameConstants.GAME_SESSION, gameSession);
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
        GameSession gameSession = (GameSession) session.getAttribute(GameConstants.GAME_SESSION);
        GameState state = gameSession.getGameState();

        GameStateResolver resolver = state.getResolver();

        return resolver.resolveIntent(session, request);
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
        log.info("onSessionEnded requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());
        // any cleanup logic goes here
        Session session = requestEnvelope.getSession();
        GameSession gameSession = (GameSession) session.getAttribute(GameConstants.GAME_SESSION);
        GameState state = gameSession.getGameState();

        // Only save game session if in dungeon (or combat)
        if (state == GameState.COMBAT || state == GameState.DUNGEON) {
            SaveLoad.SaveGameSession(gameSession);
        } else {
            // Otherwise clear the old session value if exists.
            session.removeAttribute(GameConstants.GAME_SESSION);
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
