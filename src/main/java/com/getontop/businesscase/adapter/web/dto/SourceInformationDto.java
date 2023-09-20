package com.getontop.businesscase.adapter.web.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SourceInformationDto implements Serializable {

	private static final long serialVersionUID = -8819320514176105738L;
	
	private String name;

}
