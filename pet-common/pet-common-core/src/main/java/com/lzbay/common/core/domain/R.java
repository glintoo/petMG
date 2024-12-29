package com.lzbay.common.core.domain;

import com.lzbay.common.core.utils.MessageUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.lzbay.common.core.constant.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> ok() {
        return restResult(null, HttpStatus.SUCCESS, MessageUtils.message("res.operation.success"));
    }

    public static <T> R<T> ok(T data) {
        return restResult(data,HttpStatus.SUCCESS, MessageUtils.message("res.operation.success"));
    }

    public static <T> R<T> ok(String msg) {
        return restResult(null, HttpStatus.SUCCESS, msg);
    }

    public static <T> R<T> ok(String msg, T data) {
        return restResult(data, HttpStatus.SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, HttpStatus.ERROR, MessageUtils.message("res.operation.fail"));
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, HttpStatus.ERROR, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, HttpStatus.ERROR, MessageUtils.message("res.operation.fail"));
    }

    public static <T> R<T> fail(String msg, T data) {
        return restResult(data, HttpStatus.ERROR, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static <T> R<T> warn(String msg) {
        return restResult(null, HttpStatus.WARN, msg);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static <T> R<T> warn(String msg, T data) {
        return restResult(data, HttpStatus.WARN, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    public static <T> Boolean isError(R<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R<T> ret) {
        return HttpStatus.SUCCESS == ret.getCode();
    }
}
