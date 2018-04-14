package com.dungeonrealms.app.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Level {
    LEVEL_1(0, 2), // 6 = 3 monsters required
    LEVEL_2(6, 3), // 9 = 3 monsters req
    LEVEL_3(15, 4), // 20 = 5
    LEVEL_4(35, 5), // 65 = 13
    LEVEL_5(100, 7), // 150 = 22
    LEVEL_6(250, 10), // 450 = 45
    LEVEL_7(700, 20), // 1000 = 50
    LEVEL_8(1700, 42), // 2100 = 52
    LEVEL_9(3800, 85), // 4400 = 55
    LEVEL_10(8200, 207), // 11800 = 57
    LEVEL_11(20000, 416), // 25000 = 60
    LEVEL_12(45000, 692), // 55000 = 65
    LEVEL_13(100000, 900);

    private final int mExpRequired;
    private final int mExpRewardForMonster;
}
