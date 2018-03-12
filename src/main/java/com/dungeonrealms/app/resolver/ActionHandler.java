package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;

public interface ActionHandler {
    SpeechletResponse handleIntent(Session session, String intentName, Intent intent);
}
