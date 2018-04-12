package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.*;

import java.util.Set;

public class Navigation {

    public static boolean moveToRoom(DungeonUser user, Area area, String roomId) {
        GameSession gameSession = user.getGameSession();
        Room newRoom = area.getRoomById(roomId);
        if (newRoom != null) {
            if (newRoom.getRoomType() == Room.RoomType.LINK_TO_AREA) {
                Area newArea = getArea(newRoom.getLinkAreaId());
                return moveToArea(user, newArea, newRoom.getLinkRoomId());
            }

            gameSession.setRoomId(roomId);
            GameState prevState = gameSession.getGameState();
            gameSession.setGameStateByRoomType(newRoom.getRoomType());

            if (gameSession.getGameState().equals(GameState.DUNGEON)) {
                if (!prevState.equals(GameState.DUNGEON) && !prevState.equals(GameState.COMBAT)) {
                    gameSession.startDungeonSession(user.getHeroes());
                }

                boolean isCleared = false;
                for (String clearedRoom : gameSession.getClearedRoomIds()) {
                    if (clearedRoom.equals(roomId)) {
                        isCleared = true;
                        break;
                    }
                }

                if (!isCleared) {
                    RandomTable.SpawnTable spawnTable = GameResources.getInstance().getSpawnTables().get(newRoom.getSpawnTableId());
                    if (spawnTable != null) {
                        for (Monster monster : spawnTable.draw()) {
                            MonsterInstance monsterInstance = new MonsterInstance(monster);
                            gameSession.getMonsters().add(monsterInstance);

                            if (monster.isHostile()) {
                                gameSession.setGameState(GameState.COMBAT);
                            }
                        }
                    }
                }
            } else {
                if (prevState.equals(GameState.DUNGEON)) {
                    gameSession.clearDungeonSession();
                }
            }

            /*
            for (String trapId : newRoom.getTrapIds()) {
                // Trap trap = GameResources.getInstance().getTraps().get(trapId);
                // TrapInstance trapInstance = new TrapInstance(trap);
                // gameSession.getTraps().add(trapInstance);
            }*/

            /*
            for (String treasureId : newRoom.getTreasureIds()) {
                // Treasure treasure = GameResources.getInstance().getTreasures().get(treasureId);
                // TreasureInstance treasureInstance = new TreasureInstance(treasure);
                // gameSession.getTreasures().add(treasureInstance);
            }*/

            return true;
        }
        return false;
    }

    public static boolean moveToArea(DungeonUser user, Area area, String roomId) {
        user.getGameSession().setAreaId(area.getId());
        return moveToRoom(user, area, roomId);
    }

    public static boolean moveToArea(DungeonUser user, Area area) {
        return moveToArea(user, area, "0");
    }

    public static boolean moveToTown(GameSession gameSession) {
        gameSession.clearDungeonSession();
        gameSession.setGameState(GameState.TOWN);
        return true;
    }

    public static boolean moveToShop(GameSession gameSession) {
        gameSession.setGameState(GameState.SHOP);
        return true;
    }

    public static Set<String> getRoomExits(DungeonUser user) {
        Area area = getArea(user.getGameSession().getAreaId());
        if (area != null) {
            Room room = getAreaRoom(area, user.getGameSession().getRoomId());
            if (room != null) {
                return room.getExits().keySet();
            }
        }
        return null;
    }

    public static Area getArea(String areaId) {
        if (areaId != null && !BaseModel.INVALID.equals(areaId)) {
            return GameResources.getInstance().getAreas().get(areaId);
        }
        return null;
    }

    public static Room getAreaRoom(Area area, String roomId) {
        if (area != null && roomId != null && !BaseModel.INVALID.equals(roomId)) {
            return area.getRoomById(roomId);
        }
        return null;
    }
}
