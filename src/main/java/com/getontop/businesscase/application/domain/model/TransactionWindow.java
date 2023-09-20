package com.getontop.businesscase.application.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.NonNull;

/**
 * A window of account transactions.
 */
public class TransactionWindow {

	/**
	 * The list of account transactions within this window.
	 */
	private List<Transaction> transactions;

	/**
	 * The timestamp of the first transaction within this window.
	 */
	public LocalDateTime getStartTimestamp() {
		return transactions.stream()
				.min(Comparator.comparing(Transaction::getTimestamp))
				.orElseThrow(IllegalStateException::new)
				.getTimestamp();
	}

	/**
	 * The timestamp of the last transaction within this window.
	 * @return
	 */
	public LocalDateTime getEndTimestamp() {
		return transactions.stream()
				.max(Comparator.comparing(Transaction::getTimestamp))
				.orElseThrow(IllegalStateException::new)
				.getTimestamp();
	}

	/**
	 * Calculates the balance by summing up the values of all transactions within this window.
	 */
	public Money calculateBalance(Account.AccountId accountId) {
		Money depositBalance = transactions.stream()
				.filter(a -> a.getTargetAccountId().equals(accountId))
				.map(Transaction::getMoney)
				.reduce(Money.ZERO, Money::add);

		Money withdrawalBalance = transactions.stream()
				.filter(a -> a.getSourceAccountId().equals(accountId))
				.map(Transaction::getMoney)
				.reduce(Money.ZERO, Money::add);

		return Money.add(depositBalance, withdrawalBalance.negate());
	}

	public TransactionWindow(@NonNull List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public TransactionWindow(@NonNull Transaction... transactions) {
		this.transactions = new ArrayList<Transaction>(Arrays.asList(transactions));
	}

	public List<Transaction> getTransactions() {
		return Collections.unmodifiableList(this.transactions);
	}

	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}
}
