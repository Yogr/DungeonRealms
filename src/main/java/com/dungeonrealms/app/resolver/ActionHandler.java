package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.dungeonrealms.app.model.DungeonUser;

public interface ActionHandler {
    SpeechletResponse handleIntent(Session session, DungeonUser user, Intent intent);
}
