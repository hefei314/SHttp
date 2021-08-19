package com.hefei.shttp.entity.base;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/08/12
 *     desc  :
 * </pre>
 */
public class Data<T> {

    private T data;
    private int status;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
