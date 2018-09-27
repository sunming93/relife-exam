package com.tw.relife;

import com.tw.relife.exceptions.SampleNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionResponseTest {
    @Test
    void should_generate_response_from_exception() {
        RelifeApp app = new RelifeApp(request -> {
            throw new SampleNotFoundException();
        });

        RelifeRequest whateverRequest = new RelifeRequest("/any/path", RelifeMethod.GET);
        RelifeResponse response = app.process(whateverRequest);

        assertEquals(404, response.getStatus());
    }
}
