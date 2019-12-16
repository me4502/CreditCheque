package com.me4502.CreditCheque.gameplay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me4502.CreditCheque.CreditCheque;
import com.me4502.CreditCheque.enums.AccountType;
import com.me4502.CreditCheque.enums.TransactionType;
import com.me4502.CreditCheque.gameplay.entity.AccountMarker;
import com.me4502.CreditCheque.gameplay.entity.Bins;
import com.me4502.CreditCheque.gameplay.entity.Entity;
import com.me4502.CreditCheque.gameplay.entity.Item;
import com.me4502.CreditCheque.gameplay.entity.Slider;
import com.me4502.CreditCheque.ui.GameOverScreen;

public class Game {

	Random rand = new Random();

	private int[] currentScore;

	int lastAccountNum;
	int currentPlayer = 0;

	public int playerCount = 1;

	private int[] health;

	private boolean isDead = false;

	public List<Entity> entities = new ArrayList<Entity>();

	public Slider slider;

	Bins debit, credit;

	private int spawnTimer = 128;

	public AccountType selectedAccountType = null;

	//List<MAPLButton> buttons = new ArrayList<MAPLButton>();

	public boolean isHighScore;

	private int playerTimeout;

	public Game(int playerCount) {

		this.playerCount = playerCount;

		currentScore = new int[playerCount];
		health = new int[playerCount];
		for(int i = 0; i < health.length; i++)
			health[i] = 3;
		entities.add(debit = new Bins(CreditCheque.instance.bins, new Vector2(10, 60), TransactionType.DEBIT)); //Gdx.graphics.getHeight()-116
		entities.add(credit = new Bins(CreditCheque.instance.bins, new Vector2(Gdx.graphics.getWidth() - 10 - 64, 60), TransactionType.CREDIT));
		entities.add(slider = new Slider(CreditCheque.instance.slider, new Vector2(Gdx.graphics.getWidth()/2-32, 60)));

		int i = -3;

		for(AccountType t : AccountType.values()) {

			entities.add(new AccountMarker(new Vector2(Gdx.graphics.getWidth()/2 + i++*20 + 12, 40), t));
		}
	}

	public void setupPositions() {

		debit.setLocation(new Vector2(10, 60));
		credit.setLocation(new Vector2(Gdx.graphics.getWidth() - 10 - 64, 60));
		slider.getLocation().y = 60;

		for(Entity ent : entities)
			if(ent.isValid())
				if(ent.getLocation().x > Gdx.graphics.getWidth())
					ent.getLocation().x = Gdx.graphics.getWidth()-32;
	}

	public void render(SpriteBatch batch) {

		CreditCheque.instance.mainFont.setColor(Color.WHITE);

		for(int i = 0; i < health[0]; i++)
			batch.draw(CreditCheque.instance.money, Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4 + i*20 - health[0]*20 + 12, Gdx.graphics.getHeight() - 80);
		if(playerCount == 2)
			for(int i = 0; i < health[1]; i++)
				batch.draw(CreditCheque.instance.money, Gdx.graphics.getWidth()/2 + Gdx.graphics.getWidth()/4 + i*20 - health[1]*20 + 52, Gdx.graphics.getHeight() - 80);

		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
			Entity ent = it.next();
			if(ent instanceof Bins && slider.passenger == null)
				continue;
			else
				ent.render(batch);
			if(!ent.isValid() || ent instanceof Item && playerTimeout > 0)
				it.remove();
		}

		if(playerTimeout > 0)
			CreditCheque.instance.drawCentredText(CreditCheque.instance.mainFont, batch, "Player 2 Ready!", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

		if(slider.passenger != null) {
			CreditCheque.instance.drawCentredText(CreditCheque.instance.mainFont, batch, slider.passenger.account.getName(), Gdx.graphics.getWidth()/2, 20);
		}
	}

