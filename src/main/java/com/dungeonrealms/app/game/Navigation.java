package com.dungeonrealms.app.game;

import com.dungeonrealms.app.dummy.GetDummy;
import com.dungeonrealms.app.model.Dungeon;
import com.dungeonrealms.app.model.DungeonUser;
import com.dungeonrealms.app.model.Monster;
import com.dungeonrealms.app.model.MonsterInstance;

public class Navigation {

    public static boolean MoveToRoom(DungeonUser user, Dungeon dungeon, int roomId) {
        if (dungeon.getRooms().containsKey(roomId)) {
            user.getGameSession().setRoomId(roomId);
            // TODO: Load monsters, traps, treasure into game session (properly)
            // HACK
            if (roomId == 2) {
                Monster monster = GetDummy.dummyGoblin();
                MonsterInstance monsterInstance = new MonsterInstance(monster);
                user.getGameSession().getMonsters().add(monsterInstance);
            }
            return true;
        }
        return false;
    }
}
