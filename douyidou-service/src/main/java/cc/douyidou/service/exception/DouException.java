package cc.douyidou.service.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Slf4j
public class DouException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DouException() {}

    public DouException(String message) {
        super(message);
    }
}
