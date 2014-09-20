package com.corundumgames.mach.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.corundumgames.mach.components.ParticleComponent;
import com.corundumgames.mach.components.TransformComponent;
import com.corundumgames.mach.managers.ResourceManagers.CameraManager;
import com.corundumgames.mach.managers.ResourceManagers.RenderingManager;

public class ParticleSystem extends IntervalEntityProcessingSystem {
    private static final Aspect ASPECT = Aspect.getAspectForAll(ParticleComponent.class, TransformComponent.class);

    @Wire
    private ComponentMapper<ParticleComponent> particleMapper;

    @Wire
    private ComponentMapper<TransformComponent> transformMapper;

    @Wire
    private CameraManager cameras;

    @Wire
    private RenderingManager rendering;

    private Matrix4 temp;
    private Matrix4 temp2;
    private Vector2 temp3;

    public ParticleSystem(float delta) {
        super(Aspect.getAspectForAll(ParticleComponent.class, TransformComponent.class), delta);
        this.temp = new Matrix4();
        this.temp2 = new Matrix4();
        this.temp3 = new Vector2();
    }

    @Override
    protected void inserted(Entity e) {
        ParticleComponent pc = particleMapper.get(e);
        for (ParticleComponent.EffectInfo effect : pc.effects.values()) {
            if (effect.enabled) {
                effect.effect.start();
                if (effect.once) {
                    effect.effect.allowCompletion();
                }
            }
        }
    }

    @Override
    protected void begin() {
        this.temp.set(this.rendering.batch.getProjectionMatrix());
        this.temp2.set(this.cameras.worldCamera.combined);
        this.rendering.batch.setProjectionMatrix(this.temp2);
        // this.resources.getBatch().begin();
    }

    @Override
    protected void process(Entity e) {
        ParticleComponent pc = this.particleMapper.get(e);
        TransformComponent txc = this.transformMapper.get(e);
        txc.transform.getTranslation(this.temp3);

        for (ParticleComponent.EffectInfo effect : pc.effects.values()) {
            if (effect.enabled) {
                effect.effect.setPosition(this.temp3.x, this.temp3.y);
                effect.effect.draw(this.rendering.batch, this.world.getDelta());

                if (effect.once && effect.effect.isComplete()) {
                    effect.enabled = false;
                }
            }
        }
    }

    @Override
    protected void end() {
        // this.resources.getBatch().end();
        this.rendering.batch.setProjectionMatrix(this.temp);
    }
}
