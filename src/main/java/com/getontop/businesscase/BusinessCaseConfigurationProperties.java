package com.getontop.businesscase;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "buckpal")
public class BusinessCaseConfigurationProperties {

  private long transferThreshold = Long.MAX_VALUE;

}
