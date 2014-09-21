package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.corundumgames.Assets;
import com.corundumgames.mach.systems.TextureRenderSystem;

/**
 * @see TextureRenderSystem
 * @author jesse
 *
 */
public class TextureComponent extends PooledComponent implements Serializable {
    public AtlasRegion region;
    public boolean visible;
    private static AssetManager assets;

    public static void setAssetManager(final AssetManager assets) {
        TextureComponent.assets = assets;
    }

    public TextureComponent() {
        this.reset();
    }

    @Override
    public void reset() {
        this.region = null;
        this.visible = true;
    }

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        JsonValue region = jsonData.get("region");
        if (region == null) {
            String path = jsonData.getString("texture", null);
            if (!assets.isLoaded(path)) {
                assets.load(path, Texture.class);
                assets.finishLoading();
            }
            Texture texture = assets.get(path);

            this.region = new AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
        }
        else if (region.isString()) {
            TextureAtlas atlas = assets.get(Assets.ATLAS);
            AtlasRegion ar = atlas.findRegion(region.asString());
            this.region = ar;
        }
        else if (region.isObject()) {
            // Set the texture region by rectangle
            String path = jsonData.getString("texture", null);
            if (!assets.isLoaded(path)) {
                assets.load(path, Texture.class);
                assets.finishLoading();
            }
            Texture texture = assets.get(path);
            this.region = new AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
            int x = region.getInt("x", 0);
            int y = region.getInt("y", 0);
            int width = region.getInt("width", 0);
            int height = region.getInt("height", 0);

            this.region.setRegion(x, y, width, height);
        }
    }
}
