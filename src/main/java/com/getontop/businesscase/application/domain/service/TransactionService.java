package com.getontop.businesscase.application.domain.service;


import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

import com.getontop.businesscase.adapter.web.dto.TransactionInfoDto;
import com.getontop.businesscase.adapter.web.dto.RequestInfoDto;
import com.getontop.businesscase.adapter.web.dto.response.TransactionResponse;
import com.getontop.businesscase.application.domain.exception.ResourceNotFoundException;
import com.getontop.businesscase.application.domain.exception.ThresholdExceededException;
import com.getontop.businesscase.application.domain.model.Account;
import com.getontop.businesscase.application.domain.model.Account.AccountId;
import com.getontop.businesscase.application.port.in.TransactionCommand;
import com.getontop.businesscase.application.port.in.TransactionUseCase;
import com.getontop.businesscase.application.port.out.AccountLock;
import com.getontop.businesscase.application.port.out.LoadAccountPort;
import com.getontop.businesscase.application.port.out.UpdateAccountStatePort;
import com.getontop.businesscase.common.UseCase;

@RequiredArgsConstructor
@UseCase
@Transactional
public class TransactionService implements TransactionUseCase {

	private final LoadAccountPort loadAccountPort;
	private final AccountLock accountLock;
	private final UpdateAccountStatePort updateAccountStatePort;
	private final MoneyTransferProperties moneyTransferProperties;


	@Override
	public TransactionResponse createTransaction(TransactionCommand command) {

		checkThreshold(command);

		LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

		Account sourceAccount = loadAccountPort.loadAccount(
				command.sourceAccountId(),
				baselineDate);

		Account targetAccount = loadAccountPort.loadAccount(
				command.targetAccountId(),
				baselineDate);

		AccountId sourceAccountId = sourceAccount.getId()
				.orElseThrow(() -> new ResourceNotFoundException("expected source account ID not to be empty"));
		AccountId targetAccountId = targetAccount.getId()
				.orElseThrow(() -> new ResourceNotFoundException("expected target account ID not to be empty"));

		/**
		 * 
		 *
		 * Calling wallet balance before calculate balance in the account.
		 *
		 * ResponseEntity<WalletBalanceResponse> balanceResponse =
		 * walletProxy.getWalletBalance(userId);
		 * 
		 * if(!sourcerAccount.may (Money.of(balanceResponse.getBody().getBalance()))){
		 * 	return TransactionResponse.builder().requestInfo(RequestInfoDto.builder().status("Fail").message("insufficient funds").build()).build();
		 * }
		 * 
		 */

		
		accountLock.lockAccount(sourceAccountId);
		if (!sourceAccount.withdraw(command.money(), targetAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			return TransactionResponse.builder().requestInfo(RequestInfoDto.builder().status("Fail").message("insufficient funds").build()).build();
		}

		accountLock.lockAccount(targetAccountId);
		if (!targetAccount.deposit(command.money(), sourceAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			accountLock.releaseAccount(targetAccountId);
			return TransactionResponse.builder().requestInfo(RequestInfoDto.builder().status("Fail").message("failed transaction").build()).build();
		}

		updateAccountStatePort.updateActivities(sourceAccount);
		updateAccountStatePort.updateActivities(targetAccount);

		accountLock.releaseAccount(sourceAccountId);
		accountLock.releaseAccount(targetAccountId);
		
		/**
		 * 
		 * Calling external API to start the payment accounts
		 * 
		 * ResponseEntity<PaymentResponse> paymentResponse =
		 * paymentsProxy.getPayment(PaymentRequest);
		 * 
		 */
		
		
		/**
		 * 
		 * if Success withdraw amount to the wallet
		 * 
		 * ResponseEntity<WalletTransactionResponse> balanceResponse =
		 * walletProxy.walletTransaction(WalletTransactionRequest);
		 * 
		 */
		
		return TransactionResponse.builder().requestInfo(RequestInfoDto.builder().status("Success").build())
				.paymentInfo(TransactionInfoDto.builder()
						.amount(command.money().minus(command.money().fee()).getAmount().doubleValue()).build())
				.build();
	}

	private void checkThreshold(TransactionCommand command) {
		if(command.money().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())){
			throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.money());
		}
	}

}




