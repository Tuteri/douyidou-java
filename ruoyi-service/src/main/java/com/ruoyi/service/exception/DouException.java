package com.ruoyi.service.exception;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DouException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DouException() {}

    public DouException(String message) {
        super(message);
    }
}
