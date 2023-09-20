package com.getontop.businesscase.adapter.web;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.getontop.businesscase.application.domain.model.Money;
import com.getontop.businesscase.adapter.web.dto.request.TransactionRequest;
import com.getontop.businesscase.adapter.web.dto.response.TransactionResponse;
import com.getontop.businesscase.application.domain.model.Account.AccountId;
import com.getontop.businesscase.application.port.in.TransactionCommand;
import com.getontop.businesscase.application.port.in.TransactionUseCase;
import com.getontop.businesscase.common.WebAdapter;

import jakarta.validation.Valid;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionUseCase transactionUseCase;

	@PostMapping(path = "/api/v1/transactions")
	ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request) {

		TransactionCommand command = new TransactionCommand(
				new AccountId(request.getSource().getAccount().getAccountNumber()),
				new AccountId(request.getDestination().getAccount().getAccountNumber()), Money.of(request.getAmount()));

		TransactionResponse response = transactionUseCase.createTransaction(command);
		if (Objects.isNull(response) || Objects.equals(response.getRequestInfo().getStatus(), "Fail")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
	


}
