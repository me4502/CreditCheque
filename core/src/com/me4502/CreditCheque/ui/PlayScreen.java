package com.me4502.CreditCheque.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me4502.CreditCheque.CreditCheque;

public class PlayScreen extends Screen {

	@Override
	public void init() {

		CreditCheque.instance.game.setupPositions();
	}

	@Override
	public void render(SpriteBatch batch) {
		CreditCheque.instance.drawCentredText(CreditCheque.instance.smallFont, batch, "HIGH SCORE", getCentreX(), Gdx.graphics.getHeight() - 20);

		CreditCheque.instance.drawCentredText(CreditCheque.instance.smallFont, batch, String.valueOf(CreditCheque.instance.highScore), getCentreX(), Gdx.graphics.getHeight() - 32);

		CreditCheque.instance.drawCentredText(CreditCheque.instance.smallFont, batch, "PLAYER 1", getCentreX()/2-20, Gdx.graphics.getHeight() - 20);
		CreditCheque.instance.drawCentredText(CreditCheque.instance.smallFont, batch, String.valueOf(CreditCheque.instance.game.getPlayerScore(0)), getCentreX()/2-20, Gdx.graphics.getHeight() - 32);

		if(CreditCheque.instance.game.playerCount == 2) {
			CreditCheque.instance.drawCentredText(CreditCheque.instance.smallFont, batch, "PLAYER 2", getCentreX()+getCentreX()/2+20, Gdx.graphics.getHeight() - 20);
			CreditCheque.instance.drawCentredText(CreditCheque.instance.smallFont, batch, String.valueOf(CreditCheque.instance.game.getPlayerScore(1)), getCentreX()+getCentreX()/2+20, Gdx.graphics.getHeight() - 32);
		}

		CreditCheque.instance.game.render(batch);
	}

	@Override
	public void update() {

		if(CreditCheque.instance.game.getCurrentScore() > CreditCheque.instance.highScore) {
			CreditCheque.instance.highScore = CreditCheque.instance.game.getCurrentScore();
			CreditCheque.instance.game.isHighScore = true;
		}

		CreditCheque.instance.game.update();
	}

	@Override
	public void onKeyPress(int key) {
		if(key == Keys.ESCAPE) {
			CreditCheque.instance.screen = new MainMenuScreen();
			if(CreditCheque.instance.game.isHighScore)
				//	CreditCheque.instance.save();
				System.gc();
		} else
			CreditCheque.instance.game.onKeyPress(key);
	}

	@Override
	public void onMouseClick(int x, int y, int button) {
		CreditCheque.instance.game.onMousePress(x, y, button);
	}

}