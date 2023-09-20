package com.getontop.businesscase.adapter.persistence;

import lombok.RequiredArgsConstructor;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import com.getontop.businesscase.adapter.persistence.entity.AccountEntity;
import com.getontop.businesscase.adapter.persistence.entity.TransactionEntity;
import com.getontop.businesscase.adapter.persistence.mapper.AccountMapper;
import com.getontop.businesscase.adapter.persistence.repository.AccountRepository;
import com.getontop.businesscase.adapter.persistence.repository.TransactionRepository;
import com.getontop.businesscase.application.domain.model.Account;
import com.getontop.businesscase.application.domain.model.Transaction;
import com.getontop.businesscase.application.domain.model.Account.AccountId;
import com.getontop.businesscase.application.port.out.LoadAccountPort;
import com.getontop.businesscase.application.port.out.UpdateAccountStatePort;
import com.getontop.businesscase.common.PersistenceAdapter;

@PersistenceAdapter
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;
	private final AccountMapper accountMapper;

	@Override
	public Account loadAccount(AccountId accountId, LocalDateTime baselineDate) {

		AccountEntity account = accountRepository.findById(accountId.getValue())
				.orElseThrow(() -> new EntityNotFoundException("Account not found"));

		List<TransactionEntity> activities = transactionRepository.findByOwnerSince(accountId.getValue(), baselineDate);

		Double withdrawalBalance = transactionRepository.getWithdrawalBalanceUntil(accountId.getValue(), baselineDate)
				.orElse(0.0);

		Double depositBalance = transactionRepository.getDepositBalanceUntil(accountId.getValue(), baselineDate)
				.orElse(0.0);

		return accountMapper.mapToDomainEntity(account, activities, withdrawalBalance, depositBalance);

	}

	@Override
	public void updateActivities(Account account) {
		for (Transaction transaction : account.getTransactionWindow().getTransactions()) {
			if (transaction.getId() == null) {
				transactionRepository.save(accountMapper.mapToJpaEntity(transaction));
			}
		}
	}

}
