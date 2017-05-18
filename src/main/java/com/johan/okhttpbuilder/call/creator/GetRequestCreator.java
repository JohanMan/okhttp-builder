package com.johan.okhttpbuilder.call.creator;

import com.johan.okhttpbuilder.annotation.Query;
import com.johan.okhttpbuilder.parser.MethodParseResult;

import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/5/13.
 */

public class GetRequestCreator extends AbstractRequestCreator {

    public GetRequestCreator(MethodParseResult result) {
        super(result);
    }

    @Override
    Request.Builder parse(String url, List<Annotation> parameterAnnotationList, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            Annotation annotation = parameterAnnotationList.get(i);
            if (annotation instanceof Query) {
                Query queryAnnotation = (Query) annotation;
                String query = queryAnnotation.value();
                url = url.replace("<" + query + ">", String.valueOf(args[i]));
            }
        }
        return new Request.Builder().url(url);
    }

}
