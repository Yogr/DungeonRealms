package com.dungeonrealms.app.game;

import com.amazon.speech.speechlet.Session;
import com.dungeonrealms.app.GameConstants;
import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.GameSession;
import com.dungeonrealms.app.model.GameState;
import com.dungeonrealms.app.model.Hero;
import com.dungeonrealms.app.util.SaveLoad;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;

public class GameSessionManager {

    @Getter
    private DungeonUser mUser;

    public GameSession getGameSession() { return mUser.getGameSession(); }

    public List<Hero> getHeroes() { return mUser.getHeroes(); }

    @Getter(lazy = true)
    private final static GameSessionManager sInstance = new GameSessionManager();

    public void StartGameSession(Session session) {
        DungeonUser user = SaveLoad.LoadUser(session.getUser().getUserId());
        if (user == null) {
            user = new DungeonUser();
            user.setId(session.getUser().getUserId());
        }
        mUser = user;

        // Check for heroes to set Create state
        List<Hero> heroes = user.getHeroes();
        if (heroes == null || heroes.isEmpty()) {
            user.getGameSession().setGameState(GameState.CREATE);
        }

        SaveGameSession(session);
    }

    @SuppressWarnings("unchecked")
    public void RestoreGameSession(Session session) {
        mUser = new DungeonUser((LinkedHashMap<String, Object>)session.getAttribute(GameConstants.USER));
        GameSession gameSession = mUser.getGameSession();
        if (gameSession == null) {
            gameSession = new GameSession();
            gameSession.setGameState(GameState.TOWN);
        }

        if (mUser.getHeroes() == null || mUser.getHeroes().isEmpty()) {
            gameSession.setGameState(GameState.CREATE);
        }

        SaveGameSession(session);
    }

    public void SaveGameSession(Session session) {
        session.removeAttribute(GameConstants.USER);
        session.setAttribute(GameConstants.USER, mUser);
    }


}
