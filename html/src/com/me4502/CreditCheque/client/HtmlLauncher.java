package com.me4502.CreditCheque.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.me4502.CreditCheque.CreditCheque;

public class HtmlLauncher extends GwtApplication {

	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(600, 650);
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new CreditCheque();
	}
}