package com.me4502.CreditCheque.enums;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me4502.CreditCheque.CreditCheque;

public enum AccountType {

	OWNERSEQUITY(CreditCheque.instance.OWNERSEQUITY, Keys.O, Keys.Q),ASSET(CreditCheque.instance.ASSETS, Keys.A, Keys.W),LIABILITY(CreditCheque.instance.LIABILITIES, Keys.L, Keys.E),EXPENSE(CreditCheque.instance.EXPENSES, Keys.E, Keys.R),REVENUE(CreditCheque.instance.REVENUE, Keys.R, Keys.T);

	AccountType(TextureRegion texture, int key, int altkey) {
		this.key = key;
		this.altkey = altkey;
		this.texture = texture;
	}

	public int key, altkey;
	public TextureRegion texture;
}