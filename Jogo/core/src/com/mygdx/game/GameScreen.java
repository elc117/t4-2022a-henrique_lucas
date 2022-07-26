package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
	final Jogo game;
		
		Texture cenario;
		Texture personagemImg;
		Texture personagemLImg;
		Texture personagemRImg;
		Texture flechaImg;
		Texture flechaLImg;
		Texture flechaRImg;
		Texture chefeImg;
		Texture barreiraImg;
		Sound fogoSound;
		Music gameMusic;
		OrthographicCamera camera;
		SpriteBatch batch;
		long jumpTime;
		long flechaTime;
		int barreiraCount;
		int vida;
		Rectangle personagem;
		Rectangle chefe;
		Rectangle barreira;
		boolean jumpState;
		boolean ascendente;
		boolean fireState;
		Array<Rectangle> fireballs;
		Array<Rectangle> flechas;
		Texture fireballImg;
		long fireTime, fireTime2;
		float xi, yi, xf, yf;
		boolean ladoState;
		boolean flechaState;
		boolean flechaOk;
		boolean flechaPos;
	
	public GameScreen(final Jogo passed_game) {
		game = passed_game;
		barreiraCount = 100;
		vida = 3;
		jumpState = false;
		ascendente = true;
		fireState = true;
		flechaState = false;
		ladoState = true; //true lado direito false lado esquerdo
		flechaOk=true;

		
		
		fireballImg = new Texture(Gdx.files.internal("fireball_16.png"));
		flechaImg = new Texture(Gdx.files.internal("flecha.png"));
		flechaRImg = new Texture(Gdx.files.internal("flecha.png"));
		flechaLImg = new Texture(Gdx.files.internal("flechaL.png"));
		fogoSound = Gdx.audio.newSound(Gdx.files.internal("gameMusic.mp3")); //MUDAR arquivo de audio
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
		personagem.width = 64;
		personagem.height = 88;
		personagemLImg = new Texture("personagemL.png");
		personagemRImg = new Texture("personagem.png");
		chefeImg = new Texture("chefe.png");
		chefe = new Rectangle();
		chefe.x = 680;
		chefe.y = 260;
		barreiraImg = new Texture("barreira.png");
		barreira = new Rectangle();
		barreira.x = 556;
		barreira.y = 64;
		barreira.width = 45;
		barreira.height = 44; 
			
		fireballs = new Array<Rectangle>();
		flechas = new Array<Rectangle>();
		fire();
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
		
		game.font.draw(game.batch, "Barreira: " + barreiraCount, 300, 550);
		game.font.draw(game.batch, "Vida: " + vida, 300, 500);
		
		for (Rectangle fireball: fireballs) {
			game.batch.draw(fireballImg, fireball.x, fireball.y);
		}
		
		for (Rectangle flecha: flechas) {
			game.batch.draw(flechaImg, flecha.x, flecha.y);
		}
		
		
		
		game.batch.end();
		
		if(vida == 0) {
			game.setScreen(new GameOver(game));
		}
		
		if(barreiraCount == 0) {
			game.setScreen(new EndGame(game));
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			ladoState = false;
			personagemImg = personagemLImg;
			personagem.x -= 200 * Gdx.graphics.getDeltaTime();
		} 	
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			ladoState = true;
			personagemImg = personagemRImg;
			personagem.x += 200 * Gdx.graphics.getDeltaTime();
		}
			
		if (personagem.x < 0) 
			personagem.x = 0;
		if (personagem.x > 500) 
			personagem.x = 500;
		if (personagem.y < 54) 
			personagem.y = 54;
		if (Gdx.input.isKeyPressed(Keys.UP)) 
			jumpState = true;
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			flechaState = true;
		}
		
		if(flechaState == true) {
			flechaState = false;
			if (TimeUtils.nanoTime() - flechaTime > 10000000)
				atirar();
		}
			
		
		if(jumpState == true) {
			if (TimeUtils.nanoTime() - jumpTime > 10000000) 
				jump();
		}
		
		if(fireState == true)
			if (TimeUtils.nanoTime() - fireTime > 200000000) 
				fire();
				
		Iterator<Rectangle> iter = fireballs.iterator();
		while(iter.hasNext()) {
			Rectangle fireball = iter.next();
			fireball.y -= (yf-yi)/(xf-xi)*200 * Gdx.graphics.getDeltaTime();
			fireball.x -= 200 * Gdx.graphics.getDeltaTime();
			if(fireball.y < 54) {
				iter.remove();
				fogoSound.play(); //MUDAR PARA SOM do fogo
				fireState = true;
			}
			if(fireball.overlaps(personagem)) {
				vida -= 1;
				iter.remove();
				fogoSound.play(); //MUDAR PARA SOM do fogo
				fireState = true;
			}
		}
		
		
		Iterator<Rectangle> iter1 = flechas.iterator();
		while(iter1.hasNext()) {
			Rectangle flecha = iter1.next();
			
			
			if(flechaPos == true){
				fogoSound.play(); //MUDAR PARA SOM da FLecha sendo lancada
				flechaImg = flechaRImg;
				flecha.x += 400 * Gdx.graphics.getDeltaTime();
			}else {
				fogoSound.play(); //MUDAR PARA SOM da FLecha sendo lancada
				flechaImg = flechaLImg;
				flecha.x -= 400 * Gdx.graphics.getDeltaTime();
			}
			if(flecha.overlaps(barreira)) {
				barreiraCount -= 5;
				iter1.remove();
				flechaOk = true;
				fogoSound.play(); //MUDAR PARA SOM de FLecha acertanodo alvo
			}
			if(flecha.x >= 800) {
				iter1.remove();
				fogoSound.play(); //MUDAR PARA SOM de FLecha acertanodo alvo
				flechaOk = true;
			}
			if(flecha.x <= 0) {
				iter1.remove();
				fogoSound.play(); //MUDAR PARA SOM de FLecha acertanodo alvo
				flechaOk = true;
			}
			
				
		}
		
	}

private void atirar() {
	if(flechaOk == true) {
		flechaOk = false;
		flechaState = false;
		Rectangle flecha = new  Rectangle();
		flecha.x = personagem.x;
		flecha.y = personagem.y + 40;
		flechas.add(flecha);
		flechaPos = ladoState;
		flechaTime = TimeUtils.nanoTime();
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

private void fire() {
		fireState = false;
		Rectangle fireball = new Rectangle();
		fireball.x = chefe.x;
		fireball.y = chefe.y;
		fireballs.add(fireball);
		xi = chefe.x;
		yi = chefe.y;
		xf = personagem.x;
		yf = personagem.y;
		fireTime = TimeUtils.nanoTime();
}
	
	
	@Override
	public void dispose() {
		fogoSound.dispose();
		fireballImg.dispose();
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
