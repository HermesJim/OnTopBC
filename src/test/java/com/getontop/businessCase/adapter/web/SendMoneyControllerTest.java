package com.getontop.businessCase.adapter.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getontop.businesscase.adapter.web.TransactionController;
import com.getontop.businesscase.adapter.web.dto.AccountDto;
import com.getontop.businesscase.adapter.web.dto.TransactionInfoDto;
import com.getontop.businesscase.adapter.web.dto.RequestInfoDto;
import com.getontop.businesscase.adapter.web.dto.SourceInformationDto;
import com.getontop.businesscase.adapter.web.dto.request.TransactionRequest;
import com.getontop.businesscase.adapter.web.dto.response.TransactionResponse;
import com.getontop.businesscase.application.domain.model.Money;
import com.getontop.businesscase.application.domain.model.Account.AccountId;
import com.getontop.businesscase.application.port.in.TransactionCommand;
import com.getontop.businesscase.application.port.in.TransactionUseCase;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc
class createTransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionUseCase createTransactionUseCase;

	@Test
	void testcreateTransaction() throws Exception {

		mockMvc.perform(post("/api/v1/transactions").content(asJsonString(preparePaymentRequest())).header("Content-Type",
				"application/json")).andExpect(status().isConflict());

		then(createTransactionUseCase).should().createTransaction(eq(preparecreateTransactionCommand()));
	}

	@Test
	void testcreateTransactionOk() throws Exception {
		
		when(createTransactionUseCase.createTransaction(preparecreateTransactionCommand())).thenReturn(preparePaymentResponse());
		
		mockMvc.perform(post("/api/v1/transactions")
				.content(asJsonString(preparePaymentRequest()))
				.header("Content-Type", "application/json"))
				.andExpect(status().isOk());
		
		
	}

	private TransactionCommand preparecreateTransactionCommand() {
		return new TransactionCommand(new AccountId(41L), new AccountId(42L), Money.of(500L));
	}

	private TransactionRequest preparePaymentRequest() {

		SourceInformationDto sourceInformation = SourceInformationDto.builder().name("ONTOP INC").build();

		TransactionInfoDto source = TransactionInfoDto
				.builder().account(AccountDto.builder().accountNumber(41L).currency("USD")
						.routingNumber(Long.valueOf("6666666")).build())
				.type("Company").sourceInformation(sourceInformation).build();

		TransactionInfoDto destination = TransactionInfoDto.builder().account(
				AccountDto.builder().accountNumber(42L).currency("USD").routingNumber(Long.valueOf("7777777")).build())
				.name("William R").build();

		return TransactionRequest.builder().amount(500).source(source).destination(destination).build();

	}

	private TransactionResponse preparePaymentResponse() {

		return TransactionResponse.builder().requestInfo(RequestInfoDto.builder().status("Sucess").build())
				.paymentInfo(TransactionInfoDto.builder().amount(Double.valueOf(500)).build()).build();

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
