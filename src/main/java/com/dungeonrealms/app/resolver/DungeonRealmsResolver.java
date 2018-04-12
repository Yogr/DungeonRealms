package com.dungeonrealms.app.resolver;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazonaws.util.StringUtils;
import com.dungeonrealms.app.game.GameResources;
import com.dungeonrealms.app.game.Inventory;
import com.dungeonrealms.app.model.*;
import com.dungeonrealms.app.speech.*;
import com.dungeonrealms.app.util.DungeonUtils;
import com.dungeonrealms.app.util.SaveLoad;

import com.dungeonrealms.app.game.Navigation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DungeonRealmsResolver extends GameStateResolver {

    @Override
    public SpeechletResponse resolveIntent(Session session, DungeonUser user, Intent intent) {
        String intentName = (intent != null) ? intent.getName() : null;

        Map<String, ActionHandler> actions = getAllActions();
        if (actions.containsKey(intentName)) {
            return actions.get(intentName).handleIntent(session, user, intent);
        }

        return getInvalidActionResponse();
    }

    private Map<String, ActionHandler> getAllActions() {
        Map<String, ActionHandler> actions = new HashMap<>();
        actions.put(IntentNames.LOOK, mLookHandler);
        actions.put(IntentNames.GOLD_COUNT, mGoldCountHandler);
        actions.put(IntentNames.HERO_DESCRIPTION, mDescribeHeroHandler);
        actions.put(IntentNames.ITEM_DESCRIPTION, mDescribeItemHandler);
        actions.put(IntentNames.AMAZON_HELP, mHelpActionHandler);
        actions.put(IntentNames.AMAZON_CANCEL, mStopActionHandler);
        actions.put(IntentNames.AMAZON_STOP, mStopActionHandler);
        actions.put(IntentNames.MOVE_ROOM, mMoveRoomHandler);

        actions.putAll(getActions());

        return actions;
    }

    protected Map<String, ActionHandler> getActions() {
        return new HashMap<>();
    }

    private ActionHandler mHelpActionHandler = (Session session, DungeonUser user, Intent intent) -> {
        StringBuilder actionsText = new StringBuilder();

        for (String name : getActions().keySet()) {
            if (IntentNames.AMAZON_CANCEL.equals(name) ||
                IntentNames.AMAZON_HELP.equals(name) ||
                IntentNames.AMAZON_STOP.equals(name)) {
                continue;
            }
            if (IntentNames.MOVE_ROOM.equals(name)) {
                Set<String> roomExits = Navigation.getRoomExits(user);
                if (roomExits != null) {
                    for (String exit : roomExits) {
                        actionsText.append(", go ").append(exit);
                    }
                }
            } else {
                actionsText.append(", ").append(HelpActionMap.getIntentFriendlyName().get(name));
            }
        }

        String speechText;
        if (!StringUtils.isNullOrEmpty(actionsText.toString())) {
            speechText = "You may " + actionsText;
        } else {
            speechText = "There are no available actions";
        }

        return getAskResponse(CardTitle.DUNGEON_REALMS, speechText);
    };

    private ActionHandler mLookHandler = (Session session, DungeonUser user, Intent intent) -> {
        String speechText = DungeonUtils.constructFullRoomMessage(user.getGameSession());
        Area area = Navigation.getArea(user.getGameSession().getAreaId());
        Room room = null;
        if (area != null) {
            room = Navigation.getAreaRoom(area, user.getGameSession().getRoomId());
        }
        return getAskResponse(room != null ? room.getTitle() : CardTitle.DUNGEON_REALMS, speechText);
    };

    private ActionHandler mMoveRoomHandler = (session, user, intent) -> {
        Area area = Navigation.getArea(user.getGameSession().getAreaId());
        Room room = Navigation.getAreaRoom(area, user.getGameSession().getRoomId());

        boolean moveSuccessful = false;
        if (area != null && room != null) {
            Slot locationSlot = intent.getSlot(SlotNames.LOCATION);
            if (locationSlot != null) {
                String moveLocation = locationSlot.getValue();
                if (!StringUtils.isNullOrEmpty(moveLocation)) {
                    String newRoomId = room.getExits().get(moveLocation);
                    if (newRoomId != null) {
                        if (newRoomId.equals(BaseModel.INVALID)) {
                            moveSuccessful = Navigation.moveToTown(user.getGameSession());
                        } else {
                            moveSuccessful = Navigation.moveToRoom(user, area, newRoomId);
                        }
                    }
                }
            }
        }

        String speechText;
        if (moveSuccessful) {
            speechText = DungeonUtils.constructFullRoomMessage(user.getGameSession());
        } else {
            speechText = "You cannot move in that direction";
        }

        Room currentRoom = Navigation.getAreaRoom(area, user.getGameSession().getRoomId());
        return getAskResponse(currentRoom != null ? currentRoom.getTitle() : CardTitle.DUNGEON_REALMS, speechText);
    };

    private ActionHandler mStopActionHandler = (Session session, DungeonUser user, Intent intent) -> {
        SaveLoad.saveUser(user);
        PlainTextOutputSpeech speech = getPlainTextOutputSpeech(Responses.GOODBYE);
        SpeechletResponse response = SpeechletResponse.newTellResponse(speech);
        response.setNullableShouldEndSession(true);
        return response;
    };

    private ActionHandler mDescribeHeroHandler = (Session session, DungeonUser user, Intent intent) -> {
        Slot heroNameSlot = intent.getSlot(SlotNames.HERO);
        String heroName = heroNameSlot != null ? heroNameSlot.getValue() : null;
        if (!StringUtils.isNullOrEmpty(heroName)) {
            Hero hero = user.findHeroByName(heroName);
            if (hero != null) {
                StringBuilder heroDescrip = new StringBuilder();
                heroDescrip.append(String.format(Responses.DESCRIBE_HERO, hero.getName(), hero.getLevel()));
                StringBuilder equipmentSb = new StringBuilder();
                Inventory.buildEquipmentString(equipmentSb, hero);
                heroDescrip.append(String.format(Responses.DESCRIBE_HERO_EQUIP, hero.getName(), equipmentSb.toString()));
                return getAskResponse(CardTitle.DUNGEON_REALMS, heroDescrip.toString());
            } else {
                return getAskResponse(CardTitle.DUNGEON_REALMS, String.format(Responses.THING_NOT_FOUND, heroName));
            }
        }
        return getAskResponse(CardTitle.DUNGEON_REALMS, Responses.NOT_FOUND);
    };

    private ActionHandler mDescribeItemHandler = (Session session, DungeonUser user, Intent intent) -> {
        Slot itemNameSlot = intent.getSlot(SlotNames.ITEM);
        String itemName = itemNameSlot != null ? itemNameSlot.getValue() : null;
        if (!StringUtils.isNullOrEmpty(itemName)) {
            // Search through hero equipment and then backpack for this item
            for (Hero hero : user.getHeroes()) {
                Item foundItem = null;

                // Try to resolve by exact name first to favor descriptive name over similar name
                for (String itemId : hero.getEquipment()) {
                    Item itemTmp = GameResources.getInstance().getItems().get(itemId);
                    if (itemTmp.getName().equals(itemName)) {
                        foundItem = itemTmp;
                        break;
                    }
                }

                // Look at alias as a fallback if not found
                if (foundItem == null) {
                    for (String itemId : hero.getEquipment()) {
                        Item itemTmp = GameResources.getInstance().getItems().get(itemId);
                        if (itemTmp.getAlias().equals(itemName)) {
                            foundItem = itemTmp;
                            break;
                        }
                    }
                }

                if (foundItem != null) {
                    StringBuilder itemSb = new StringBuilder();
                    itemSb.append(String.format(Responses.DESCRIBE_ITEM, foundItem.getDescription()));
                    if (foundItem.getAttack() > 0 ||
                            foundItem.getDefense() > 0 ||
                            foundItem.getSpellpower() > 0) {
                        itemSb.append("It offers ");
                        if (foundItem.getAttack() > 0) {
                            itemSb.append(foundItem.getAttack()).append(" attack power; ");
                        }
                        if (foundItem.getDefense() > 0) {
                            itemSb.append(foundItem.getDefense()).append(" armor rating; ");
                        }
                        if (foundItem.getSpellpower() > 0) {
                            itemSb.append(foundItem.getSpellpower()).append(" magical power; ");
                        }
                    }
                    if (foundItem.getCost() > 0) {
                        itemSb.append("It is worth ").append(foundItem.getCost()/2).append(" gold coins; ");
                    }
                    return getAskResponse(CardTitle.DUNGEON_REALMS, itemSb.toString());
                } else {
                    return getAskResponse(CardTitle.DUNGEON_REALMS, String.format(Responses.THING_NOT_FOUND, itemName));
                }
            }
        }
        return getAskResponse(CardTitle.DUNGEON_REALMS, Responses.NOT_FOUND);
    };

    private ActionHandler mGoldCountHandler = (Session session, DungeonUser user, Intent intent) -> {
        int gold = user.getGold();
        String speechText = String.format(Responses.GOLD_COUNT, gold);
        return getAskResponse(CardTitle.DUNGEON_REALMS, speechText);
    };

    protected SpeechletResponse getInvalidActionResponse() {
        return getAskResponse(CardTitle.DUNGEON_REALMS, Responses.INVALID_ACTION_RESPONSE);
    }

    /**
     * Basic return speech with Dungeon Realms card
     * @param speechText Your message to user
     * @return Speechlet with Dungeon Realms card and custom text
     */
    protected SpeechletResponse getPromptedAskResponse(String cardTitle, String speechText) {
        String doNextAppendedString = speechText + Responses.PROMPT_ACTION;
        return getAskResponse(cardTitle, doNextAppendedString);
    }

}
