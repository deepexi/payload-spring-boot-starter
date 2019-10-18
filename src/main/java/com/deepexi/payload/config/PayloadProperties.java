package com.deepexi.payload.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "payload")
public class PayloadProperties {

    private String code = "1";

    @NonNull
    private String bizErrorCode = "-1";

    @NonNull
    private String systemErrorCode = "-2";

}
