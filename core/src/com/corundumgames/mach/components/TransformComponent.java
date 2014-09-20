package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Decoupled from the MeshComponent so we can reuse MeshComponents while giving
 * each Entity a unique TransformComponent
 * 
 * @author jesse
 */
public class TransformComponent extends PooledComponent implements Serializable {
    /**
     * Matrix transforms should be done in hardware (passed to the shader), not
     * in software (passed to Java code)
     */
    public Matrix3 transform;
    public boolean changed;

    public TransformComponent() {
        this.transform = new Matrix3();
        this.changed = false;
    }

    @Override
    public void reset() {
        this.transform.idt();
        this.changed = false;
    }

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        this.reset();
    }

}
