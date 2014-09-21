package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.corundumgames.mach.systems.MotionSystem;

/**
 * Given to any game entity that moves.
 * 
 * @see MotionSystem
 * 
 * @author jesse
 */
public class VelocityComponent extends PooledComponent implements Serializable {
    /**
     * Linear velocity in pixels per second
     */
    public final Vector2 linear;

    public VelocityComponent() {
        this.linear = new Vector2();
    }

    @Override
    protected void reset() {
        this.linear.setZero();
    }

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        this.linear.x = jsonData.getFloat("x", 0);
        this.linear.y = jsonData.getFloat("y", 0);
    }
}
