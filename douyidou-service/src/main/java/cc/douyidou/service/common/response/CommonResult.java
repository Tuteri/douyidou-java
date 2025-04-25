package cc.douyidou.service.common.response;



import cc.douyidou.service.exception.ExceptionCodeEnum;
import cc.douyidou.service.exception.ExceptionHandler;
import cc.douyidou.service.vo.MyRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
public class CommonResult<T> {
    private long code;
    private String msg;
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(long code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(ExceptionCodeEnum.SUCCESS.getCode(), ExceptionCodeEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success(String msg) {
        return new CommonResult<T>(ExceptionCodeEnum.SUCCESS.getCode(), msg, null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ExceptionCodeEnum.SUCCESS.getCode(), ExceptionCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param record 获取的数据
     */
    public static CommonResult<Map<String, Object>> success(MyRecord record) {
        return new CommonResult<>(ExceptionCodeEnum.SUCCESS.getCode(), ExceptionCodeEnum.SUCCESS.getMessage(), record.getColumns());
    }

    /**
     * 成功返回结果
     *
     * @param recordList 获取的数据
     */
    public static CommonResult<List<Map<String, Object>>> success(List<MyRecord> recordList) {
        List<Map<String, Object>> list = new ArrayList<>();
        recordList.forEach(i -> {
             list.add(i.getColumns());
        });
        return new CommonResult<>(ExceptionCodeEnum.SUCCESS.getCode(), ExceptionCodeEnum.SUCCESS.getMessage(), list);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  msg 提示信息
     */
    public static <T> CommonResult<T> success(T data, String msg) {
        return new CommonResult<T>(ExceptionCodeEnum.SUCCESS.getCode(), msg, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(ExceptionHandler errorCode) {
        System.out.println("errorCode1:" + errorCode);
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param msg 错误信息
     */
    public static <T> CommonResult<T> failed(ExceptionHandler errorCode,String msg) {
        System.out.println("errorCode2:" + errorCode);
        return new CommonResult<T>(errorCode.getCode(), msg, null);
    }

    /**
     * 失败返回结果
     * @param msg 提示信息
     */
    public static <T> CommonResult<T> failed(String msg) {
        return new CommonResult<T>(ExceptionCodeEnum.FAILED.getCode(), msg, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ExceptionCodeEnum.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ExceptionCodeEnum.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param msg 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String msg) {
        return new CommonResult<T>(ExceptionCodeEnum.VALIDATE_FAILED.getCode(), msg, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ExceptionCodeEnum.UNAUTHORIZED.getCode(), ExceptionCodeEnum.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized() {
        return new CommonResult<T>(ExceptionCodeEnum.UNAUTHORIZED.getCode(), ExceptionCodeEnum.UNAUTHORIZED.getMessage(), null);
    }

    /**
     * 没有权限查看
     */
    public static <T> CommonResult<T> forbidden() {
        return new CommonResult<T>(ExceptionCodeEnum.FORBIDDEN.getCode(), ExceptionCodeEnum.FORBIDDEN.getMessage(), null);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ExceptionCodeEnum.FORBIDDEN.getCode(), ExceptionCodeEnum.FORBIDDEN.getMessage(), data);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
