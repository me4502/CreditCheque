package com.me4502.CreditCheque.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Screen {

	public Screen() {
		init();
	}

	public abstract void init();

	public abstract void render(SpriteBatch batch);

	public void update() {
	}

	public abstract void onKeyPress(int key);

	public abstract void onMouseClick(int x, int y, int button);

	public int getCentreX() {

		return Gdx.graphics.getWidth() / 2;
	}

	public int getCentreY() {

		return Gdx.graphics.getHeight() / 2;
	}
}