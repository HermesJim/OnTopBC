package com.getontop.businesscase.application.port.out.proxy;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.getontop.businesscase.application.port.out.proxy.dto.WalletBalanceResponse;

@RefreshScope
@FeignClient(value = "http://mockoon.tools.getontop.com:3000/wallets")
public interface WalletProxy {

	@GetMapping("/balance?user_id={id}")
	public ResponseEntity<WalletBalanceResponse> getWalletBalance(@PathVariable long id);
}
