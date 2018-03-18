package com.dungeonrealms.app.game;

import com.amazon.speech.speechlet.Session;
import com.dungeonrealms.app.model.Hero;
import com.dungeonrealms.app.util.SaveLoad;
import com.dungeonrealms.app.GameConstants;
import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.GameSession;
import com.dungeonrealms.app.model.GameState;

import java.util.LinkedHashMap;
import java.util.List;

public class GameSessionManager {

    public static DungeonUser StartGameSession(Session session) {
        DungeonUser user = SaveLoad.loadUser(session.getUser().getUserId());
        if (user == null) {
            user = new DungeonUser();
            user.setId(session.getUser().getUserId());
        }

        // Check for heroes to set Create state
        List<Hero> heroes = user.getHeroes();
        if (heroes == null || heroes.isEmpty()) {
            user.getGameSession().setGameState(GameState.CREATE);
        }

        SaveGameSession(session, user);

        return user;
    }

    @SuppressWarnings("unchecked")
    public static DungeonUser RestoreGameSession(Session session) {
        DungeonUser user = new DungeonUser((LinkedHashMap<String, Object>)session.getAttribute(GameConstants.USER));
        GameSession gameSession = user.getGameSession();
        if (gameSession == null) {
            gameSession = new GameSession();
            gameSession.setGameState(GameState.TOWN);
        }

        if (user.getHeroes() == null || user.getHeroes().isEmpty()) {
            gameSession.setGameState(GameState.CREATE);
        }

        SaveGameSession(session, user);

        return user;
    }

    public static void SaveGameSession(Session session, DungeonUser user) {
        session.removeAttribute(GameConstants.USER);
        session.setAttribute(GameConstants.USER, user);
    }


}
