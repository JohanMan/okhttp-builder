package com.johan.okhttpbuilder.parser;

import com.johan.okhttpbuilder.call.HttpMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/5/13.
 */

public class MethodParseResult {

    private HttpMethod method;
    private String url;
    private String[] headKeys;
    private String[] headValues;
    private List<Annotation> parameterAnnotationList;
    private ParameterizedType returnType;

    public MethodParseResult(HttpMethod method, String url) {
        this.method = method;
        this.url = url;
        this.parameterAnnotationList = new ArrayList<>();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String[] getHeadKeys() {
        return headKeys;
    }

    public void setHeadKeys(String[] headKeys) {
        this.headKeys = headKeys;
    }

    public String[] getHeadValues() {
        return headValues;
    }

    public void setHeadValues(String[] headValues) {
        this.headValues = headValues;
    }

    public void addParameterAnnotation(Annotation annotation) {
        parameterAnnotationList.add(annotation);
    }

    public List<Annotation> getParameterAnnotationList() {
        return parameterAnnotationList;
    }

    public ParameterizedType getReturnType() {
        return returnType;
    }

    public void setReturnType(ParameterizedType  returnType) {
        this.returnType = returnType;
    }

}
