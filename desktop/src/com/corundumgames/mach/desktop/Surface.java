package com.corundumgames.mach.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public class Surface extends Game{

	@Override
	public void create() {
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
	public void resume(){
		super.resume();
	}
	

}
