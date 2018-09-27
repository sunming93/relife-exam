package com.tw.relife;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
            return new RelifeResponse(500);
        }
    }
}
