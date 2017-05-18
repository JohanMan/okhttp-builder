package com.johan.okhttpbuilder.call.creator;

import com.johan.okhttpbuilder.parser.MethodParseResult;

/**
 * Created by Administrator on 2017/5/15.
 */

public class RequestCreatorFactory {

    public static RequestCreator create(MethodParseResult result) {
        RequestCreator creator = null;
        switch (result.getMethod()) {
            case GET :
                creator = new GetRequestCreator(result);
                break;
            case POST :
                creator = new PostRequestCreator(result);
                break;
            case UPLOAD :
                creator = new UploadRequestCreator(result);
                break;
        }
        return creator;
    }

}
