package com.dungeonrealms.app;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import com.dungeonrealms.app.dummy.GetDummy;
import com.dungeonrealms.app.game.GameSessionManager;
import com.dungeonrealms.app.model.*;
import com.dungeonrealms.app.resolver.DungeonRealmsResolver;
import com.dungeonrealms.app.resolver.GameStateResolver;
import com.dungeonrealms.app.speech.Responses;
import com.dungeonrealms.app.util.DungeonUtils;
import com.dungeonrealms.app.util.SaveLoad;

public class DungeonRealmsSpeechlet implements SpeechletV2 {
    private static void log(String message) {
        System.out.println(message);
    }

    private DungeonUser mDungeonUser;

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        // any initialization logic goes here
        Session session = requestEnvelope.getSession();

        if (session.isNew()) {
            mDungeonUser = GameSessionManager.StartGameSession(session);
        } else {
            mDungeonUser = GameSessionManager.RestoreGameSession(session);
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
        mDungeonUser = GameSessionManager.RestoreGameSession(session);
        GameState state = mDungeonUser.getGameSession().getGameState();

        GameStateResolver resolver = state.getResolver();

        return resolver.resolveIntent(session, mDungeonUser, request);
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
        // any cleanup logic goes here
        log("YOUR SESSION HAS BEEN ENDED USER IS " + mDungeonUser);
        if (mDungeonUser == null) {
            mDungeonUser = GameSessionManager.RestoreGameSession(requestEnvelope.getSession());
        }
        if (mDungeonUser != null) {
            SaveLoad.saveUser(mDungeonUser);
        }
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {
        String speechText = Responses.WELCOME_NEW;
        if (mDungeonUser != null && !mDungeonUser.getHeroes().isEmpty()) {
            Hero hero = mDungeonUser.getHeroes().get(0);
            speechText = String.format(Responses.WELCOME_BACK, hero.getName());

            speechText = DungeonUtils.constructFullRoomMessage(speechText, mDungeonUser.getGameSession());
        }
        return new DungeonRealmsResolver().getAskResponse(GameConstants.SKILL_ID, speechText);
    }

}
