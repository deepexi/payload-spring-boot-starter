package com.deepexi.payload.config;

import com.deepexi.payload.PayloadSpringBootStarterTestApplicationTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.Assert.*;

public class PayloadPropertiesTest extends PayloadSpringBootStarterTestApplicationTests {

    @Autowired
    private PayloadProperties payloadProperties;

    @Value("${payload.system-error-code}")
    private String systemCode;

    @Value("${payload.biz-error-code}")
    private String bizCode;

    @Test
    public void testPayloadCode() {
        payloadProperties.setBizErrorCode("20001");
        payloadProperties.setSystemErrorCode("20005");
        Assert.assertEquals(payloadProperties.getBizErrorCode(), "20001");
        Assert.assertEquals(payloadProperties.getSystemErrorCode(), "20005");
    }

    @Test
    public void testPayload() {
        Assert.assertEquals(payloadProperties.getBizErrorCode(), this.bizCode);
        Assert.assertEquals(payloadProperties.getSystemErrorCode(), this.systemCode);
    }

    @Test
    public void testPayloadNullValue() {
        Assert.assertNotNull(payloadProperties.getSystemErrorCode());
    }

}