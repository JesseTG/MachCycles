package com.corundumgames.mach.desktop.screens;

import com.badlogic.gdx.Game;
import com.corundumgames.mach.Play;

public class SurfaceMap extends Game {

	@Override
	public void create() {
		// TODO Auto-generated method stub
		setScreen(new Play());
	}
	
	@Override 
	public void dispose(){
		super.dispose();
	}
	
	@Override 
	public void render(){
		super.render();
	}
	
	@Override 
	public void resize(int width, int height){
		super.resize(width, height);
	}
	
	@Override 
	public void pause(){
		super.pause();
	}

	@Override 
	public void resume(){
		super.resume();
	}
	
}
