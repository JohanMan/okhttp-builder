package com.johan.okhttpbuilder.call;

/**
 * Created by Administrator on 2017/5/13.
 */

public interface HttpCallback <T> {
    void onSuccess(T data);
    void onError(Exception e);
}
