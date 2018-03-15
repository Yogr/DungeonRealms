package com.dungeonrealms.app.dummy;

import com.dungeonrealms.app.model.Item;
import com.dungeonrealms.app.model.ItemType;
import com.dungeonrealms.app.util.Config;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(Item.builder().id(1).name("Short Sword").type(ItemType.WEAPON).build());
        itemList.add(Item.builder().id(2).name("Leather Armor").type(ItemType.ARMOR).build());
        itemList.add(Item.builder().id(3).name("Leather Helmet").type(ItemType.HELMET).build());
        itemList.add(Item.builder().id(4).name("Gold Bling").type(ItemType.GOLD).build());

        try {
            System.out.println(Config.getInstance().getJacksonMapper().writeValueAsString(itemList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
