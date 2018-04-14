package com.dungeonrealms.app.resolver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DungeonAction {
    private final String mFriendlyName;
    private final ActionHandler mHandler;
    private final boolean mIsHidden;
}
