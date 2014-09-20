package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class ScoreGivingComponent extends PooledComponent implements Serializable {
    public int prize;
    public boolean bonus;

    public ScoreGivingComponent() {
        this.reset();
    }

    @Override
    protected void reset() {
        this.prize = 10;
        this.bonus = false;
    }

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        this.prize = jsonData.getInt("prize", 10);
    }
}
