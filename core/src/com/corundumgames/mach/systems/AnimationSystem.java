package com.corundumgames.mach.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.corundumgames.mach.components.AnimationComponent;
import com.corundumgames.mach.components.TextureComponent;

public class AnimationSystem extends IntervalEntityProcessingSystem {
    private static final Aspect ASPECT = Aspect.getAspectForAll(AnimationComponent.class, TextureComponent.class);

    @Wire()
    private ComponentMapper<AnimationComponent> animationMapper;

    @Wire()
    private ComponentMapper<TextureComponent> textureMapper;

    public AnimationSystem(float interval) {
        super(Aspect.getAspectForAll(AnimationComponent.class, TextureComponent.class), interval);
    }

    @Override
    protected void process(Entity e) {
        TextureComponent tx = textureMapper.get(e);
        AnimationComponent ac = animationMapper.get(e);

        if (ac.current != null) {
            ac.time += this.world.getDelta();
            tx.region = (AtlasRegion)ac.current.getKeyFrame(ac.time);
        }
    }
}
