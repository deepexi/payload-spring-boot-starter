package com.deepexi.payload.utils;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Author: myyl
 * @Time: 2019/10/27 - 20:29
 * @Description:
 */

public class PayloadMapUtils {

    public static final String CODE_DEFAULT_VALUE = "code";
    public static final String SUCCESS_DEFAULT_VALUE = "success";
    public static final String MESSAGE_DEFAULT_VALUE = "message";
    public static final String PAYLOAD_DEFAULT_VALUE = "payload";

    public static Map<String,String> payloadMap;

    public static String getFieldValue(String field){
        return StringUtils.isEmpty(payloadMap.get(field)) ? field : payloadMap.get(field);
    }


}
