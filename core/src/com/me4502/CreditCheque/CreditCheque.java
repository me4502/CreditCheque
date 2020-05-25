package com.me4502.CreditCheque;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.me4502.CreditCheque.gameplay.Game;
import com.me4502.CreditCheque.ui.MainMenuScreen;
import com.me4502.CreditCheque.ui.Screen;

import java.io.BufferedReader;

public class CreditCheque extends ApplicationAdapter {

	public static CreditCheque instance;

	SpriteBatch batch;

	public Game game;

	public Texture money;

	public TextureRegion bins;
	public TextureRegion slider;
	public TextureRegion item;
	public TextureRegion itemPop;

	public TextureRegion shadow;

	public TextureRegion ASSETS;
	public TextureRegion LIABILITIES;
	public TextureRegion EXPENSES;
	public TextureRegion REVENUE;
	public TextureRegion OWNERSEQUITY;

	public TextureRegion button, buttonPressed;

	public Sound hurt;
	public Sound extraLife;
	public Sound spawn;
	public Sound points;

	public boolean realisticSlider = false;
	public boolean realisticKeys = true;

	Camera camera;

	public GlyphLayout glyphLayout;
	public BitmapFont mainFont;
	public BitmapFont smallFont;

	public int highScore = 20000;

	public Screen screen;

	@Override
	public void create () {

		load();

		instance = this;

		screen = new MainMenuScreen();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		camera.translate(w/2, h/2, 0);

		money = new Texture("img/money.png");

		bins = new TextureRegion(new Texture("img/bins.png"));
		slider = new TextureRegion(new Texture("img/slider.png"));
		item = new TextureRegion(new Texture("img/item.png"));
		itemPop = new TextureRegion(new Texture("img/itemcollect.png"));

		shadow = new TextureRegion(new Texture("img/shadow.png"));

		Texture accTypes = new Texture("img/accounttypes.png");
		ASSETS = new TextureRegion(accTypes, 1*16, 0, 16, 16);
		LIABILITIES = new TextureRegion(accTypes, 2*16, 0, 16, 16);
		EXPENSES = new TextureRegion(accTypes, 0, 1*16, 16, 16);
		REVENUE = new TextureRegion(accTypes, 1*16, 1*16, 16, 16);
		OWNERSEQUITY = new TextureRegion(accTypes, 0, 0, 16, 16);

		Texture buttonMain = new Texture("img/button.png");
		button = new TextureRegion(buttonMain, 0, 0, 16, 8);
		buttonPressed = new TextureRegion(buttonMain, 0, 1*8, 16, 8);

		hurt = Gdx.audio.newSound(Gdx.files.internal("mus/hurt.ogg"));
		extraLife = Gdx.audio.newSound(Gdx.files.internal("mus/extralife.ogg"));
		spawn = Gdx.audio.newSound(Gdx.files.internal("mus/spawn.ogg"));
		points = Gdx.audio.newSound(Gdx.files.internal("mus/points.ogg"));

		batch = new SpriteBatch();

		glyphLayout = new GlyphLayout();

		mainFont = new BitmapFont(Gdx.files.internal("font/main.fnt"), Gdx.files.internal("font/main.png"), false);
		smallFont = new BitmapFont(Gdx.files.internal("font/small.fnt"), Gdx.files.internal("font/small.png"), false);

		Gdx.input.setInputProcessor(new InputListener());
	}

	@Override
	public void render () {

		screen.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		camera.update();

		batch.setProjectionMatrix(camera.combined);

		screen.render(batch);

		batch.end();
	}

	public void drawCentredText(BitmapFont font, SpriteBatch batch, String text, int x, int y) {

		glyphLayout.setText(font, text);
		x -= glyphLayout.width/2;

		font.draw(batch, text, x, y);
	}

	public void load() {

		try(BufferedReader reader = Gdx.files.local("save-data.dat").reader(1024)) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("highscore:"))
					highScore = Integer.parseInt(line.replace("highscore:", ""));
			}
		} catch (GdxRuntimeException e) {
			// ignore
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {

//		try(PrintWriter writer = new PrintWriter(Gdx.files.local("save-data.dat").writer(false))) {
//			writer.println("highscore:" + highScore);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public class InputListener implements InputProcessor {

		@Override
		public boolean keyDown(int keycode) {

			screen.onKeyPress(keycode);

			return true;
		}

		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {

			screen.onMouseClick(screenX, screenY, button);

			return true;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			return false;
		}

	}
}
