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
    }

    @Override
    protected void process(Entity e) {
        CollisionComponent cc = collisionMapper.get(e);
        VelocityComponent vc = velocityMapper.get(e);
        TransformComponent txc = transformMapper.get(e);

        vc.velocity.x += vc.acceleration.x * this.acc;
        vc.velocity.y += vc.acceleration.y * this.acc;

        cc.rect.x += vc.velocity.x * this.acc;
        cc.rect.y += vc.velocity.y * this.acc;

        txc.transform.trn(vc.velocity.x * this.acc, vc.velocity.y * this.acc);
    }
}
