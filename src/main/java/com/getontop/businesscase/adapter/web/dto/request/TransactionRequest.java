package com.getontop.businesscase.adapter.web.dto.request;

import com.getontop.businesscase.adapter.web.dto.TransactionInfoDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
	
	@NotNull
	private TransactionInfoDto source;
	@NotNull
	private TransactionInfoDto destination;
	@NotNull
	private double amount;
	
}
