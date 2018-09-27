package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

@RelifeController
public class MultipleActionsController {
    @RelifeRequestMapping(value = "/path", method = RelifeMethod.GET)
    public RelifeResponse sayHello(RelifeRequest request) {
        return new RelifeResponse(
                200,
                "Hello from " + request.getPath(),
                "text/plain");
    }

    @RelifeRequestMapping(value = "/path", method = RelifeMethod.POST)
    public RelifeResponse sayHi(RelifeRequest request) {
        return new RelifeResponse(
                403,
                "Hello from " + request.getPath(),
                "text/plain");
    }

}
