package com.deepexi.payload.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

import static com.deepexi.payload.utils.PayloadMapUtils.*;

@ConfigurationProperties(prefix = "spring.mvc.payload")
public class PayloadProperties {

    /**
     * 开起统一异常结果
     */
    private boolean errorEnabled = false;

    /**
     * 成功响应码
     */
    private String code = "1";

    /**
     * 业务异常码
     */
    private String bizErrorCode = "-1";

    /**
     * 系统异常码
     */
    private String systemErrorCode = "-2";

    /**
     * 异常堆栈信息
     */
    private boolean enableTrace = false;

    /**
     * 自定义响应属性
     */
    private Map<String, String> payloadMap = new HashMap<>();



    @Bean(name = "payloadMap")
    private Map<String, String> initPayloadMap(){
        return this.payloadMap;
    }


    public PayloadProperties() {
        payloadMap.put(CODE_DEFAULT_VALUE, CODE_DEFAULT_VALUE);
        payloadMap.put(SUCCESS_DEFAULT_VALUE, SUCCESS_DEFAULT_VALUE);
        payloadMap.put(MESSAGE_DEFAULT_VALUE, MESSAGE_DEFAULT_VALUE);
        payloadMap.put(PAYLOAD_DEFAULT_VALUE, PAYLOAD_DEFAULT_VALUE);
    }

    public boolean isErrorEnabled() {
        return errorEnabled;
    }

    public void setErrorEnabled(boolean errorEnabled) {
        this.errorEnabled = errorEnabled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBizErrorCode() {
        return bizErrorCode;
    }

    public void setBizErrorCode(String bizErrorCode) {
        this.bizErrorCode = bizErrorCode;
    }

    public String getSystemErrorCode() {
        return systemErrorCode;
    }

    public void setSystemErrorCode(String systemErrorCode) {
        this.systemErrorCode = systemErrorCode;
    }

    public boolean isEnableTrace() {
        return enableTrace;
    }

    public void setEnableTrace(boolean enableTrace) {
        this.enableTrace = enableTrace;
    }

    public Map<String, String> getPayloadMap() {
        return payloadMap;
    }

    public void setPayloadMap(Map<String, String> payloadMap) {
        this.payloadMap = payloadMap;
    }
}
