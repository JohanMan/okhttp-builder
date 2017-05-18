package com.johan.okhttpbuilder.call.creator;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/5/13.
 */

public interface RequestCreator {
    Request create(Object[] args) throws Exception;
}
