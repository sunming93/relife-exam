package com.tw.relife;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestBindToActionTests {
    @Test
    void should_match_the_given_process() {
        RelifeApp app = getOneValidApp();
        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
        assertEquals("Hello", response.getContent());
        assertEquals("text/plain", response.getContentType());
    }

    private RelifeApp getOneValidApp() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path", RelifeMethod.GET,
                        request -> new RelifeResponse(200, "Hello", "text/plain"))
                .build();

        return new RelifeApp(handler);
    }

    @Test
    void should_return_response_404_if_path_is_not_correct() {
        RelifeApp app = getOneValidApp();
        RelifeResponse response = app.process(
                new RelifeRequest("/api/path", RelifeMethod.GET));

        assertEquals(404, response.getStatus());
    }

    @Test
    void should_return_response_404_if_method_is_not_correct() {
        RelifeApp app = getOneValidApp();
        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.POST));

        assertEquals(404, response.getStatus());
    }
}
