package com.corundumgames.mach.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.corundumgames.mach.components.CollisionComponent;
import com.corundumgames.mach.components.MachComponent;
import com.corundumgames.mach.components.VelocityComponent;

public class CycleControlSystem extends EntityProcessingSystem {

    @Wire
    private ComponentMapper<MachComponent> machMapper;

    @Wire
    private ComponentMapper<VelocityComponent> velocityMapper;

    @Wire
    private ComponentMapper<CollisionComponent> collisionMapper;

    public CycleControlSystem() {
        super(Aspect.getAspectForAll(MachComponent.class, VelocityComponent.class, CollisionComponent.class));
    }

    @Override
    protected void process(Entity e) {
        MachComponent mc = machMapper.get(e);
        VelocityComponent vc = velocityMapper.get(e);

        switch (mc.action) {
            case TURN_LEFT: {
                vc.linear.rotate90(1);
                break;
            }
            case TURN_RIGHT: {
                vc.linear.rotate90(-1);
                break;
            }
            case SPEED_UP_START: {
                // hit the accelerator
                break;
            }
            case SPEED_UP_STOP: {
                // release the accelerator
                break;
            }
            case SLOW_DOWN_START: {
                // hit the brakes
                break;
            }
            case SLOW_DOWN_STOP: {
                // release the brakes
                break;
            }
            default:
                break;
        }
        
        mc.action = MachComponent.Action.NONE;
    }

}
