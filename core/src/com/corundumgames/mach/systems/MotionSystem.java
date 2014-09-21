package com.corundumgames.mach.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.corundumgames.mach.components.CollisionComponent;
import com.corundumgames.mach.components.TransformComponent;
import com.corundumgames.mach.components.VelocityComponent;

public class MotionSystem extends IntervalEntityProcessingSystem {

    @Wire
    private ComponentMapper<CollisionComponent> collisionMapper;

    @Wire
    private ComponentMapper<VelocityComponent> velocityMapper;

    @Wire
    private ComponentMapper<TransformComponent> transformMapper;

    public MotionSystem(float interval) {
        super(Aspect.getAspectForAll(CollisionComponent.class, VelocityComponent.class, TransformComponent.class),
                interval);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void process(Entity e) {
        CollisionComponent cc = collisionMapper.get(e);
        VelocityComponent vc = velocityMapper.get(e);
        TransformComponent txc = transformMapper.get(e);

        cc.rect.x += (vc.linear.x / this.acc);
        cc.rect.y += (vc.linear.y / this.acc);

        txc.transform.translate(vc.linear);
    }
}
