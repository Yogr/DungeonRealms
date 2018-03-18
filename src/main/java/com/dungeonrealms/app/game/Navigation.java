package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.*;

import java.util.Set;

public class Navigation {

    public static boolean moveToRoom(DungeonUser user, Dungeon dungeon, String roomId) {
        if (dungeon.getRooms().containsKey(roomId)) {
            user.getGameSession().setRoomId(roomId);
            Room newRoom = getDungeonRoom(dungeon, roomId);

            for (String monsterId : newRoom.getMonsterIds()) {
                Monster monster = GameResources.getInstance().getMonsters().get(monsterId);
                MonsterInstance monsterInstance = new MonsterInstance(monster);
                user.getGameSession().getMonsters().add(monsterInstance);
                user.getGameSession().setGameState(GameState.COMBAT);
            }

            for (String trapId : newRoom.getTrapIds()) {
                // Trap trap = GameResources.getInstance().getTraps().get(trapId);
                // TrapInstance trapInstance = new TrapInstance(trap);
                // user.getGameSession().getTraps().add(trapInstance);
            }

            for (String treasureId : newRoom.getTreasureIds()) {
                // Treasure treasure = GameResources.getInstance().getTreasures().get(treasureId);
                // TreasureInstance treasureInstance = new TreasureInstance(treasure);
                // user.getGameSession().getTreasures().add(treasureInstance);
            }

            return true;
        }
        return false;
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
