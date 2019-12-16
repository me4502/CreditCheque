package com.me4502.CreditCheque.gameplay.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;

public abstract class Entity {

	TextureRegion texture;

	Vector2 location;
	Vector2 velocity;

	BoundingBox bounds;

	public Entity(TextureRegion texture) {

		this.texture = texture;
	}

	public Vector2 getLocation() {
		return location;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public BoundingBox getBoundingBox() {
		return bounds;
	}

	public void setBoundingBox(BoundingBox bounds) {
		this.bounds = bounds;
	}

	public int getAge() {
		return 0;
	}

	public boolean isValid() {
		return false;
	}

	public abstract void render(SpriteBatch batch);

	public abstract void update();

	public boolean doesIntersect(Entity other) {
		return bounds.intersects(other.getBoundingBox());
	}

	public void setLocation(Vector2 other) {
		location = other.cpy();
	}

	public void onClick(int x, int y, int press) {

	}
}
