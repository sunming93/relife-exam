package com.tw.relife;

import com.tw.relife.exceptions.RelifeStatusCode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.annotation.Annotation;

public class RelifeApp implements RelifeAppHandler {
    private final RelifeAppHandler handler;

    public RelifeApp(RelifeAppHandler handler) {
        // TODO: You can start here
        if(handler == null){
            throw new IllegalArgumentException("handler can't be null.");
        }

        this.handler = handler;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) {
        // TODO: You can start here
        try {
            return handler.process(request);
        }catch (Throwable throwable){
            RelifeStatusCode statusCodeAnnotation = throwable.getClass().getDeclaredAnnotation(RelifeStatusCode.class);

            if(statusCodeAnnotation != null)  {
                int statusCode = statusCodeAnnotation.value();
                return new RelifeResponse(statusCode);
            }

            return new RelifeResponse(500);
        }
    }
}
