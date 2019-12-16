package com.me4502.CreditCheque.gameplay.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.me4502.CreditCheque.CreditCheque;
import com.me4502.CreditCheque.gameplay.Account;

public class Item extends Entity {

	private static Vector2 GRAVITY = new Vector2(0,3.5f);

	public Account account;

	int age;

	int timer = 0;

	public boolean isValid = true;

	public int hits = 0;

	public Item(TextureRegion sprite, Vector2 location, Account account) {

		super(sprite);
		this.location = location;
		this.account = account;
		velocity = new Vector2(0,0.4f);
		bounds = new BoundingBox(new Vector3(0,0,0), new Vector3(16,16,0));
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public boolean isValid() {
		return isValid || timer > 0;
	}

	@Override
	public void render(SpriteBatch batch) {

		batch.draw(texture, location.x, location.y);
	}

	@Override
	public void update() {

		if(location.y < 0) {
			isValid = false;
			CreditCheque.instance.game.harmPlayer();
			return;
		}
		age++;

		if(velocity.y < GRAVITY.y) {
			if(velocity.y <= 0.1 && velocity.y >= -0.1)
				velocity.add(new Vector2(0,0.01f));
			else if (velocity.y < 0)
				velocity.scl(new Vector2(0,0.99f));
			else
				velocity.scl(new Vector2(0,1.01f));
		}

		location.sub(velocity);

		bounds.set(new Vector3(location.x, location.y, 0), new Vector3(location.x + 16, location.y + 16, 10));

		if(timer > 0 && texture != CreditCheque.instance.itemPop) {
			texture = CreditCheque.instance.itemPop;
		}

		timer --;
	}

	public void remove() {
		isValid = false;
		timer = 120;
	}
}
