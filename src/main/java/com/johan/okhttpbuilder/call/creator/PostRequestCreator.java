package com.johan.okhttpbuilder.call.creator;

import com.johan.okhttpbuilder.annotation.Param;
import com.johan.okhttpbuilder.parser.MethodParseResult;

import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/5/13.
 */

public class PostRequestCreator extends AbstractRequestCreator {

    public PostRequestCreator(MethodParseResult result) {
        super(result);
    }

    @Override
    Request.Builder parse(String url, List<Annotation> parameterAnnotationList, Object[] args) {
        FormBody.Builder builder = new FormBody.Builder();
        for (int i = 0; i < args.length; i++) {
            Annotation annotation = parameterAnnotationList.get(i);
            if (annotation instanceof Param) {
                Param paramAnnotation = (Param) annotation;
                String param = paramAnnotation.value();
                builder.add(param, String.valueOf(args[i]));
            }
        }
        return new Request.Builder().url(url).post(builder.build());
    }

}
