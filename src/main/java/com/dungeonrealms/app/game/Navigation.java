package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.*;

import java.util.List;
import java.util.Set;

public class Navigation {

    public static boolean moveToRoom(GameSession gameSession, Dungeon dungeon, String roomId) {
        if (dungeon.getRooms().containsKey(roomId)) {
            gameSession.setRoomId(roomId);
            Room newRoom = getDungeonRoom(dungeon, roomId);

            for (String monsterId : newRoom.getMonsterIds()) {
                Monster monster = GameResources.getInstance().getMonsters().get(monsterId);
                MonsterInstance monsterInstance = new MonsterInstance(monster);
                gameSession.getMonsters().add(monsterInstance);
                gameSession.setGameState(GameState.COMBAT);
            }

            for (String trapId : newRoom.getTrapIds()) {
                // Trap trap = GameResources.getInstance().getTraps().get(trapId);
                // TrapInstance trapInstance = new TrapInstance(trap);
                // gameSession.getTraps().add(trapInstance);
            }

            for (String treasureId : newRoom.getTreasureIds()) {
                // Treasure treasure = GameResources.getInstance().getTreasures().get(treasureId);
                // TreasureInstance treasureInstance = new TreasureInstance(treasure);
                // gameSession.getTreasures().add(treasureInstance);
            }

            return true;
        }
        return false;
    }

    public static boolean moveToDungeon(GameSession gameSession, Dungeon dungeon, List<Hero> heroes) {
        gameSession.setGameState(GameState.DUNGEON);
        gameSession.setDungeonId(dungeon.getId());
        List<HeroInstance> heroInstances = gameSession.getHeroInstances();
        for (Hero hero : heroes) {
            heroInstances.add(new HeroInstance(hero));
        }
        return moveToRoom(gameSession, dungeon, dungeon.getRooms().keySet().iterator().next());
    }

    public static boolean moveToTown(GameSession gameSession) {
        gameSession.clearDungeonVars();
        gameSession.setGameState(GameState.TOWN);
        return true;
    }

    public static boolean moveToShop(GameSession gameSession) {
        gameSession.setGameState(GameState.SHOP);
        return true;
    }

    public static Set<String> getRoomExits(DungeonUser user) {
        Dungeon dungeon = getDungeon(user.getGameSession().getDungeonId());
        if (dungeon != null) {
            Room room = getDungeonRoom(dungeon, user.getGameSession().getRoomId());
            if (room != null) {
                return room.getExits().keySet();
            }
        }
        return null;
    }

    public static Dungeon getDungeon(String dungeonId) {
        if (dungeonId != null && !BaseModel.INVALID.equals(dungeonId)) {
            return GameResources.getInstance().getDungeons().get(dungeonId);
        }
        return null;
    }

    public static Room getDungeonRoom(Dungeon dungeon, String roomId) {
        if (roomId != null && !BaseModel.INVALID.equals(roomId)) {
            return dungeon.getRooms().get(roomId);
        }
        return null;
    }
}
