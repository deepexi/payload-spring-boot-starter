package com.deepexi.payload.config;

import com.deepexi.payload.annotation.Payload;
import com.deepexi.payload.utils.PayloadMapUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.HashMap;
import java.util.Map;

import static com.deepexi.payload.utils.PayloadMapUtils.*;


public class RequestResponseBodyMethodProcessorProxy implements HandlerMethodReturnValueHandler {

    private RequestResponseBodyMethodProcessor delegate;

    private PayloadProperties payloadProperties;


    RequestResponseBodyMethodProcessorProxy(RequestResponseBodyMethodProcessor delegate, PayloadProperties payloadProperties) {
        this.delegate = delegate;
        this.payloadProperties = payloadProperties;
        PayloadMapUtils.payloadMap = payloadProperties.getPayloadMap();
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        if (!delegate.supportsReturnType(methodParameter)) {
            return false;
        }

        if (AnnotationUtils.findAnnotation(methodParameter.getMethod(), Payload.class) != null) {
            return true;
        } else {
            Class<?> clazz = methodParameter.getContainingClass();
            return AnnotationUtils.findAnnotation(clazz, Payload.class) != null;
        }
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        Map<String, Object> result = new HashMap<>();
        result.put(getFieldValue(SUCCESS_DEFAULT_VALUE), true);
        result.put(getFieldValue(CODE_DEFAULT_VALUE), this.getCode());
        result.put(getFieldValue(PAYLOAD_DEFAULT_VALUE), o);

        delegate.handleReturnValue(result, methodParameter, modelAndViewContainer, nativeWebRequest);
    }

    private String getCode(){
        return payloadProperties.getCode().trim().length() == 0 ? "1" : payloadProperties.getCode();
    }
}