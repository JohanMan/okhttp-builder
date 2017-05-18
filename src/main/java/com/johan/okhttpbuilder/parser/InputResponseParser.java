package com.johan.okhttpbuilder.parser;

import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/5/15.
 */

public class InputResponseParser <T> implements IResponseParser <T> {

    @Override
    public T parse(Type type, ResponseBody body) throws IOException {
        return (T) body.byteStream();
    }

}
