package com.getontop.businesscase;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.getontop.businesscase.application.domain.model.Money;
import com.getontop.businesscase.application.domain.service.MoneyTransferProperties;

@Configuration
@EnableConfigurationProperties(BusinessCaseConfigurationProperties.class)
public class BusinessCaseConfiguration {

  /**
   * Adds a use-case-specific {@link MoneyTransferProperties} object to the application context. The properties
   * are read from the Spring-Boot-specific {@link BusinessCaseConfigurationProperties} object.
   */
  @Bean
  public MoneyTransferProperties moneyTransferProperties(BusinessCaseConfigurationProperties buckPalConfigurationProperties){
    return new MoneyTransferProperties(Money.of(buckPalConfigurationProperties.getTransferThreshold()));
  }

}
