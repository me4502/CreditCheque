package com.me4502.CreditCheque.gameplay.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.me4502.CreditCheque.CreditCheque;
import com.me4502.CreditCheque.enums.AccountType;

public class AccountMarker extends Entity {

	AccountType accountType;

	@Override
	public boolean isValid() {
		return true;
	}

	public AccountMarker(Vector2 location, AccountType accountType) {
		super(accountType.texture);

		this.location = location;
		this.accountType = accountType;

		setBoundingBox(new BoundingBox(new Vector3(0, 0, 0), new Vector3(16, 16, 0)));
	}

	@Override
	public void render(SpriteBatch batch) {

		if(CreditCheque.instance.game.slider.passenger != null) {
			batch.draw(texture, location.x, location.y);

			if(CreditCheque.instance.game.selectedAccountType != accountType) {
				batch.draw(CreditCheque.instance.shadow, location.x, location.y);
			}
		}
	}

	@Override
	public void update() {

		bounds.set(new Vector3(location.x, location.y, 0), new Vector3(location.x + 16, location.y + 16, 10));
	}

	@Override
	public void onClick(int x, int y, int press) {

		if(bounds.contains(new Vector3(x, Gdx.graphics.getHeight() - y, 1))) {

			CreditCheque.instance.game.selectAccountType(accountType);
		}
	}

}
