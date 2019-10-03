package com.arpg.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ArpgGame extends Game {
	private SpriteBatch batch;
	private SpriteBatch hudBatch;

	// Домашнее задание:
	// Вероятность появления скелета - 90%, жнеца - 10% (можно добавить зависимость от уроня героя)
	// Зависимость уровня появляющихся монстров от уровня героя
	// * Добавить зависимость урона от параметров (статов) героя/монстра
	// * Добавить получение героем опыта за уничтожение монстров

	@Override
	public void create() {
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		ScreenManager.getInstance().init(this, batch, hudBatch);
		ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float dt = Gdx.graphics.getDeltaTime();
		getScreen().render(dt);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
