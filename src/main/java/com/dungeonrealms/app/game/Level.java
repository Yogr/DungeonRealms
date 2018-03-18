package com.dungeonrealms.app.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Level {
    LEVEL_1(0),
    LEVEL_2(6),
    LEVEL_3(15),
    LEVEL_4(35),
    LEVEL_5(100),
    LEVEL_6(250),
    LEVEL_7(700),
    LEVEL_8(1800),
    LEVEL_9(3600),
    LEVEL_10(8000),
    LEVEL_11(20000),
    LEVEL_12(45000),
    LEVEL_13(100000);

    private final int mExpRequired;
}
