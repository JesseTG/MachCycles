package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class ExpirationComponent extends PooledComponent implements Serializable {

    public float remaining;

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        this.remaining = jsonData.getFloat("remaining", 0);
    }

    @Override
    protected void reset() {
        this.remaining = 0;
    }
}
