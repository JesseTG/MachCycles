package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class PlayerComponent extends PooledComponent implements Serializable {
    public long score;
    public int multiplier;

    public PlayerComponent() {
        this.reset();
    }

    @Override
    public void reset() {
        this.score = 0;
        this.multiplier = 1;
    }

    @Override
    public void write(Json json) {}

    @Override
    public void read(Json json, JsonValue jsonData) {
        this.score = jsonData.getLong("score", 0);
        this.multiplier = jsonData.getInt("multiplier", 1);
    }
}