	public void update() {


		spawnTimer--;
		if(playerTimeout >= 0)
			playerTimeout--;
		if(playerTimeout == 0)
			isDead = false;

		if(health[currentPlayer] < 0) {
			if(!isDead) {
				if(currentPlayer < playerCount-1) {
					currentPlayer = 1;
					playerTimeout = 120;
					selectedAccountType = null;
					if(slider.passenger != null) {
						entities.remove(slider.passenger);
						slider.passenger.remove();
						slider.passenger = null;
					}
				} else {
					if(isHighScore)
						CreditCheque.instance.save();
					CreditCheque.instance.screen = new GameOverScreen();
				}
			}
			isDead = true;
			return;
		}

		if(spawnTimer < 0 && playerTimeout < 0) {
			CreditCheque.instance.spawn.play();
			int accountNum;
			do {
				accountNum = rand.nextInt(accounts.length);
			} while(accountNum == lastAccountNum);
			lastAccountNum = accountNum;
			entities.add(new Item(CreditCheque.instance.item, new Vector2(rand.nextInt(Gdx.graphics.getWidth() - 60) + 30, Gdx.graphics.getHeight()), accounts[accountNum]));
			spawnTimer = 350;
		}

		for(Entity ent : entities)
			if(ent.isValid())
				if(!(ent instanceof Item) || ((Item)ent).isValid())
					ent.update();

		if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.NUMPAD_4))
			slider.setVelocity(slider.getVelocity().sub(new Vector2(CreditCheque.instance.realisticSlider ? 2 : 5,0)));
		if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.NUMPAD_6))
			slider.setVelocity(slider.getVelocity().add(new Vector2(CreditCheque.instance.realisticSlider ? 2 : 5,0)));
	}

	public void selectAccountType(AccountType type) {

		if(selectedAccountType == type)
			return;
		if(type != slider.passenger.account.getAccountType()) {
			harmPlayer();
			selectedAccountType = null;
			slider.passenger.remove();
			slider.passenger = null;
			return;
		}

		rewardPoints(100);
		selectedAccountType = type;
	}

	public void rewardPoints(int points) {

		if(points <= 0)
			return;
		CreditCheque.instance.points.play();
		int oldScore = currentScore[currentPlayer];
		currentScore[currentPlayer] += points;
		if(oldScore / 10000 != currentScore[currentPlayer] / 10000)
			healPlayer();
	}

	public void harmPlayer() {

		CreditCheque.instance.hurt.play();
		health[currentPlayer] -= 1;
	}

	public void healPlayer() {

		CreditCheque.instance.extraLife.play();
		health[currentPlayer] += 1;
	}

	public void onKeyPress(int key) {

		if(slider.passenger != null) {
			for(AccountType type : AccountType.values()) {
				if (CreditCheque.instance.realisticKeys && type.key == key)
					selectAccountType(type);
				else if (!CreditCheque.instance.realisticKeys && type.altkey == key)
					selectAccountType(type);
			}

			if(key == Keys.SPACE || key == Keys.NUMPAD_5) {
				if(credit.doesIntersect(slider)) {
					if(slider.passenger.account.getType() == TransactionType.CREDIT)
						rewardPoints(200);
					else
						harmPlayer();

					selectedAccountType = null;
					slider.passenger.remove();
					slider.passenger = null;
				} else if(debit.doesIntersect(slider)) {
					if(slider.passenger.account.getType() == TransactionType.DEBIT)
						rewardPoints(200);
					else
						harmPlayer();

					selectedAccountType = null;
					slider.passenger.remove();
					slider.passenger = null;
				}
			}
		}
	}

	public void onMousePress(int x, int y, int press) {

		if(slider.passenger != null)
			for(Entity ent : entities) {
				ent.onClick(x, y, press);
			}
	}

	public int getCurrentScore() {

		return currentScore[currentPlayer];
	}

	public int getPlayerScore(int player) {

		return currentScore[player];
	}

	private static Account[] accounts = new Account[] {
		new Account("Accounts Recievable", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Cash at Bank", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Cash in Hand", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Petty Cash", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Inventory", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Computers", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Vehicles", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Land", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Buildings", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Fixtures", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Equipment", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Machinery", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Shares", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Delivery Vehicle", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Loan To Farmer Billy", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Loan To Carl Gustaf", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Loan To Joseph Stalin", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Loan To Kim Jong Un", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Loan To Kim Kardashian", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Loan To Barrack Obama", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Loan To Olive Garden", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Loan To Tony Abbot", TransactionType.DEBIT, AccountType.ASSET),
		new Account("Loan To Randy Marsh", TransactionType.DEBIT, AccountType.ASSET),

		new Account("Accounts Payable", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Wages Payable", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Interest Payable", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Unearned Revenues", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Bills", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Tax", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Loan", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Mortgage Loan Payable", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Credit Card Debt", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Bank Overdraft", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Loan From Jill", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Loan From Gerald", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Loan From Lymia", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Loan From Kim Jong Un", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Loan From Anna Kendrick", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Loan From The Washington Redskins", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Loan From Fidel Castro", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Loan From Eric Cartman", TransactionType.CREDIT, AccountType.LIABILITY),
		new Account("Loan From Butters 'Leopold' Stotch", TransactionType.CREDIT, AccountType.LIABILITY),

		new Account("Capital", TransactionType.CREDIT, AccountType.OWNERSEQUITY),
		new Account("Drawings", TransactionType.DEBIT, AccountType.OWNERSEQUITY),

		new Account("Sales Revenue", TransactionType.CREDIT, AccountType.REVENUE),
		new Account("Rent Revenue", TransactionType.CREDIT, AccountType.REVENUE),
		new Account("Service Revenue", TransactionType.CREDIT, AccountType.REVENUE),
		new Account("Interest Revenue", TransactionType.CREDIT, AccountType.REVENUE),

		new Account("Wages", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Salaries", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Rent", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Insurance", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Purchases", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Advertising", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Travel Costs", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Electricity", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Internet", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Water", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Telephone", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Domain Name", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Virtual Private Server", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Web Host", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("DNS Service", TransactionType.DEBIT, AccountType.EXPENSE),
		new Account("Depreciation", TransactionType.DEBIT, AccountType.EXPENSE),
	};
}
