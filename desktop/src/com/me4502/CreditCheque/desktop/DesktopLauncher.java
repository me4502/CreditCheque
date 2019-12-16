package com.me4502.CreditCheque.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.me4502.CreditCheque.CreditCheque;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 600;
		config.height = 650;

		config.resizable = false;

		new LwjglApplication(new CreditCheque(), config);
	}
}
