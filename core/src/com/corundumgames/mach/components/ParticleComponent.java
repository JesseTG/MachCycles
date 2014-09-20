package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.corundumgames.Assets;

public class ParticleComponent extends PooledComponent implements Serializable {
    private static AssetManager assets;
    private static Pool<EffectInfo> effectInfoPool = Pools.get(EffectInfo.class);
    private static ObjectMap<String, ParticleEffectPool> effectPools = new ObjectMap<>();

    private static final int INITIAL_POOL_CAPACITY = 8;
    private static final int MAX_POOL_CAPACITY = 64;

    public final ObjectMap<String, EffectInfo> effects;

    public static void setAssetManager(AssetManager assets) {
        ParticleComponent.assets = assets;

        if (assets == null) {
            effectPools.clear();
        }
    }

    public ParticleComponent() {
        this.effects = new ObjectMap<>(4);
    }

    @Override
    protected void reset() {
        for (EffectInfo effect : this.effects.values()) {
            ((PooledEffect)effect.effect).free();
            effectInfoPool.free(effect);
        }

        this.effects.clear();
    }

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        JsonValue effects = jsonData.get("effects");
        if (effects != null && effects.isObject()) {
            for (JsonValue effect : effects) {
                EffectInfo ei = effectInfoPool.obtain();

                String name = effect.getString("effect", null);
                ei.once = effect.getBoolean("once", false);
                ei.enabled = effect.getBoolean("enabled", false);
                String sprite = effect.getString("sprite", null);

                if (!assets.isLoaded(name)) {
                    assets.load(name, ParticleEffect.class, Assets.PARTICLE_EFFECT_PARAMETER);
                    assets.finishLoading();
                }

                ParticleEffect e = assets.get(name, ParticleEffect.class);

                if (sprite == null) {
                    if (!effectPools.containsKey(name)) {
                        effectPools.put(name, new ParticleEffectPool(e, INITIAL_POOL_CAPACITY, MAX_POOL_CAPACITY));
                        Gdx.app.debug("ParticleComponent", "New pool " + name + " created");
                    }

                    ei.effect = effectPools.get(name).obtain();
                }
                else {
                    String full = name + sprite;

                    ParticleEffect newe = new ParticleEffect(e);

                    TextureRegion region = assets.get(Assets.ATLAS).findRegion(sprite);

                    if (region != null) {
                        Sprite s = new Sprite(region);
                        for (ParticleEmitter emitter : newe.getEmitters()) {
                            emitter.setSprite(s);
                        }
                    }

                    if (!effectPools.containsKey(full)) {
                        effectPools.put(full, new ParticleEffectPool(newe, INITIAL_POOL_CAPACITY, MAX_POOL_CAPACITY));
                        Gdx.app.debug("ParticleComponent", "New pool " + full + " created");
                    }

                    ei.effect = effectPools.get(full).obtain();
                }

                this.effects.put(effect.name(), ei);
            }
        }
    }

    public static class EffectInfo implements Poolable {
        public ParticleEffect effect;
        public boolean enabled;
        public boolean once;

        public EffectInfo() {
            this.reset();
        }

        @Override
        public void reset() {
            this.effect = null;
            this.enabled = true;
            this.once = false;
        }
    }
}
