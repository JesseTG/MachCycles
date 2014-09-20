package com.corundumgames.mach.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.ObjectMap;
import com.corundumgames.mach.components.BackgroundComponent;
import com.corundumgames.mach.components.TextureComponent;
import com.corundumgames.mach.components.TransformComponent;
import com.corundumgames.mach.managers.ResourceManagers.CameraManager;
import com.corundumgames.mach.managers.ResourceManagers.RenderingManager;

public class BackgroundRenderSystem extends IntervalEntityProcessingSystem {
    private static final Aspect ASPECT = Aspect.getAspectForAll(
            BackgroundComponent.class,
            TextureComponent.class,
            TransformComponent.class);

    @Wire
    private ComponentMapper<BackgroundComponent> backgroundMapper;

    @Wire
    private ComponentMapper<TextureComponent> textureMapper;

    @Wire
    private ComponentMapper<TransformComponent> transformMapper;

    @Wire
    private CameraManager cameras;

    @Wire
    private RenderingManager rendering;

    private ObjectMap<Entity, TiledDrawable> backgrounds;

    public BackgroundRenderSystem(float interval) {
        super(Aspect.getAspectForAll(
                BackgroundComponent.class,
                TextureComponent.class,
                TransformComponent.class)
                , interval);
        this.backgrounds = new ObjectMap<>(4);
    }

    @Override
    protected void inserted(Entity e) {
        BackgroundComponent bc = backgroundMapper.get(e);
        TextureComponent tc = textureMapper.get(e);
        TransformComponent txc = transformMapper.get(e);
        this.backgrounds.put(e, new TiledDrawable(tc.region));
    }

    @Override
    protected void removed(Entity e) {
        this.backgrounds.remove(e);
    }

    @Override
    protected void process(Entity e) {
        OrthographicCamera camera = this.cameras.worldCamera;
        BackgroundComponent bc = backgroundMapper.get(e);

        float width = camera.viewportWidth;
        float height = camera.viewportHeight;
        float x = (-camera.position.x * bc.z - width) / 2;
        float y = (-camera.position.y * bc.z - height * 1.5f) / 2;
        this.backgrounds.get(e)
                .draw(
                        this.rendering.batch,
                        x,
                        y,
                        width * 2,
                        (int)(height * 2.5f)
                );
    }
}
