package com.common.exception;

import java.io.Serializable;

/**
 * @Author json
 * @Description 前台返回结果、运行时异常结果
 * @Date 2018/4/7
 */
public class Result<T> implements Serializable {

    //    error_code 状态值：1 成功，其他数值代表失败
    private Integer status;

    //    返回消息
    private String msg;

    //    content 返回体报文的出参，使用泛型兼容不同的类型
    private T data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer code) {
        this.status = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData(Object object) {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Result() {
    }

    public Result(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
