package com.corundumgames.mach.screens;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InGameScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture img;

    private World world;

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        this.world = new World();
        
        this.world.initialize();
    }

    @Override
    public void render(float delta) {
        this.world.setDelta(delta);
        this.world.process();
    }

    @Override
    public void hide() {
        this.world.dispose();
    }
}
