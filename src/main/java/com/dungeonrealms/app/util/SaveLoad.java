package com.dungeonrealms.app.util;

import com.dungeonrealms.app.model.DungeonUser;

public class SaveLoad {
    /***
     * This method loads the DungeonUser from the DynamoDB Database
     * into the game using the userId (Alexa userId)
     * @param userId the Alexa userId of the user making the request
     * @return DungeonUser
     */
    public static DungeonUser LoadUser(String userId) {
       return Config.getInstance().getDbMapper().load(DungeonUser.class, userId);
    }

    /***
     * This method saves the current user to our DynamoDB Database
     * @param dungeonUser object that is to be saved to DynamoDB
     */
    public static void SaveUser(DungeonUser dungeonUser) {
        Config.getInstance().getDbMapper().save(dungeonUser);
    }

}
