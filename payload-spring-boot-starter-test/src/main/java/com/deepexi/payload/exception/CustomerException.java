package com.deepexi.payload.exception;

import com.deepexi.payload.annotation.BizErrorResponseStatus;

@BizErrorResponseStatus("00000-0000-0001")
public class CustomerException extends RuntimeException {
}
