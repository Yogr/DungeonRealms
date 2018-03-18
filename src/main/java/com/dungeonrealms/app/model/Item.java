package com.dungeonrealms.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private final String mId;
    private final String mName;
    private final ItemType mType;

    public Item(@JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("type") ItemType type) {
        mId = id;
        mName = name;
        mType = type;
    }
}
