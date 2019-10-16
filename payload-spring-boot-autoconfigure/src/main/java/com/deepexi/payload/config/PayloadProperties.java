package com.deepexi.payload.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "payload")
public class PayloadProperties {

    private String code = "1";

}
