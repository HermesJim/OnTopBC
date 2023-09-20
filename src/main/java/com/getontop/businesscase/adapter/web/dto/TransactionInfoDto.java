package com.getontop.businesscase.adapter.web.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class TransactionInfoDto implements Serializable {

	private static final long serialVersionUID = -6607627075952387781L;
	
	private String id;
	private String name;
	private String type;
	private SourceInformationDto sourceInformation;
	private AccountDto account;
	private double amount;

}
