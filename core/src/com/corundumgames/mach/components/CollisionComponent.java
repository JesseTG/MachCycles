package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class CollisionComponent extends PooledComponent implements Serializable {
    public final Rectangle rect;
    public short group;
    public short is;
    public short collidesWith;

    public CollisionComponent() {
        this.rect = new Rectangle();
    }

    @Override
    protected void reset() {
        this.rect.set(0, 0, 0, 0);
        this.group = 0;
        this.is = 1;
        this.collidesWith = ~0;
    }

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        this.rect.set(
                jsonData.getFloat("x", 0),
                jsonData.getFloat("y", 0),
                jsonData.getFloat("width", 0),
                jsonData.getFloat("height", 0));

        // x and y are the *bottom left* of the collision rectangle
        // typically they should be 0

        this.group = jsonData.getShort("group", (short)0);
        this.is = jsonData.getShort("is", (short)1);
        this.collidesWith = jsonData.getShort("collidesWith", (short)(~0));
        // Inspired by Box2D
    }
}
