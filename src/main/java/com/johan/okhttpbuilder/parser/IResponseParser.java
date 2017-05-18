package com.johan.okhttpbuilder.parser;

import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/5/15.
 */

public interface IResponseParser <T> {
    T parse(Type type, ResponseBody body) throws IOException;
}
