package com.deepexi.payload.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "mvc.payload")
public class PayloadProperties {

    private String code = "1";

    private String bizErrorCode = "-1";

    private String systemErrorCode = "-2";

    private boolean enableTrace = false;

    private String successField = "success";

    private String codeField = "code";

    private String messageField = "message";

}
