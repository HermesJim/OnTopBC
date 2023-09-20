package com.getontop.businesscase.application.port.in;

import com.getontop.businesscase.adapter.web.dto.response.TransactionResponse;

public interface TransactionUseCase {

	TransactionResponse createTransaction(TransactionCommand command);

}
