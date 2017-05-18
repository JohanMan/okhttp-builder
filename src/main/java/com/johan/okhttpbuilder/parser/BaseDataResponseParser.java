package com.johan.okhttpbuilder.parser;

import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/5/15.
 */

public class BaseDataResponseParser <T> implements IResponseParser <T> {

    @SuppressWarnings({"unchecked"})
    @Override
    public T parse(Type type, ResponseBody body) throws IOException {
        String className = type.toString();
        if (className.endsWith(String.class.getName())) {
            return (T) body.string();
        } else if (className.endsWith(Integer.class.getName())) {
            return (T) (Integer)Integer.parseInt(body.string());
        } else if (className.endsWith(Long.class.getName())) {
            return (T) (Long)Long.parseLong(body.string());
        } else if (className.endsWith(Double.class.getName())) {
            return (T) (Double)Double.parseDouble(body.string());
        } else if (className.endsWith(Float.class.getName())) {
            return (T) (Float)Float.parseFloat(body.string());
        } else if (className.endsWith(Boolean.class.getName())) {
            return (T) (Boolean)Boolean.parseBoolean(body.string());
        }
        return null;
    }

}
