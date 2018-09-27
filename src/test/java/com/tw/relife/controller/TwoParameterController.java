package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeRequestMapping;

public class TwoParameterController {
    @RelifeRequestMapping(value = "/path", method = RelifeMethod.GET)
    public RelifeResponse sayHello(RelifeRequest request, RelifeRequest request1) {
        return new RelifeResponse(
                200,
                "Hello from " + request.getPath(),
                "text/plain");
    }
}
