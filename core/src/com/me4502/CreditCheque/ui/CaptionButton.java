package com.me4502.CreditCheque.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me4502.CreditCheque.CreditCheque;

public abstract class CaptionButton extends Button {

	String caption;

	public CaptionButton(Vector2 location, int width, int height, String caption) {
		super(location, width, height);

		this.caption = caption;
	}

	@Override
	public void render(SpriteBatch batch) {

		batch.draw(CreditCheque.instance.button, location.x, location.y, width, height);

		CreditCheque.instance.mainFont.setColor(Color.BLACK);
		CreditCheque.instance.drawCentredText(CreditCheque.instance.mainFont, batch, caption, (int)location.x + width/2, (int)location.y + 24);
	}

}
