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
    public final Vector2 velocity;

    /**
     * Linear acceleration in pixels per second per second
     */
    public final Vector2 acceleration;

    public VelocityComponent() {
        this.velocity = new Vector2();
        this.acceleration = new Vector2();
    }

    @Override
    protected void reset() {
        this.velocity.setZero();
        this.acceleration.setZero();
    }

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        JsonValue v = jsonData.get("velocity");

        if (v != null) {
            this.velocity.x = v.getFloat("x", 0);
            this.velocity.y = v.getFloat("y", 0);
        }

        JsonValue a = jsonData.get("acceleration");

        if (a != null) {
            this.acceleration.x = a.getFloat("x", 0);
            this.acceleration.y = a.getFloat("y", 0);
        }
    }
}
