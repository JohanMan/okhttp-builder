package com.johan.okhttpbuilder.call.creator;

import com.johan.okhttpbuilder.annotation.Head;
import com.johan.okhttpbuilder.annotation.Path;
import com.johan.okhttpbuilder.parser.MethodParseResult;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/5/13.
 */

public abstract class AbstractRequestCreator implements RequestCreator {

    protected MethodParseResult result;

    public AbstractRequestCreator(MethodParseResult result) {
        this.result = result;
    }

    @Override
    public Request create(Object[] args) throws Exception {
        String url = result.getUrl();
        List<Annotation> parameterAnnotationList = result.getParameterAnnotationList();
        if (args == null) {
            return new Request.Builder().url(url).build();
        }
        if (args.length != parameterAnnotationList.size()) {
            throw new Exception(getClass().getName() + " : " + "create args length no equal with result parameter annotation list size");
        }
        Map<String, String> headMap = new HashMap<>();
        if (result.getHeadKeys() != null && result.getHeadValues() != null) {
            for (int i = 0; i < result.getHeadValues().length; i++) {
                headMap.put(result.getHeadKeys()[i], result.getHeadValues()[i]);
            }
        }
        for (int i = 0; i < args.length; i++) {
            Annotation annotation = parameterAnnotationList.get(i);
            if (annotation instanceof Path) {
                Path pathAnnotation = (Path) annotation;
                String path = pathAnnotation.value();
                url = url.replace("{" + path + "}", String.valueOf(args[i]));
                continue;
            }
            if (annotation instanceof Head) {
                Head headAnnotation = (Head) annotation;
                String head = headAnnotation.value();
                headMap.put(head, String.valueOf(args[i]));
            }
        }
        Request.Builder builder = parse(url, parameterAnnotationList, args);
        for (Map.Entry<String, String> entry : headMap.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    abstract Request.Builder parse(String url, List<Annotation> parameterAnnotationList, Object[] args);

}
