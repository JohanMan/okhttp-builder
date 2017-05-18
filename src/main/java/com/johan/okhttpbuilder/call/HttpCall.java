package com.johan.okhttpbuilder.call;

/**
 * Created by Administrator on 2017/5/13.
 */

public interface HttpCall <T> {
    void call(HttpCallback<T> callback);
}
