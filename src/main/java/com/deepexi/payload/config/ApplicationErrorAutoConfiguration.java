package com.deepexi.payload.config;

import com.deepexi.payload.annotation.BizErrorResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "mvc.payload.error", name = "enabled", havingValue = "true")
public class ApplicationErrorAutoConfiguration extends DefaultErrorAttributes {

    private PayloadProperties payloadProperties;

    @Autowired
    public ApplicationErrorAutoConfiguration(ServerProperties serverProperties, PayloadProperties payloadProperties) {
        super(serverProperties.getError().isIncludeException());
        this.payloadProperties = payloadProperties;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> attributes = super.getErrorAttributes(webRequest, includeStackTrace);
        if (webRequest.getHeader("Accept").matches(".*text/html.*")) {
            return attributes;
        }

        Map<String, Object> resultAttributes = new LinkedHashMap<>();
        resultAttributes.put(payloadProperties.getSuccessField(), false);
        resultAttributes.put(payloadProperties.getMessageField(), attributes.get("message"));

        int status = Integer.parseInt(attributes.get("status").toString());
        if (status >= 400 && status < 500) {
            Throwable error = getError(webRequest);
            if (error != null) {
                BizErrorResponseStatus annotation = AnnotationUtils.findAnnotation(error.getClass(), BizErrorResponseStatus.class);
                if (annotation != null) {
                    resultAttributes.put(payloadProperties.getCodeField(), annotation.value());
                } else {
                    resultAttributes.put(payloadProperties.getCodeField(), payloadProperties.getBizErrorCode());
                }
            } else {
                resultAttributes.put(payloadProperties.getCodeField(), payloadProperties.getBizErrorCode());
            }
        } else {
            resultAttributes.put(payloadProperties.getCodeField(), payloadProperties.getSystemErrorCode());
        }

        if (payloadProperties.isEnableTrace() || includeStackTrace) {
            resultAttributes.put("stack", attributes.get("trace"));
        }

        return resultAttributes;
    }
}
