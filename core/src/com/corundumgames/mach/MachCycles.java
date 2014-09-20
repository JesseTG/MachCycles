package com.corundumgames.mach;

import net.dermetfan.utils.libgdx.AnnotationAssetManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.JsonValue;
import com.corundumgames.Assets;
import com.corundumgames.JsonLoader;
import com.corundumgames.entities.Entities;
import com.corundumgames.mach.screens.InGameScreen;

public class MachCycles extends Game {
    public static final int FPS = 60;
    public static final float SPF = 1.0f / FPS;

    public AnnotationAssetManager assets;
    // This is dirty, I should work around it

    public OrthographicCamera gameCamera;
    public OrthographicCamera guiCamera;
    public SpriteBatch batch;
    public ShaderProgram shader;

    @Override
    public void create() {
        this.assets = new AnnotationAssetManager();
        this.assets.setLoader(JsonValue.class, new JsonLoader(new InternalFileHandleResolver()));
        this.assets.load(Assets.class);

        this.shader = SpriteBatch.createDefaultShader();
        this.batch = new SpriteBatch(1000, this.shader);
        this.gameCamera = new OrthographicCamera();
        this.guiCamera = new OrthographicCamera();
        Entities.setAssetManager(this.assets);
        

        this.assets.finishLoading();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        this.setScreen(new InGameScreen(this));
    }
    
    @Override
    public void resize(int width, int height) {
        this.gameCamera.setToOrtho(false, width, height);
        this.guiCamera.setToOrtho(false, width, height);
    }
}
