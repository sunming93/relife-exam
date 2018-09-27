package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;
import com.tw.relife.exceptions.SampleNotFoundException;

@RelifeController
public class StatusCodeController {
    @RelifeRequestMapping(value = "/path", method = RelifeMethod.GET)
    public RelifeResponse sayHello(RelifeRequest request) {
        throw new SampleNotFoundException();
    }
}
