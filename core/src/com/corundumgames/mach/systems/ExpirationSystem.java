package com.corundumgames.mach.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.DelayedEntityProcessingSystem;
import com.corundumgames.mach.components.ExpirationComponent;
import com.corundumgames.mach.components.ExpireOnDeathComponent;
import com.corundumgames.mach.events.DeathListener;

public class ExpirationSystem extends DelayedEntityProcessingSystem implements DeathListener {

    @Wire
    private ComponentMapper<ExpirationComponent> expirationMapper;

    @Wire
    private ComponentMapper<ExpireOnDeathComponent> expireOnDeathMapper;

    public ExpirationSystem() {
        super(Aspect.getAspectForAll(ExpirationComponent.class));
    }

    @Override
    protected float getRemainingDelay(Entity e) {
        return expirationMapper.get(e).remaining;
    }

    @Override
    protected void processDelta(Entity e, float accumulatedDelta) {
        expirationMapper.get(e).remaining -= accumulatedDelta;
    }

    @Override
    protected void processExpired(Entity e) {
        e.deleteFromWorld();
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof DeathEvent) {
            return this.died((DeathEvent)event);
        }
        else {
            return false;
        }
    }

    @Override
    public boolean died(DeathEvent event) {
        if (expireOnDeathMapper.has(event.getEntity())) {
            event.getEntity().deleteFromWorld();
            return true;
        }
        else {
            return false;
        }
    }
}
