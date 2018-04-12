package com.dungeonrealms.app.dummy;

import com.dungeonrealms.app.model.Area;
import com.dungeonrealms.app.model.Item.ItemType;
import com.dungeonrealms.app.model.Item;
import com.dungeonrealms.app.game.GameResources;
import com.dungeonrealms.app.model.Monster;
import com.dungeonrealms.app.model.RandomTable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {

        Iterator<RandomTable.SpawnTable> iterator = GameResources.getInstance().getSpawnTables().values().iterator();
        //iterator.next();
        RandomTable.SpawnTable m = iterator.next();
        System.out.println("RandomTable.SpawnTable is " + m.getName());
    }
}
