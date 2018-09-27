package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

@RelifeController
public class HandlerNullController {
    @RelifeRequestMapping(value = "/path", method = RelifeMethod.GET)
    public RelifeResponse sayHello(RelifeRequest request) {
        return null;
    }
}
