package com.deepexi.payload.config;

import com.deepexi.payload.annotation.BizErrorResponseStatus;
import com.deepexi.payload.utils.PayloadMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.deepexi.payload.utils.PayloadMapUtils.*;

@Configuration
@ConditionalOnProperty(prefix = "spring.mvc.payload", name = "error-enabled", havingValue = "true")
public class PayloadErrorAutoConfiguration extends DefaultErrorAttributes {

    private PayloadProperties payloadProperties;


    @Autowired
    public PayloadErrorAutoConfiguration(ServerProperties serverProperties, PayloadProperties payloadProperties, Map<String,String> payloadMap) {
        super(serverProperties.getError().isIncludeException());
        this.payloadProperties = payloadProperties;
        PayloadMapUtils.payloadMap = payloadMap;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> attributes = super.getErrorAttributes(webRequest, includeStackTrace);
        if (webRequest.getHeader("Accept").matches(".*text/html.*")) {
            return attributes;
        }

        Map<String, Object> resultAttributes = new LinkedHashMap<>();
        resultAttributes.put(getFieldValue(SUCCESS_DEFAULT_VALUE), false);
        resultAttributes.put(getFieldValue(MESSAGE_DEFAULT_VALUE), attributes.get("message"));

        int status = Integer.parseInt(attributes.get("status").toString());
        String code = "";
        if (status >= 400 && status < 500) {
            Throwable error = getError(webRequest);
            if (error != null) {
                BizErrorResponseStatus annotation = AnnotationUtils.findAnnotation(error.getClass(), BizErrorResponseStatus.class);
                if (annotation != null) {
                    code = annotation.value();
                } else {
                    code = payloadProperties.getBizErrorCode();
                }
            } else {
                code = payloadProperties.getBizErrorCode();
            }
        } else {
            code = payloadProperties.getSystemErrorCode();
        }
        resultAttributes.put(getFieldValue(CODE_DEFAULT_VALUE), code);

        if (payloadProperties.isEnableTrace() || includeStackTrace) {
            resultAttributes.put("stack", attributes.get("trace"));
        }

        return resultAttributes;
    }
}
