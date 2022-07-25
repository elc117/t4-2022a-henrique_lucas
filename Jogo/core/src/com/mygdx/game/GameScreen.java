package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
	final Jogo game;
		
		Texture cenario;
		Texture personagemImg;
		Texture chefeImg;
		Texture barreiraImg;
		Music gameMusic;
		OrthographicCamera camera;
		SpriteBatch batch;
		long jumpTime;
		int barreiraCount;
		int vida;
		Rectangle personagem;
		Rectangle chefe;
		Rectangle barreira;
		boolean jumpState;
		boolean ascendente;
	
	public GameScreen(final Jogo passed_game) {
		game = passed_game;
		barreiraCount = 100;
		vida = 3;
		jumpState = false;
		ascendente = true;

		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));
		
		gameMusic.setLooping(true);
		gameMusic.play();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

		
		batch = new SpriteBatch();
		cenario = new Texture("cenario.png");
		personagemImg = new Texture("personagem.png");
		personagem = new Rectangle();
		personagem.x = 32;
		personagem.y = 54;
		chefeImg = new Texture("chefe.png");
		chefe = new Rectangle();
		chefe.x = 680;
		chefe.y = 260;
		barreiraImg = new Texture("barreira.png");
		barreira = new Rectangle();
		barreira.x = 556;
		barreira.y = 64;
	}
	


	
	@Override
	public void render(float delta) {
		camera.update();	
		game.batch.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		ScreenUtils.clear(1, 0, 0, 1);
		game.batch.begin();
		
		game.batch.draw(cenario, 0, 0);
		game.batch.draw(personagemImg, personagem.x, personagem.y);
		game.batch.draw(chefeImg, chefe.x, chefe.y);
		game.batch.draw(barreiraImg, barreira.x, barreira.y);
		
		game.font.draw(game.batch, "Barreira: " + barreiraCount, 0, 550);
		game.font.draw(game.batch, "Vida: " + vida, 0, 500);
		
		
		game.batch.end();
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) 
			personagem.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
			personagem.x += 200 * Gdx.graphics.getDeltaTime();
		if (personagem.x < 0) 
			personagem.x = 0;
		if (personagem.x > 500) 
			personagem.x = 500;
		if (personagem.y < 54) 
			personagem.y = 54;
		if (Gdx.input.isKeyPressed(Keys.UP)) 
			jumpState = true;

		if(jumpState == true) {
			if (TimeUtils.nanoTime() - jumpTime > 10000000) 
				jump();
		}

	}
private void jump() {
		if(personagem.y < 108 && ascendente == true)
			personagem.y += 200 * Gdx.graphics.getDeltaTime();
		if(personagem.y >= 108 || ascendente == false) {
			ascendente = false;
			personagem.y -= 200 * Gdx.graphics.getDeltaTime();
		}
		if(personagem.y <= 54) {
			jumpState = false;
			ascendente = true;
		}
		jumpTime = TimeUtils.nanoTime();
}
	
	@Override
	public void dispose() {
		gameMusic.dispose();
		cenario.dispose();
		batch.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		gameMusic.play();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
