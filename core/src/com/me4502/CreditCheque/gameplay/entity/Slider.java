package com.me4502.CreditCheque.gameplay.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.me4502.CreditCheque.CreditCheque;

public class Slider extends Entity {

	public Item passenger;

	public Slider(TextureRegion sprite, Vector2 location) {
		super(sprite);
		setLocation(location);
		setVelocity(new Vector2(0,0));
		setBoundingBox(new BoundingBox(new Vector3(0, 0, 0), new Vector3(64, 16, 0)));
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void render(SpriteBatch batch) {

		batch.draw(texture, location.x, location.y);
	}

	@Override
	public void update() {

		if(passenger != null) {
			passenger.setVelocity(new Vector2(0,0));
			passenger.setLocation(getLocation().cpy().add(new Vector2(22,12)));
		}

		for(Entity ent : CreditCheque.instance.game.entities) {
			if(ent instanceof Item) {
				if(ent == passenger || !((Item)ent).isValid)
					continue;
				if(doesIntersect(ent)) {
					if(passenger == null || !passenger.isValid()) {
						passenger = (Item) ent;
						CreditCheque.instance.game.rewardPoints(10);
					} else {
						CreditCheque.instance.game.rewardPoints(50 - ((Item) ent).hits * 10);
						((Item) ent).hits += 1;
						ent.setVelocity(new Vector2(0,-ent.getVelocity().y).scl(new Vector2(0,0.8f)));
						ent.getLocation().add(new Vector2(0,8));
					}
				}
			}
		}

		if(velocity.len2() != 0) {
			if(location.cpy().add(velocity).x < 0) {
				location.x = 0;
				velocity = new Vector2(0,0);
				return;
			} else if (location.cpy().add(velocity).x+64 > Gdx.graphics.getWidth()) {
				location.x = Gdx.graphics.getWidth()-64;
				velocity = new Vector2(0,0);
				return;
			}
			location.add(velocity);
			if(CreditCheque.instance.realisticSlider)
				velocity.scl(new Vector2(0.85f,0.85f));
			else
				velocity.scl(new Vector2(0.5f,0.5f));
		}

		bounds.set(new Vector3(location.x, location.y, 0), new Vector3(location.x + 64, location.y + 16, 10));
	}
}
