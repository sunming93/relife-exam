package com.tw.relife;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleActionsTest {
    @Test
    void should_map_to_different_action_for_different_request() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> new RelifeResponse(200, "get action", "text/plain"))
                .addAction(
                        "/path",
                        RelifeMethod.POST,
                        request -> new RelifeResponse(403, "post action", "text/plain"))
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeRequest getRequest = new RelifeRequest("/path", RelifeMethod.GET);
        RelifeResponse responseForGet = app.process(getRequest);
        assertEquals(200, responseForGet.getStatus());

        RelifeRequest postRequest = new RelifeRequest("/path", RelifeMethod.POST);
        RelifeResponse responseForPost = app.process(postRequest);
        assertEquals(403, responseForPost.getStatus());
    }

    @Test
    void should_map_to_the_last_action_if_there_are_multiple_handlers() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path",
                        RelifeMethod.POST,
                        request -> new RelifeResponse(200, "first action", "text/plain"))
                .addAction(
                        "/path",
                        RelifeMethod.POST,
                        request -> new RelifeResponse(403, "second action", "text/plain"))
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeRequest request = new RelifeRequest("/path", RelifeMethod.POST);
        RelifeResponse response = app.process(request);

        assertEquals(403, response.getStatus());
        assertEquals("second action", response.getContent());
        assertEquals("text/plain", response.getContentType());
    }
}
