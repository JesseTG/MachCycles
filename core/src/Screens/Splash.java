package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.Game;

public class Splash implements Screen{
	
	private Texture texture = new Texture(Gdx.files.internal("img/badlogic.jpg"));
    private Image splashImage = new Image(texture);
    private Stage stage = new Stage();

	@Override
	public void render(float delta) {
	    Gdx.gl.glClearColor(0,0,0,1); 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(); 
        stage.draw(); 
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		stage.addActor(splashImage);
		 splashImage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(1.0f),Actions.delay(4),Actions.run(new Runnable() {
	            @Override
	            public void run() {
	                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
	            }
	        })));
		
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		texture.dispose();
        stage.dispose();
	}

}
