package com.lee.exception;

/**
 * Created by lee on 17/2/22.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String msg) {
        super(msg);
    }
}