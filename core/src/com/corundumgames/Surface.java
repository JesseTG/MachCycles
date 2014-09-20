package com.corundumgames;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.corundumgames.mach.desktop.screens.Play;

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
