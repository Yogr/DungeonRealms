package com.dungeonrealms.app.model;

import com.dungeonrealms.app.resolver.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameState {
    CREATE(new CreateStateResolver()),
    TOWN(new TownStateResolver()),
    SHOP(new ShopStateResolver()),
    DUNGEON(new DungeonStateResolver()),
    COMBAT(new CombatStateResolver()),
    INVENTORY(new InventoryStateResolver()),
    ABILITIES(new AbilitiesStateResolver());

    private final GameStateResolver mResolver;
}
