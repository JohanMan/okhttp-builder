package com.johan.okhttpbuilder.call;

import com.johan.okhttpbuilder.call.creator.RequestCreator;
import com.johan.okhttpbuilder.call.creator.RequestCreatorFactory;
import com.johan.okhttpbuilder.parser.BaseDataResponseParser;
import com.johan.okhttpbuilder.parser.ClassResponseParser;
import com.johan.okhttpbuilder.parser.IResponseParser;
import com.johan.okhttpbuilder.parser.InputResponseParser;
import com.johan.okhttpbuilder.parser.MethodParseResult;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/13.
 */

public class HttpCaller <T> implements HttpCall <T> {

    private Request request;
    private Type type;
    private boolean isList;

    public HttpCaller(MethodParseResult result, Object[] args) throws Exception {
        RequestCreator requestCreator = RequestCreatorFactory.create(result);
        this.request = requestCreator.create(args);
        parseReturnType(result.getReturnType());
    }

    @Override
    public void call(final HttpCallback<T> callback) {
        Call call = HttpClient.getClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() != 200) {
                    callback.onError(new Exception("response code : " + response.code()));
                    return;
                }
                IResponseParser<T> responseParser;
                if (type instanceof InputStream) {
                   responseParser = new InputResponseParser<>();
                } else if (isBaseDataType()) {
                   responseParser = new BaseDataResponseParser<>();
                } else {
                   responseParser = new ClassResponseParser<>(isList);
                }
                callback.onSuccess(responseParser.parse(type, response.body()));
            }
        });
    }

    private void parseReturnType(ParameterizedType returnType) {
        Type[] types = returnType.getActualTypeArguments();
        type = types[0];
        if (type instanceof ParameterizedType) {
            if (type.toString().endsWith(List.class.getName())) {
                isList = true;
                parseReturnType((ParameterizedType) type);
            }
        }
    }

    private boolean isBaseDataType() {
        String className = type.toString();
        return className.endsWith(String.class.getName())  ||
                className.endsWith(Integer.class.getName()) ||  className.endsWith("int")     ||
                className.endsWith(Long.class.getName())    ||  className.endsWith("long")    ||
                className.endsWith(Double.class.getName())  ||  className.endsWith("double")  ||
                className.endsWith(Float.class.getName())   ||  className.endsWith("float")   ||
                className.endsWith(Boolean.class.getName()) ||  className.endsWith("boolean");
    }

}
