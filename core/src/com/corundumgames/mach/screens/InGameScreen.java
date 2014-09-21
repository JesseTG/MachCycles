package com.corundumgames.mach.screens;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.systems.IntervalEntitySystem;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.corundumgames.entities.Entities;
import com.corundumgames.mach.MachCycles;
import com.corundumgames.mach.components.*;
import com.corundumgames.mach.managers.*;
import com.corundumgames.mach.managers.ResourceManagers.*;
import com.corundumgames.mach.systems.*;

public class InGameScreen extends ScreenAdapter {

    private AnimationSystem animation;
    private BackgroundRenderSystem background;
    private ParticleSystem particles;
    private TextureRenderSystem textures;
    private SoundSystem sounds;
    private TooltipRenderSystem tooltips;
    private CollisionSystem collision;
    private MotionSystem motion;
    private PlayerInputSystem playerInput;
    private DebugPhysicsRenderingSystem debugPhysicsRender;
    private CycleControlSystem mach;

    private AssetsManager assets;
    private CameraManager cameras;
    private RenderingManager rendering;

    private World world;
    private MachCycles cycles;
    private InputMultiplexer input;
    
    public InGameScreen(MachCycles cycles) {
        this.cycles = cycles;
        this.input = new InputMultiplexer();
    }

    @Override
    public void show() {
        this.world = new World();

        this.initStaticFields(true);
        this.initManagers();
        this.initSystems();
        
        this.initInputProcessors();
        this.world.initialize();

        Gdx.input.setInputProcessor(this.input);
        Entity e = Entities.createEntityFromFile(Entities.getEntityPath("cycle"));
    }

    private void initStaticFields(boolean on) {
        Entities.setEntityWorld(on ? this.world : null);
        TextureComponent.setAssetManager(on ? this.cycles.assets : null);
    }

    private void initManagers() {
        this.assets = this.world.setManager(new AssetsManager(cycles.assets));
        this.cameras = this.world.setManager(new CameraManager(cycles.gameCamera, cycles.guiCamera));
        this.rendering = this.world.setManager(new RenderingManager(cycles.batch, cycles.shader));
    }
    
    private void initInputProcessors() {
        this.input.addProcessor(this.playerInput);
    }

    private void initSystems() {
        this.playerInput = this.world.setSystem(new PlayerInputSystem());
        this.mach = this.world.setSystem(new CycleControlSystem());
        this.motion = this.world.setSystem(new MotionSystem(MachCycles.SPF));
        this.collision = this.world.setSystem(new CollisionSystem(MachCycles.SPF));
        this.animation = this.world.setSystem(new AnimationSystem(MachCycles.SPF));
        this.sounds = this.world.setSystem(new SoundSystem());

        { // Begin rendering
            this.world.setSystem(new IntervalEntitySystem(Aspect.getEmpty(), MachCycles.SPF)
            {

                @Override
                protected void processEntities(ImmutableBag<Entity> entities) {
                    rendering.batch.begin();
                }
            });
            this.background = this.world.setSystem(new BackgroundRenderSystem(MachCycles.SPF));
            this.textures = this.world.setSystem(new TextureRenderSystem(MachCycles.SPF));
            this.particles = this.world.setSystem(new ParticleSystem(MachCycles.SPF));
            this.world.setSystem(new IntervalEntitySystem(Aspect.getEmpty(), MachCycles.SPF)
            {

                @Override
                protected void processEntities(ImmutableBag<Entity> entities) {
                    rendering.batch.end();
                }
            });
            this.debugPhysicsRender = this.world.setSystem(new DebugPhysicsRenderingSystem(MachCycles.SPF));
        } // End rendering
    }

    @Override
    public void render(float delta) {
        this.world.setDelta(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.world.process();
    }

    @Override
    public void hide() {
        this.input.clear();
        this.world.dispose();
    }
}
