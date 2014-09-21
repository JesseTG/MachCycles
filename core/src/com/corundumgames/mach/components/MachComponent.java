package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

/**
 * Given to any MACH Cycle, human or computer-controlled.
 * 
 * @author jesse
 */
public class MachComponent extends PooledComponent {
    /**
     * The next control action the MACH Cycle will under take. Will be reset to
     * {@link Action#NONE} whenever it's processed.
     */
    public Action action;
    
   /* MoveToAction a = new MoveToAction();
    a.setPosition(x, y);*/
    

    public MachComponent() {
        this.reset();
    }

    @Override
    protected void reset() {
        this.action = Action.NONE;
    }

    /**
     * Represents a possible action a player (either human or AI) could take
     * in-game. This refers to conscious control decisions (e.g. turning), not
     * game events (e.g. crashing).
     * 
     * @author jesse
     */
    public static enum Action {
        NONE,
        TURN_LEFT,
        TURN_RIGHT,
        SPEED_UP_START,
        SPEED_UP_STOP,
        SLOW_DOWN_START,
        SLOW_DOWN_STOP,
    }

}
