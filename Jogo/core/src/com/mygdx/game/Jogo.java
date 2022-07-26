package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Jogo extends Game{
	public SpriteBatch batch;
	public BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale((float) 1.2);
		this.setScreen(new MyGdxGame(this));
	}
	
	@Override
	public void render () {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
	
}
