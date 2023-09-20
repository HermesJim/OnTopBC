package com.getontop.businesscase.application.domain.exception;

import com.getontop.businesscase.application.domain.model.Money;

public class ThresholdExceededException extends RuntimeException {

	private static final long serialVersionUID = 6947074119460531515L;

	public ThresholdExceededException(Money threshold, Money actual) {
		super(String.format(
				"Maximum threshold for transferring money exceeded: tried to transfer %s but threshold is %s!", actual,
				threshold));
	}

}
