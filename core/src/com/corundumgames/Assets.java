package com.corundumgames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.AssetLoaderParameters.LoadedCallback;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader.I18NBundleParameter;
import com.badlogic.gdx.assets.loaders.MusicLoader.MusicParameter;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.assets.loaders.SoundLoader.SoundParameter;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader.TextureAtlasParameter;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.corundumgames.JsonLoader.JsonParameters;

public final class Assets {
    private Assets() {}

    private static final String ATLAS_PATH = "gfx/gfx-0.atlas";

    public static final TextureAtlasParameter ATLAS_PARAMETER = new TextureAtlasParameter();
    public static final JsonParameters JSON_PARAMETER = new JsonParameters();
    public static final BitmapFontParameter FONT_PARAMETER = new BitmapFontParameter();
    public static final I18NBundleParameter I18N_PARAMETER = new I18NBundleParameter();
    public static final MusicParameter MUSIC_PARAMETER = new MusicParameter();
    public static final ParticleEffectParameter PARTICLE_EFFECT_PARAMETER = new ParticleEffectParameter();
    public static final SkinParameter SKIN_PARAMETER = new SkinParameter(ATLAS_PATH);
    public static final SoundParameter SOUND_PARAMETER = new SoundParameter();
    public static final TextureParameter TEXTURE_PARAMETER = new TextureParameter();

    public static final AssetDescriptor<TextureAtlas> ATLAS =
            new AssetDescriptor<TextureAtlas>(ATLAS_PATH, TextureAtlas.class, ATLAS_PARAMETER);

    public static final AssetDescriptor<I18NBundle> TEXT =
            new AssetDescriptor<I18NBundle>("", I18NBundle.class, I18N_PARAMETER);

    public static final LoadedCallback ON_LOADED = new LoadedCallback()
    {
        @Override
        public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
            Gdx.app.log("Assets", ClassReflection.getSimpleName(type) + " at " + fileName
                    + " loaded successfully");
        }
    };

    private static final LoadedCallback TEXTURE_LOADED = new LoadedCallback()
    {
        @Override
        public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
            ON_LOADED.finishedLoading(assetManager, fileName, type);
            Texture t = assetManager.get(fileName);
            Gdx.app.log("\t" + "Managed", Boolean.toString(t.isManaged()));
            Gdx.app.log("\t" + "Target", Integer.toString(t.glTarget));
            Gdx.app.log("\t" + "Size", t.getWidth() + " x " + t.getHeight() + " (" + t.getDepth() + "-bit)");
            Gdx.app.log("\t" + "Handle", Integer.toString(t.getTextureObjectHandle()));
            Gdx.app.log("\t" + "Min Filter", t.getMinFilter() + " (GL: " + t.getMinFilter().getGLEnum() + ")");
            Gdx.app.log("\t" + "Mag Filter", t.getMagFilter() + " (GL: " + t.getMagFilter().getGLEnum() + ")");
            Gdx.app.log("\t" + "U-Wrap", t.getUWrap() + " (GL: " + t.getUWrap().getGLEnum() + ")");
            Gdx.app.log("\t" + "V-Wrap", t.getVWrap() + " (GL: " + t.getVWrap().getGLEnum() + ")");
            Gdx.app.log("\t" + "Texture Data Type", t.getTextureData().getType().toString());
            Gdx.app.log("\t" + "Format", t.getTextureData().getFormat().toString());
        }
    };

    private static final LoadedCallback ATLAS_LOADED = new LoadedCallback()
    {
        @Override
        public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
            TextureAtlas ta = assetManager.get(fileName);
            for (Texture t : ta.getTextures()) {
                String name = assetManager.getAssetFileName(t);
                if (name != null) {
                    TEXTURE_LOADED.finishedLoading(assetManager, name, Texture.class);
                }
            }

            ON_LOADED.finishedLoading(assetManager, fileName, type);

            Gdx.app.log("\t" + "Number of regions", Integer.toString(ta.getRegions().size));
            Gdx.app.log("\t" + "Number of textures", Integer.toString(ta.getTextures().size));
        }
    };

    private static final LoadedCallback PARTICLE_LOADED = new LoadedCallback()
    {
        @Override
        public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
            ON_LOADED.finishedLoading(assetManager, fileName, type);
            ParticleEffect pe = assetManager.get(fileName);
            Gdx.app.log("\t" + "Number of emitters", Integer.toString(pe.getEmitters().size));
        }
    };

    private static final LoadedCallback I18N_LOADED = new LoadedCallback()
    {
        @Override
        public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
            ON_LOADED.finishedLoading(assetManager, fileName, type);
            I18NBundle i18n = assetManager.get(fileName);
            Gdx.app.log("\t" + "Locale", i18n.getLocale().toString());
        }
    };

    private static final LoadedCallback FONT_LOADED = new LoadedCallback()
    {
        @Override
        public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
            ON_LOADED.finishedLoading(assetManager, fileName, type);
            BitmapFont font = assetManager.get(fileName);
            Gdx.app.log("\t" + "Ascent", Float.toString(font.getAscent()));
            Gdx.app.log("\t" + "Cap height", Float.toString(font.getCapHeight()));
            Gdx.app.log("\t" + "Color", font.getColor().toString());
            Gdx.app.log("\t" + "Descent", Float.toString(font.getDescent()));
            Gdx.app.log("\t" + "Line height", Float.toString(font.getLineHeight()));
            Gdx.app.log("\t" + "Space width", Float.toString(font.getSpaceWidth()));
            Gdx.app.log("\t" + "X-height", Float.toString(font.getXHeight()));
        }
    };

    private static final LoadedCallback JSON_LOADED = new LoadedCallback()
    {
        @Override
        public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
            ON_LOADED.finishedLoading(assetManager, fileName, type);
            JsonValue json = assetManager.get(fileName);
            Gdx.app.log("\t" + "Type", json.type().toString());
            Gdx.app.log("\t" + "Children", Integer.toString(json.size));
        }
    };

    static {
        ATLAS_PARAMETER.loadedCallback = ATLAS_LOADED;
        JSON_PARAMETER.loadedCallback = JSON_LOADED;
        FONT_PARAMETER.genMipMaps = true;
        FONT_PARAMETER.loadedCallback = FONT_LOADED;
        FONT_PARAMETER.atlasName = ATLAS_PATH;
        I18N_PARAMETER.loadedCallback = I18N_LOADED;
        MUSIC_PARAMETER.loadedCallback = ON_LOADED;
        PARTICLE_EFFECT_PARAMETER.atlasFile = ATLAS_PATH;
        PARTICLE_EFFECT_PARAMETER.loadedCallback = PARTICLE_LOADED;
        SKIN_PARAMETER.loadedCallback = ON_LOADED;
        SOUND_PARAMETER.loadedCallback = ON_LOADED;
        TEXTURE_PARAMETER.loadedCallback = TEXTURE_LOADED;
        TEXTURE_PARAMETER.genMipMaps = true;
    }
}
