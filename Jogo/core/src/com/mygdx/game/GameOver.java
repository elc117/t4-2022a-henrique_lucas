package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class  GameOver implements Screen {
	final Jogo game;
	Texture fim;
	
	OrthographicCamera camera;


	public GameOver(final Jogo passed_game) {
		game = passed_game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		fim = new Texture("tela.png"); //MUDAR TEXTURA
	}
	
	@Override
	public void render(float delta) {
		camera.update();
		ScreenUtils.clear(1, 0, 0, 1);
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(fim, 0, 0);
		game.font.draw(game.batch, "Você não conseguiu resgatar a princesa", 200, 450); 
		game.font.draw(game.batch, "Click com o mouse para tentar novamente!", 200, 400);
		game.batch.end();
		
		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void dispose() {
		fim.dispose();
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

}