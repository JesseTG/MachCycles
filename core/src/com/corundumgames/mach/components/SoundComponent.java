package com.corundumgames.mach.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.corundumgames.Assets;

public class SoundComponent extends PooledComponent implements Serializable {
    private static AssetManager assets;

    public static void setAssetManager(AssetManager assets) {
        SoundComponent.assets = assets;
    }

    public ObjectMap<String, Sound> sounds;

    public SoundComponent() {
        this.sounds = new ObjectMap<>(4);
    }

    @Override
    protected void reset() {
        this.sounds.clear();
    }

    @Override
    public void write(Json json) {
        // Read-only
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        JsonValue sounds = jsonData.get("sounds");
        
        if (sounds != null && sounds.isObject()) {
            for (JsonValue s = sounds.get(0); s != null; s = s.next()) {
                String name = s.asString();
                if (!assets.isLoaded(name, Sound.class)) {
                    assets.load(name, Sound.class, Assets.SOUND_PARAMETER);
                    assets.finishLoading();
                }
                this.sounds.put(s.name(), assets.get(name, Sound.class));
            }
        }
    }

}
