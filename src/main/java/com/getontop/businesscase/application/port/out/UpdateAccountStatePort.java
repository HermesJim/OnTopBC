package com.getontop.businesscase.application.port.out;

import com.getontop.businesscase.application.domain.model.Account;

public interface UpdateAccountStatePort {

	void updateActivities(Account account);

}
