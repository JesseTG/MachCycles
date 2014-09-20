package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.badlogic.gdx.utils.ObjectMap;
import com.corundumgames.Assets;

public class AnimationComponent extends PooledComponent implements Serializable {
    private static final Array<TextureRegion> temp = new Array<>();
    private static AssetManager assets;

    public static void setAssetManager(AssetManager manager) {
        assets = manager;
        temp.clear();
    }

    public ObjectMap<String, Animation> animations;
    public Animation current;
    public float time;

    public AnimationComponent() {
        this.animations = new ObjectMap<>(4);
    }

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        String active = jsonData.getString("active", null);
        JsonValue animations = jsonData.get("animations");
        if (animations == null || animations.type() != ValueType.object) {
            throw new IllegalArgumentException("Expected an object for animations, got "
                    + ((animations == null) ? "null" : animations.type()));
        }
        else {
            TextureAtlas atlas = assets.get(Assets.ATLAS);
            for (JsonValue animation = animations.get(0); animation != null; animation = animation.next) {
                float frameDuration = animation.getFloat("frameDuration", .5f);
                String playMode = animation.getString("playMode", "normal").toUpperCase();
                temp.clear();
                JsonValue frames = animation.get("frames");
                if (frames != null && frames.isArray()) {
                    String[] regions = frames.asStringArray();
                    for (String r : regions) {
                        temp.add(atlas.findRegion(r));
                    }
                }

                Animation.PlayMode mode = PlayMode.valueOf(playMode);
                this.animations.put(animation.name(), new Animation(frameDuration, temp, mode));
            }

            this.current = this.animations.get(active);
        }
    }

    @Override
    protected void reset() {
        this.current = null;
        this.animations.clear();
        this.time = 0;
    }

}
