package com.me4502.CreditCheque.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me4502.CreditCheque.CreditCheque;

public class GameOverScreen extends Screen {

	@Override
	public void init() {

	}

	int timeout = 60;

	int timer = 0;
	boolean showMessage = false;

	@Override
	public void render(SpriteBatch batch) {

		timer++;

		CreditCheque.instance.mainFont.setColor(Color.WHITE);
		CreditCheque.instance.smallFont.setColor(Color.WHITE);

		CreditCheque.instance.drawCentredText(CreditCheque.instance.mainFont, batch, "HIGH SCORE", getCentreX(), getCentreY() + 100);
		CreditCheque.instance.drawCentredText(CreditCheque.instance.smallFont, batch, String.valueOf(CreditCheque.instance.highScore), getCentreX(), getCentreY() + 80);

		CreditCheque.instance.drawCentredText(CreditCheque.instance.mainFont, batch, "PLAYER 1 SCORE", getCentreX(), getCentreY() + 40);
		CreditCheque.instance.drawCentredText(CreditCheque.instance.smallFont, batch, String.valueOf(CreditCheque.instance.game.getPlayerScore(0)), getCentreX(), getCentreY() + 20);

		if(CreditCheque.instance.game.playerCount == 2) {

			CreditCheque.instance.drawCentredText(CreditCheque.instance.mainFont, batch, "PLAYER 2 SCORE", getCentreX(), getCentreY() - 20);
			CreditCheque.instance.drawCentredText(CreditCheque.instance.smallFont, batch, String.valueOf(CreditCheque.instance.game.getPlayerScore(1)), getCentreX(), getCentreY() - 40);
		}

		if(CreditCheque.instance.game.isHighScore)
			CreditCheque.instance.drawCentredText(CreditCheque.instance.mainFont, batch, "CONGRATULATIONS! YOU GOT A HIGHSCORE!", getCentreX(), getCentreY() - 70);

		if(timer % 50 == 0)
			showMessage = !showMessage;

		if(showMessage)
			CreditCheque.instance.drawCentredText(CreditCheque.instance.mainFont, batch, "Press Any Key To Continue!", getCentreX(), getCentreY() - 150);
	}

	@Override
	public void update() {
		if(timeout > 0)
			timeout--;
	}

	@Override
	public void onKeyPress(int key) {
		if(timeout > 0)
			return;
		if(CreditCheque.instance.game.isHighScore)
			CreditCheque.instance.save();
		CreditCheque.instance.screen = new MainMenuScreen();
		System.gc();
	}

	@Override
	public void onMouseClick(int x, int y, int button) {

	}
}
