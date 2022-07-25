package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MyGdxGame implements Screen {
	final Jogo game;
	
	OrthographicCamera camera;
	
	public MyGdxGame(final Jogo passed_game) {
		game = passed_game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		game.font.draw(game.batch, "Seu objetivo neste jogo é resgatar a princesa que foi levada a torre do vilão.", 90, 250);
		game.font.draw(game.batch, "Voce precisará destruir a barreira, atirando flechas com a tecla SETA PARA BAIXO,  para chegar até a torre.", 90, 200);
		game.font.draw(game.batch, "Desvie das flechas de seu inimigo pulando com a tecla SETA PARA CIMA.", 90, 150);	 
		game.font.draw(game.batch, "Pressione alguma tecla para começar!", 90, 100);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
}
