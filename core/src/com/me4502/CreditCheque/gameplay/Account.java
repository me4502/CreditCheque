package com.me4502.CreditCheque.gameplay;

import com.me4502.CreditCheque.enums.AccountType;
import com.me4502.CreditCheque.enums.TransactionType;

public class Account {

	private String name;
	private TransactionType type;
	private AccountType accountType;

	public Account(String name, TransactionType type, AccountType accountType) {

		this.name = name;
		this.type = type;
		this.accountType = accountType;
	}

	public String getName() {

		return name;
	}

	public TransactionType getType() {

		return type;
	}

	public AccountType getAccountType() {

		return accountType;
	}
}