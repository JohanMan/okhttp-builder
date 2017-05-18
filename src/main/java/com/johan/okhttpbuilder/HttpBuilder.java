package com.johan.okhttpbuilder;

import com.johan.okhttpbuilder.call.HttpCall;
import com.johan.okhttpbuilder.call.HttpCaller;
import com.johan.okhttpbuilder.parser.MethodParseResult;
import com.johan.okhttpbuilder.parser.MethodParser;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/13.
 */

public class HttpBuilder {

    private static Map<Method, MethodParseResult> METHOD_CACHE_MAP = new HashMap<>();

    @SuppressWarnings({"unchecked"})
    public static <T> T create(Class<T> service) {
        return  (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Type returnType = method.getReturnType();
                        if (returnType == HttpCall.class) {
                            MethodParseResult parseResult = METHOD_CACHE_MAP.get(method);
                            if (parseResult == null) {
                                parseResult = MethodParser.parse(method);
                                METHOD_CACHE_MAP.put(method, parseResult);
                            }
                            return new HttpCaller(parseResult, args);
                        }
                        return method.invoke(this, args);
                    }
                });
    }

}
