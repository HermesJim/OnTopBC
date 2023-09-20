package com.getontop.businessCase.adapter.repository;

import java.time.LocalDateTime;

import com.getontop.businesscase.adapter.persistence.AccountPersistenceAdapter;
import com.getontop.businesscase.adapter.persistence.entity.TransactionEntity;
import com.getontop.businesscase.adapter.persistence.mapper.AccountMapper;
import com.getontop.businesscase.adapter.persistence.repository.TransactionRepository;
import com.getontop.businesscase.application.domain.model.Account;
import com.getontop.businesscase.application.domain.model.TransactionWindow;
import com.getontop.businesscase.application.domain.model.Money;
import com.getontop.businesscase.application.domain.model.Account.AccountId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static com.getontop.businessCase.util.AccountTestData.*;
import static com.getontop.businessCase.util.TransactionTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({AccountPersistenceAdapter.class, AccountMapper.class})
class AccountPersistenceAdapterTest {

	@Autowired
	private AccountPersistenceAdapter adapterUnderTest;

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	void loadsAccount() {
		Account account = adapterUnderTest.loadAccount(new AccountId(Long.valueOf("0245253419")), LocalDateTime.of(2023, 8, 10, 0, 0));

		assertThat(account.getTransactionWindow().getTransactions()).isNotNull();
		assertThat(account.calculateBalance()).isNotNull();
	}

	@Test
	void updatesActivities() {
		Account account = defaultAccount()
				.withBaselineBalance(Money.of(555L))
				.withTransactionWindow(new TransactionWindow(
						defaultTransaction()
								.withId(null)
								.withMoney(Money.of(1L)).build()))
				.build();

		adapterUnderTest.updateActivities(account);

		assertThat(transactionRepository.count()).isNotNull();

		TransactionEntity savedTransaction = transactionRepository.findAll().get(0);
		assertThat(savedTransaction.getAmount()).isNotNull();
	}

}