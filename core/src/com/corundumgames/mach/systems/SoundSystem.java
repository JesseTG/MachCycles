package com.corundumgames.mach.systems;

import java.util.Objects;

import net.dermetfan.utils.Pair;
import net.dermetfan.utils.math.MathUtils;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.corundumgames.Assets;
import com.corundumgames.SoundKeys;
import com.corundumgames.mach.components.SoundComponent;
import com.corundumgames.mach.events.*;
import com.corundumgames.mach.managers.ResourceManagers.AssetsManager;

public class SoundSystem extends EntityProcessingSystem implements SpawnListener, DeathListener, DamageListener,
    IntegerChangedListener {
    private static final Aspect ASPECT = Aspect.getAspectForAll(SoundComponent.class);

    @Wire
    private ComponentMapper<SoundComponent> soundMapper;

    @Wire
    private AssetsManager assets;

    private ObjectSet<SoundJob> queued;

    private Pool<SoundJob> jobPool;

    private float volumeScale;

    private Sound oneUp;
    private Sound levelComplete;
    private Sound getItem;
    private Sound getMultiplier;

    public SoundSystem() {
        super(Aspect.getAspectForAll(SoundComponent.class));
        this.queued = new ObjectSet<>();
        this.jobPool = new Pool<SoundJob>()
        {
            @Override
            protected SoundJob newObject() {
                return new SoundJob();
            }
        };

    }

    @Override
    protected void initialize() {
        this.volumeScale = 1;

        AssetManager assets = this.assets.assets;
    }

    @Override
    protected void begin() {
        if (this.queued.size > 0) {
            for (SoundJob job : this.queued) {
                if (job.loop) {
                    job.sound.loop(job.volume * this.volumeScale, job.pitch, job.pan);
                }
                else {
                    job.sound.play(job.volume * this.volumeScale, job.pitch, job.pan);
                }

                this.jobPool.free(job);
            }

            this.queued.clear();
        }
    }

    @Override
    protected void process(Entity e) {}

    @Override
    public boolean handle(Event e) {
        if (e instanceof DamageEvent) {
            return this.damaged((DamageEvent)e);
        }
        else if (e instanceof DeathEvent) {
            return this.died((DeathEvent)e);
        }
        else if (e instanceof SpawnEvent) {
            return this.spawned((SpawnEvent)e);
        }
        else if (e instanceof IntegerChangedEvent) {
            return this.changed((IntegerChangedEvent)e);
        }
        else {
            return false;
        }
    }

    @Override
    public boolean damaged(DamageEvent event) {
        return false;
    }

    @Override
    public boolean died(DeathEvent event) {
        return this.queueSound(event.getEntity(), SoundKeys.DEATH);
    }

    @Override
    public boolean spawned(SpawnEvent event) {
        return this.queueSound(event.getEntity(), SoundKeys.SPAWN);
    }

    public void loopSound(Entity e, Sound sound) {
        if (sound == null) return;
        SoundJob job = this.jobPool.obtain();
        job.sound = sound;

        job.loop = true;

        if (!this.queued.contains(job)) {
            this.queued.add(job);
        }
    }

    public boolean queueSound(Entity e, String key) {
        if (key == null || e == null) return false;

        SoundJob job = this.jobPool.obtain();
        SoundComponent sc = soundMapper.getSafe(e);

        if (sc == null) return false;

        return this.queueSound(e, sc.sounds.get(key, null));
    }

    public boolean queueSound(Sound sound) {
        if (sound == null) return false;

        SoundJob job = this.jobPool.obtain();
        job.sound = sound;

        if (!this.queued.contains(job)) {
            this.queued.add(job);
        }

        return true;
    }

    public boolean queueSound(Entity e, Sound sound) {
        return this.queueSound(sound);
    }

    public void stopSound(Entity e, Sound sound) {
        sound.stop();
    }

    @Override
    public boolean changed(IntegerChangedEvent event) {
        switch (event.getName()) {
            case IntegerChangedListener.LIVES: {
                if (event.getNewValue() > event.getOldValue()) {
                    this.queueSound(this.oneUp);
                    return true;
                }
                else {
                    return false;
                }
            }
            default:
                return false;
        }
    }

    private static class SoundJob implements Poolable {
        Sound sound;
        float volume;
        float pitch;
        float pan;
        boolean loop;

        public SoundJob() {
            this.reset();
        }

        @Override
        public void reset() {
            this.sound = null;
            this.volume = 1;
            this.pan = 0;
            this.pitch = 1;
            this.loop = false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.sound);
        }

        @Override
        public boolean equals(Object o) {
            return Objects.equals(this.sound, o);
        }
    }
}
