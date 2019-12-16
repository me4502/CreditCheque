package com.me4502.CreditCheque.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me4502.CreditCheque.CreditCheque;
import com.me4502.CreditCheque.gameplay.Game;

public class MainMenuScreen extends Screen {

	List<Button> buttons;

	@Override
	public void init() {

		buttons = new ArrayList<Button>();

		buttons.add(new CaptionButton(new Vector2(getCentreX() - 64, getCentreY() + 86), 128, 32, "1 Player") {

			@Override
			public void performAction() {
				CreditCheque.instance.game = new Game(1);
				CreditCheque.instance.screen = new PlayScreen();
			}
		});
		buttons.add(new CaptionButton(new Vector2(getCentreX() - 64, getCentreY() + 24), 128, 32, "2 Player") {

			@Override
			public void performAction() {
				CreditCheque.instance.game = new Game(2);
				CreditCheque.instance.screen = new PlayScreen();
			}
		});
		buttons.add(new CaptionButton(new Vector2(getCentreX() - 64, getCentreY() - 38), 128, 32, "Exit") {

			@Override
			public void performAction() {
				System.exit(0);
			}
		});
		/*buttons.add((MAPLButton) new MAPLButton(CreditCheque.instance.getWindowWidth()-32, 0, 32, 32, "Settings", null) {

			@Override
			public void clickAction(int button) {
				CreditCheque.instance.setCurrentScreen(ScreenType.getInstanceOfScreen("Settings"));
			}
		}.setIdentifier("button:Settings"));*/
	}

	@Override
	public void render(SpriteBatch batch) {
		for(Button button : buttons)
			button.render(batch);

		CreditCheque.instance.smallFont.draw(batch, "2.0" + "         Copyright© 2013-2015 Me4502", 5, 15);
	}

	@Override
	public void onKeyPress(int key) {
		if(key == Keys.ENTER) {
			CreditCheque.instance.game = new Game(1);
			CreditCheque.instance.screen = new PlayScreen();
		}
	}

	@Override
	public void onMouseClick(int x, int y, int button) {
		for(Button mbutton : buttons)
			mbutton.onClick(x, y);
	}

	@Override
	public void update() {

	}

}