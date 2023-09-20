package com.getontop.businesscase.adapter.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.getontop.businesscase.adapter.persistence.entity.AccountEntity;
import com.getontop.businesscase.adapter.persistence.entity.TransactionEntity;
import com.getontop.businesscase.application.domain.model.Account;
import com.getontop.businesscase.application.domain.model.Transaction;
import com.getontop.businesscase.application.domain.model.TransactionWindow;
import com.getontop.businesscase.application.domain.model.Money;
import com.getontop.businesscase.application.domain.model.Account.AccountId;
import com.getontop.businesscase.application.domain.model.Transaction.TransactionId;

@Component
public class AccountMapper {

	public Account mapToDomainEntity(AccountEntity account, List<TransactionEntity> transactions,
			Double withdrawalBalance, Double depositBalance) {

		Money baselineBalance = Money.subtract(Money.of(depositBalance), Money.of(withdrawalBalance));

		return Account.withId(new AccountId(account.getId()), baselineBalance, mapTotransactionWindow(transactions));

	}

	TransactionWindow mapTotransactionWindow(List<TransactionEntity> transactions) {
		List<Transaction> mappedtransactions = new ArrayList<Transaction>();

		for (TransactionEntity transaction : transactions) {
			mappedtransactions
					.add(new Transaction(new TransactionId(transaction.getId()), new AccountId(transaction.getOwnerAccountId()),
							new AccountId(transaction.getSourceAccountId()), new AccountId(transaction.getTargetAccountId()),
							transaction.getTimestamp(), Money.of(transaction.getAmount()), transaction.getStatus()));
		}

		return new TransactionWindow(mappedtransactions);
	}

	public TransactionEntity mapToJpaEntity(Transaction transaction) {
		return new TransactionEntity(transaction.getId() == null ? null : transaction.getId().getValue(),
				transaction.getTimestamp(), transaction.getOwnerAccountId().getValue(),
				transaction.getSourceAccountId().getValue(), transaction.getTargetAccountId().getValue(),
				transaction.getMoney().getAmount().doubleValue(), transaction.getStatus());
	}

}
