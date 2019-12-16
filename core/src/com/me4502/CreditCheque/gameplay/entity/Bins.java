package com.me4502.CreditCheque.gameplay.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.me4502.CreditCheque.CreditCheque;
import com.me4502.CreditCheque.enums.TransactionType;

public class Bins extends Entity {

	TransactionType type;

	public Bins(TextureRegion sprite, Vector2 location, TransactionType type) {

		super(sprite);

		this.location = location;
		this.type = type;
		bounds = new BoundingBox(new Vector3(0,0,0), new Vector3(64,32,0));
	}

	@Override
	public int getAge() {
		return 0;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void render(SpriteBatch batch) {

		batch.draw(texture, location.x, location.y);
		CreditCheque.instance.drawCentredText(CreditCheque.instance.smallFont, batch, type.name(), (int)location.x + 32, (int)location.y - 18);
	}

	@Override
	public void update() {

		bounds.set(new Vector3(location.x, location.y, 0), new Vector3(location.x + 64, location.y + 32, 10));
	}

}
