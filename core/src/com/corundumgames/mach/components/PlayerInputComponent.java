package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

/**
 * @author jesse
 */
public class PlayerInputComponent extends PooledComponent implements Serializable {
    /**
     * The controller mapped to the player, if any.
     */
    public Controller controller;

    /**
     * Set of all key and button codes that will make the player go up
     */
    public IntArray up;
    public IntArray down;
    public IntArray left;
    public IntArray right;
    public IntArray bomb;
    public int leftBeam;
    public int rightBeam;
    public int pointer;
    public float targetSpeed;
    public boolean canMove;

    public PlayerInputComponent() {
        this.reset();
    }

    @Override
    public void reset() {
        this.up = IntArray.with(Keys.UP, Keys.W, Keys.DPAD_UP);
        this.down = IntArray.with(Keys.DOWN, Keys.S, Keys.DPAD_DOWN);
        this.left = IntArray.with(Keys.LEFT, Keys.A, Keys.DPAD_LEFT);
        this.right = IntArray.with(Keys.RIGHT, Keys.D, Keys.DPAD_RIGHT);
        this.bomb = IntArray.with(Keys.SPACE);
        Array<Controller> controllers = Controllers.getControllers();
        if (controllers != null && controllers.size > 0) {
            this.controller = controllers.get(0);
        }
        this.leftBeam = Buttons.LEFT;
        this.rightBeam = Buttons.RIGHT;
        this.pointer = 0;
        this.targetSpeed = 10;
        this.canMove = true;
    }

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        // TODO: Implement
    }
    
}
