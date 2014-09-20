package com.corundumgames.mach.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.corundumgames.mach.components.CollisionComponent;

public class CollisionSystem extends IntervalEntityProcessingSystem {

    public CollisionSystem(float interval) {
        super(Aspect.getAspectForAll(CollisionComponent.class), interval);
    }

    @Override
    protected void begin() {
        
    }

    @Override
    protected void process(Entity e) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void end() {

    }
}
