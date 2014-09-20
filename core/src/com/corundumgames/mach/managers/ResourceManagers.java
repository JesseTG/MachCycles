package com.corundumgames.mach.managers;

import com.artemis.Manager;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.async.AsyncExecutor;

public final class ResourceManagers {
    public static class RenderingManager extends Manager {
        public final SpriteBatch batch;
        public final ShaderProgram shader;

        public RenderingManager(SpriteBatch batch, ShaderProgram shader) {
            this.batch = batch;
            this.shader = shader;
        }
    }

    public static class CameraManager extends Manager {
        public final OrthographicCamera worldCamera;
        public final OrthographicCamera guiCamera;

        public CameraManager(OrthographicCamera worldCamera, OrthographicCamera guiCamera) {
            this.worldCamera = worldCamera;
            this.guiCamera = guiCamera;
        }
    }

    public static class StageManager extends Manager {
        public final Stage stage;

        public StageManager(Stage stage) {
            this.stage = stage;
        }
    }

    public static class InputManager extends Manager {
        public final InputMultiplexer input;

        public InputManager(InputMultiplexer input) {
            this.input = input;
        }
    }

    public static class AssetsManager extends Manager {
        public final AssetManager assets;

        public AssetsManager(AssetManager assets) {
            this.assets = assets;
        }
    }

    public static class AsyncManager extends Manager {
        public final AsyncExecutor async;

        public AsyncManager(AsyncExecutor async) {
            this.async = async;
        }
    }

    private ResourceManagers() {}
}
