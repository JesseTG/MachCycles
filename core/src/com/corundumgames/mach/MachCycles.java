package com.corundumgames.mach;

import net.dermetfan.utils.libgdx.AnnotationAssetManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.corundumgames.Assets;
import com.corundumgames.mach.screens.InGameScreen;

public class MachCycles extends Game {
    private AnnotationAssetManager assets;

    @Override
    public void create() {
        this.assets = new AnnotationAssetManager();
        this.assets.load(Assets.class);
        this.assets.finishLoading();
        this.setScreen(new InGameScreen());
    }
}
