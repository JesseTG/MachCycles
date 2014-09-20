package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.corundumgames.Assets;

public class TooltipComponent extends PooledComponent implements Serializable {
    private static AssetManager assets;
    private static I18NBundle bundle;

    public static void setAssetManager(AssetManager assets) {
        TooltipComponent.assets = assets;
        bundle = (assets == null) ? null : assets.get(Assets.TEXT);
    }

    public String text;
    public boolean visible;
    public HAlignment align;

    public TooltipComponent() {
        this.text = "";
    }

    @Override
    protected void reset() {
        this.text = "";
        this.visible = true;
        this.align = HAlignment.LEFT;
    }

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        this.text = jsonData.getString("text", null);
        this.visible = jsonData.getBoolean("visible", true);
        this.align = HAlignment.valueOf(jsonData.getString("align", "left").toUpperCase());

        if (this.text == null) {
            String key = jsonData.getString("key", null);

            if (key != null) {
                this.text = bundle.get(key);
            }
        }
    }
}
