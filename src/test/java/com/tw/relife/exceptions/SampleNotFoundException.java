package com.tw.relife.exceptions;

import com.tw.relife.annotations.RelifeStatusCode;

@RelifeStatusCode(404)
public class SampleNotFoundException extends RuntimeException {
}
