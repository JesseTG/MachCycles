package com.corundumgames.mach.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.corundumgames.Assets;
import com.corundumgames.mach.components.TooltipComponent;
import com.corundumgames.mach.components.TransformComponent;
import com.corundumgames.mach.managers.EventManager;
import com.corundumgames.mach.managers.ResourceManagers.*;

public class TooltipRenderSystem extends IntervalEntityProcessingSystem {
    private static final Aspect ASPECT = Aspect.getAspectForAll(
            TooltipComponent.class,
            TransformComponent.class
            );

    @Wire
    private ComponentMapper<TransformComponent> transformMapper;

    @Wire
    private ComponentMapper<TooltipComponent> tooltipMapper;

    @Wire
    private EventManager events;

    @Wire
    private AssetsManager assets;

    @Wire
    private RenderingManager rendering;

    @Wire
    private CameraManager cameras;

    private BitmapFont font;

    private Vector2 temp;
    private Matrix4 temp4;

    public TooltipRenderSystem(float delta) {
        super(Aspect.getAspectForAll(
                TooltipComponent.class,
                TransformComponent.class
                ), delta);
        this.temp = new Vector2();
        this.temp4 = new Matrix4();
    }

    @Override
    protected void initialize() {
        //this.font = assets.assets.get(Assets.TOOLTIP_FONT);
        this.font.setUseIntegerPositions(false);
    }

    @Override
    protected void begin() {
        this.temp4.set(this.cameras.worldCamera.combined);
        this.rendering.batch.setProjectionMatrix(this.temp4);
    }

    @Override
    protected void process(Entity e) {
        TooltipComponent ttc = tooltipMapper.get(e);
        TransformComponent txc = transformMapper.get(e);
        txc.transform.getTranslation(this.temp);
        if (ttc.visible && !ttc.text.isEmpty()) {
            this.font.drawMultiLine(this.rendering.batch, ttc.text, this.temp.x, this.temp.y, 1, ttc.align);
        }
    }
}
