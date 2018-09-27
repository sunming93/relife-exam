package com.tw.relife;

import com.tw.relife.annotations.RelifeStatusCode;

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
            RelifeResponse response = handler.process(request);
            if(response != null){
                return handler.process(request);
            }else {
                return new RelifeResponse(200);
            }
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
