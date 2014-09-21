package com.corundumgames.mach.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.corundumgames.mach.components.BackgroundComponent;
import com.corundumgames.mach.components.TextureComponent;
import com.corundumgames.mach.components.TransformComponent;
import com.corundumgames.mach.managers.ResourceManagers.CameraManager;
import com.corundumgames.mach.managers.ResourceManagers.RenderingManager;

public class TextureRenderSystem extends IntervalEntityProcessingSystem {
    @Wire
    private ComponentMapper<TextureComponent> textureMapper;

    @Wire
    private ComponentMapper<TransformComponent> transformMapper;

    @Wire
    private CameraManager cameras;

    @Wire
    private RenderingManager rendering;

    // Global state

    private Vector2 temp2;
    private Vector2 temp2_1;

    public TextureRenderSystem(final float interval) {
        super(Aspect.getAspectForAll(TextureComponent.class, TransformComponent.class)
                .exclude(BackgroundComponent.class), interval);
        this.temp2 = new Vector2();
        this.temp2_1 = new Vector2();
    }

    @Override
    protected void begin() {
        this.rendering.batch.setProjectionMatrix(this.cameras.worldCamera.combined);
    }

    @Override
    protected void process(Entity e) {
        TextureComponent tc = textureMapper.get(e);

        if (tc.region != null && tc.visible) {
            TransformComponent txc = transformMapper.get(e);

            txc.transform.getScale(this.temp2);
            // The size of the game object
            txc.transform.getTranslation(this.temp2_1);
            // The position of the game object
            float tw = tc.region.getRegionWidth();
            float th = tc.region.getRegionHeight();
            float angle = MathUtils.radDeg
                    * MathUtils.atan2(txc.transform.val[Matrix3.M10], txc.transform.val[Matrix3.M00]);

            if (tc.region.rotate) {
                angle -= 90;
            }

            this.rendering.batch.draw(
                    tc.region,
                    this.temp2_1.x - tw / 2,
                    this.temp2_1.y - th / 2,
                    tw / 2,
                    th / 2,
                    tw,
                    th,
                    this.temp2.x,
                    this.temp2.y,
                    angle
                    );
        }

    }
}
