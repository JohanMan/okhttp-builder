package com.johan.okhttpbuilder.parser;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/5/15.
 */

public class ClassResponseParser <T> implements IResponseParser <T> {

    private boolean isList;

    public ClassResponseParser(boolean isList) {
        this.isList = isList;
    }

    @Override
    public T parse(Type type, ResponseBody body) throws IOException {
        if (isList) {
            return (T) JSON.parseArray(body.toString(), (Class) type);
        } else {
            return (T) JSON.parseObject(body.toString(), (Class) type);
        }
    }

}
