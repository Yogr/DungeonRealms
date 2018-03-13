package com.dungeonrealms.app.game;

import com.dungeonrealms.app.model.Hero;

public class CreateHero {

    public static Hero create(String heroName) {
        Hero newHero = new Hero();
        newHero.setName(heroName);
        newHero.setExperience(0);
        return newHero;
    }
}
