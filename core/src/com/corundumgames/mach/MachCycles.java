package com.corundumgames.mach;

import net.dermetfan.utils.libgdx.AnnotationAssetManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.JsonValue;
import com.corundumgames.Assets;
import com.corundumgames.JsonLoader;
import com.corundumgames.entities.Entities;
import com.corundumgames.mach.desktop.screens.Play;
import com.corundumgames.mach.screens.InGameScreen;

public class MachCycles extends Game {

	public static final int FPS = 60;
	public static final float SPF = 1.0f / FPS;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	public AnnotationAssetManager assets;
	// This is dirty, I should work around it

	public OrthographicCamera gameCamera;
	public OrthographicCamera guiCamera;
	public SpriteBatch batch;
	public ShaderProgram shader;

	@Override
	public void create() {
		this.assets = new AnnotationAssetManager();
		this.assets.setLoader(JsonValue.class, new JsonLoader(
				new InternalFileHandleResolver()));
		this.assets.load(Assets.class);

		this.shader = SpriteBatch.createDefaultShader();
		this.batch = new SpriteBatch(1000, this.shader);
		this.gameCamera = new OrthographicCamera();
		this.guiCamera = new OrthographicCamera();
		Entities.setAssetManager(this.assets);

		this.assets.finishLoading();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		this.setScreen(new InGameScreen(this));

		setScreen(new Play());
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.setView(gameCamera);
		renderer.setView(guiCamera);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		this.gameCamera.setToOrtho(false, width, height);
		this.guiCamera.setToOrtho(false, width, height);

		this.gameCamera.viewportWidth = width / 32f;
		this.gameCamera.viewportHeight = gameCamera.viewportWidth * height
				/ width;
		this.gameCamera.update();

		this.guiCamera.viewportWidth = width;
		this.guiCamera.viewportHeight = height;
		this.guiCamera.update();
	}

	public void show() {
		map = new TmxMapLoader().load("tronbggrid.tmx");

		renderer = new OrthogonalTiledMapRenderer(map);

		gameCamera = new OrthographicCamera();
		guiCamera = new OrthographicCamera();
	}

	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	/*
	 * @Override public void dispose() { dispose(); }
	 */
}
