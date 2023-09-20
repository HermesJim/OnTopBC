package com.getontop.businesscase.adapter.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.getontop.businesscase.adapter.web.dto.TransactionInfoDto;
import com.getontop.businesscase.adapter.web.dto.RequestInfoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class TransactionResponse implements Serializable{
	
	private static final long serialVersionUID = -1098266359091634863L;
	
	private RequestInfoDto requestInfo;
	private TransactionInfoDto paymentInfo;

}
