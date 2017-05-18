package com.johan.okhttpbuilder.call.creator;

import com.johan.okhttpbuilder.annotation.FileParam;
import com.johan.okhttpbuilder.annotation.Param;
import com.johan.okhttpbuilder.parser.MethodParseResult;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/5/13.
 */

public class UploadRequestCreator extends AbstractRequestCreator {

    public UploadRequestCreator(MethodParseResult result) {
        super(result);
    }

    @Override
    Request.Builder parse(String url, List<Annotation> parameterAnnotationList, Object[] args) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (int i = 0; i < args.length; i++) {
            Annotation annotation = parameterAnnotationList.get(i);
            if (annotation instanceof Param) {
                Param paramAnnotation = (Param) annotation;
                String param = paramAnnotation.value();
                builder.addFormDataPart(param, String.valueOf(args[i]));
                continue;
            }
            if (annotation instanceof FileParam) {
                FileParam fileParamAnnotation = (FileParam) annotation;
                String fileParam = fileParamAnnotation.value();
                if (args[i] instanceof File) {
                    File file = (File) args[i];
                    builder .addFormDataPart(fileParam, file.getName(),
                            RequestBody.create(getMediaType(file), file));
                } else if (args[i] instanceof File[]) {
                    File[] files = (File[]) args[i];
                    for (File file : files) {
                        builder .addFormDataPart(fileParam, file.getName(),
                                RequestBody.create(getMediaType(file), file));
                    }
                }
            }
        }
        return new Request.Builder().url(url).post(builder.build());
    }

    private MediaType getMediaType(File file) {
        String fileName = file.getName();
        // image
        if (fileName.endsWith(".jpg")) {
            return MediaType.parse("image/jpg");
        }
        if (fileName.endsWith(".png")) {
            return MediaType.parse("image/png");
        }
        if (fileName.endsWith(".gif")) {
            return MediaType.parse("image/gif");
        }
        // video
        if (fileName.endsWith(".3gp") || fileName.endsWith(".3gpp")) {
            return MediaType.parse("video/3gpp");
        }
        if (fileName.endsWith(".mp4")) {
            return MediaType.parse("video/mp4");
        }
        // audio
        if (fileName.endsWith(".mp3")) {
            return MediaType.parse("audio/mpeg");
        }
        // txt
        if (fileName.endsWith(".txt")) {
            return MediaType.parse("text/plain");
        }
        // html htm
        if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
            return MediaType.parse("text/html");
        }
        // xml
        if (fileName.endsWith(".xml")) {
            return MediaType.parse("text/xml");
        }
        // zip
        if (fileName.endsWith(".zip")) {
            return MediaType.parse("application/zip");
        }
        return null;
    }

}
