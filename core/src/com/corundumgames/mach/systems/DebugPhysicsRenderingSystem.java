package com.corundumgames.mach.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.artemis.systems.IntervalEntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.corundumgames.mach.components.CollisionComponent;
import com.corundumgames.mach.components.VelocityComponent;
import com.corundumgames.mach.managers.ResourceManagers.CameraManager;

public class DebugPhysicsRenderingSystem extends IntervalEntityProcessingSystem {

    @Wire
    private ComponentMapper<CollisionComponent> collisionMapper;

    @Wire
    private CameraManager cameras;
    
    private ShapeRenderer collisionRenderer;
    private boolean enabled;

    public DebugPhysicsRenderingSystem(float interval) {
        super(Aspect.getAspectForAll(CollisionComponent.class), interval);
        this.enabled = true;
    }

    @Override
    protected boolean checkProcessing() {
        return this.enabled;
    }

    @Override
    protected void initialize() {
        this.collisionRenderer = new ShapeRenderer();
    }

    @Override
    protected void begin() {
        this.collisionRenderer.begin(ShapeType.Line);
        this.collisionRenderer.setColor(Color.RED);
        this.collisionRenderer.setProjectionMatrix(this.cameras.worldCamera.combined);
    }

    @Override
    protected void process(Entity e) {
        CollisionComponent cc = collisionMapper.get(e);
        this.collisionRenderer.rect(cc.rect.x, cc.rect.y, cc.rect.width, cc.rect.height);
    }

    @Override
    protected void end() {
        this.collisionRenderer.end();
    }

    @Override
    protected void dispose() {
        this.collisionRenderer.dispose();
        this.collisionRenderer = null;
    }

}
