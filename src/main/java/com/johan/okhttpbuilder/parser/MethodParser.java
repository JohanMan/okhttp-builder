package com.johan.okhttpbuilder.parser;

import com.johan.okhttpbuilder.annotation.Get;
import com.johan.okhttpbuilder.annotation.Heads;
import com.johan.okhttpbuilder.annotation.Post;
import com.johan.okhttpbuilder.annotation.Upload;
import com.johan.okhttpbuilder.call.HttpMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by Administrator on 2017/5/13.
 */

public class MethodParser {

    public static MethodParseResult parse(Method method) throws Exception {
        if (!canBeParsed(method)) {
            throw new Exception("MethodParser(parse) : " + method.getName() + " should has Get, Post or Upload Annotation");
        }
        MethodParseResult result = null;
        if (method.isAnnotationPresent(Get.class)) {
            result = new MethodParseResult(HttpMethod.GET, method.getAnnotation(Get.class).value());
        } else if (method.isAnnotationPresent(Post.class)) {
            result = new MethodParseResult(HttpMethod.POST, method.getAnnotation(Post.class).value());
        } else if (method.isAnnotationPresent(Upload.class)) {
            result = new MethodParseResult(HttpMethod.UPLOAD, method.getAnnotation(Upload.class).value());
        }
        if (result == null) {
            throw new Exception("MethodParser(parse) :  MethodParseResult is null");
        }
        if (method.isAnnotationPresent(Heads.class)) {
            Heads headsAnnotation = method.getAnnotation(Heads.class);
            String[] headKeys = headsAnnotation.keys();
            String[] headValues = headsAnnotation.values();
            if (headKeys.length != headValues.length) {
                throw new Exception("MethodParser(parse) : HeadKeys.length != HeadValues.length");
            }
            result.setHeadKeys(headKeys);
            result.setHeadValues(headValues);
        }
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotationArray = parameterAnnotations[i];
            if (parameterAnnotationArray.length == 0) {
                result.addParameterAnnotation(null);
                continue;
            }
            result.addParameterAnnotation(parameterAnnotationArray[0]);
        }
        Type returnType = method.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            result.setReturnType((ParameterizedType) returnType);
        } else {
            throw new Exception("MethodParser(parse) : return type must is ParameterizedType");
        }
        return result;
    }

    private static boolean canBeParsed(Method method) {
        return method.isAnnotationPresent(Get.class) || method.isAnnotationPresent(Post.class) || method.isAnnotationPresent(Upload.class);
    }

}
