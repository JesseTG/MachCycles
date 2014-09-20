package com.corundumgames.mach;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.corundumgames.mach.screens.InGameScreen;

public class MachCycles extends Game {
    @Override
    public void create() {
        this.setScreen(new InGameScreen());
    }
}
