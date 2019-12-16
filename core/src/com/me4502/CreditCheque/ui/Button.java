package com.me4502.CreditCheque.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public abstract class Button {

	Vector2 location;

	BoundingBox box;

	int width, height;

	public Button(Vector2 location, int width, int height) {

		this.location = location;

		this.width = width;
		this.height = height;

		box = new BoundingBox(new Vector3(location.x, location.y, 0), new Vector3(location.x + width, location.y + height, 10));
	}

	public abstract void render(SpriteBatch batch);

	public void onClick(int x, int y) {

		if(box.contains(new Vector3(x, Gdx.graphics.getHeight() - y, 4))) {
			performAction();
		}
	}

	public abstract void performAction();
}
