package com.lee.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回体
 *
 * @param <T>
 */
@Data
public class JsonRes<T> implements Serializable {
    //响应码
    private boolean success;
    //返回体
    private T data;
    //返回信息
    private String msg;
}
