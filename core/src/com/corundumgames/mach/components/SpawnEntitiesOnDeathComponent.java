package com.corundumgames.mach.components;

import net.dermetfan.utils.Pair;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class SpawnEntitiesOnDeathComponent extends PooledComponent implements Serializable {
    public Array<Pair<String, Float>> toSpawn;

    public SpawnEntitiesOnDeathComponent() {
        this.toSpawn = new Array<>(4);
    }

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        JsonValue entities = jsonData.get("entities");
        for (JsonValue entity : entities) {
            Pair<String, Float> p = new Pair<>(entity.getString("name", null), entity.getFloat("probability", 1));
            this.toSpawn.add(p);
        }
    }

    @Override
    protected void reset() {
        this.toSpawn.clear();
    }

}
