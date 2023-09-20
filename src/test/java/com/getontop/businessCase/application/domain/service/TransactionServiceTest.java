package com.getontop.businessCase.application.domain.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.getontop.businesscase.adapter.web.dto.response.TransactionResponse;
import com.getontop.businesscase.application.domain.model.Account;
import com.getontop.businesscase.application.domain.model.Account.AccountId;
import com.getontop.businesscase.application.domain.model.Money;
import com.getontop.businesscase.application.domain.service.MoneyTransferProperties;
import com.getontop.businesscase.application.domain.service.TransactionService;
import com.getontop.businesscase.application.port.in.TransactionCommand;
import com.getontop.businesscase.application.port.out.AccountLock;
import com.getontop.businesscase.application.port.out.LoadAccountPort;
import com.getontop.businesscase.application.port.out.UpdateAccountStatePort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class TransactionServiceTest {

	private final LoadAccountPort loadAccountPort =
			Mockito.mock(LoadAccountPort.class);

	private final AccountLock accountLock =
			Mockito.mock(AccountLock.class);

	private final UpdateAccountStatePort updateAccountStatePort =
			Mockito.mock(UpdateAccountStatePort.class);

	private final TransactionService createTransactionService =
			new TransactionService(loadAccountPort, accountLock, updateAccountStatePort, moneyTransferProperties());

	@Test
	void givenWithdrawalFails_thenOnlySourceAccountIsLockedAndReleased() {

		AccountId sourceAccountId = new AccountId(41L);
		Account sourceAccount = givenAnAccountWithId(sourceAccountId);

		AccountId targetAccountId = new AccountId(42L);
		Account targetAccount = givenAnAccountWithId(targetAccountId);

		givenWithdrawalWillFail(sourceAccount);
		givenDepositWillSucceed(targetAccount);

		TransactionCommand command = new TransactionCommand(
				sourceAccountId,
				targetAccountId,
				Money.of(300L));

		TransactionResponse success = createTransactionService.createTransaction(command);

		assertThat(success.getRequestInfo().getStatus()).isEqualTo("Fail");

		then(accountLock).should().lockAccount(eq(sourceAccountId));
		then(accountLock).should().releaseAccount(eq(sourceAccountId));
		then(accountLock).should(times(0)).lockAccount(eq(targetAccountId));
	}

	@Test
	void transactionSucceeds() {

		Account sourceAccount = givenSourceAccount();
		Account targetAccount = givenTargetAccount();

		givenWithdrawalWillSucceed(sourceAccount);
		givenDepositWillSucceed(targetAccount);

		Money money = Money.of(500L);

		TransactionCommand command = new TransactionCommand(
				sourceAccount.getId().get(),
				targetAccount.getId().get(),
				money);

		TransactionResponse success = createTransactionService.createTransaction(command);

		assertThat(success.getRequestInfo().getStatus()).isEqualTo("Success");

		AccountId sourceAccountId = sourceAccount.getId().get();
		AccountId targetAccountId = targetAccount.getId().get();

		then(accountLock).should().lockAccount(eq(sourceAccountId));
		then(sourceAccount).should().withdraw(eq(money), eq(targetAccountId));
		then(accountLock).should().releaseAccount(eq(sourceAccountId));

		then(accountLock).should().lockAccount(eq(targetAccountId));
		then(targetAccount).should().deposit(eq(money), eq(sourceAccountId));
		then(accountLock).should().releaseAccount(eq(targetAccountId));

		thenAccountsHaveBeenUpdated(sourceAccountId, targetAccountId);
	}

	private void thenAccountsHaveBeenUpdated(AccountId... accountIds){
		ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
		then(updateAccountStatePort).should(times(accountIds.length))
				.updateActivities(accountCaptor.capture());

		List<AccountId> updatedAccountIds = accountCaptor.getAllValues()
				.stream()
				.map(Account::getId)
				.map(Optional::get)
				.collect(Collectors.toList());

		for(AccountId accountId : accountIds){
			assertThat(updatedAccountIds).contains(accountId);
		}
	}

	private void givenDepositWillSucceed(Account account) {
		given(account.deposit(any(Money.class), any(AccountId.class)))
				.willReturn(true);
	}

	private void givenWithdrawalWillFail(Account account) {
		given(account.withdraw(any(Money.class), any(AccountId.class)))
				.willReturn(false);
	}

	private void givenWithdrawalWillSucceed(Account account) {
		given(account.withdraw(any(Money.class), any(AccountId.class)))
				.willReturn(true);
	}

	private Account givenTargetAccount(){
		return givenAnAccountWithId(new AccountId(42L));
	}

	private Account givenSourceAccount(){
		return givenAnAccountWithId(new AccountId(41L));
	}

	private Account givenAnAccountWithId(AccountId id) {
		Account account = Mockito.mock(Account.class);
		given(account.getId())
				.willReturn(Optional.of(id));
		given(loadAccountPort.loadAccount(eq(account.getId().get()), any(LocalDateTime.class)))
				.willReturn(account);
		return account;
	}

	private MoneyTransferProperties moneyTransferProperties(){
		return new MoneyTransferProperties(Money.of(Long.MAX_VALUE));
	}

}
