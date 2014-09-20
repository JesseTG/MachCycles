package com.corundumgames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.UBJsonReader;

public class JsonLoader extends
        SynchronousAssetLoader<JsonValue, JsonLoader.JsonParameters> {

    public JsonLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public JsonValue load(AssetManager assetManager, String fileName, FileHandle file,
            JsonParameters parameter) {
        switch (parameter.format) {
            case JSON:
                return new JsonReader().parse(file);
            case UBJSON:
                return new UBJsonReader().parse(file);
            default:
                Gdx.app.log("JSON", "Unrecognized JSON flavor " + parameter.format + ", defaulting to stock JSON");
                return new JsonReader().parse(file);
        }
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file,
            JsonParameters parameter) {
        // TODO Auto-generated method stub
        return null;
    }

    public static enum Format {
        JSON,
        UBJSON
    }

    public static class JsonParameters extends AssetLoaderParameters<JsonValue> {
        Format format = Format.JSON;
    }

}
