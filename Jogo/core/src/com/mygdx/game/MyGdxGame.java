package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame implements Screen {
	final Jogo game;
	Texture inicio;
	
	OrthographicCamera camera;
	
	public MyGdxGame(final Jogo passed_game) {
		game = passed_game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		inicio = new Texture("tela.png"); //MUDAR TEXTURA
	}
	
	@Override
	public void render(float delta) {
		
		camera.update();
		ScreenUtils.clear(1, 0, 0, 1);
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(inicio, 0, 0);
		game.font.draw(game.batch, "Seu objetivo neste jogo é resgatar a princesa que foi levada", 180, 500);
		game.font.draw(game.batch, "à torre do vilão.", 180, 450);
		game.font.draw(game.batch, "Você precisará destruir a barreira, atirando flechas ", 180, 400);
		game.font.draw(game.batch, "com a tecla SETA PARA BAIXO, para chegar até a torre.", 180, 350);
		game.font.draw(game.batch, "Desvie dos ataques de seu inimigo pulando", 180, 300);	
		game.font.draw(game.batch, "com a tecla SETA PARA CIMA.", 180, 250);
		game.font.draw(game.batch, "Ou utilizando as SETAS PARA ESQUERDA ou DIREITA.", 180, 200);
		game.font.draw(game.batch, "Click com o mouse para começar!", 220, 150);
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
		inicio.dispose();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
}
