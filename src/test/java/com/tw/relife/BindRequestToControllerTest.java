package com.tw.relife;

import com.tw.relife.controller.OneActionController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BindRequestToControllerTest {
    @Test
    void should_bind_request_to_action_of_controller() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(OneActionController.class)
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeRequest request = new RelifeRequest("/path", RelifeMethod.GET);
        RelifeResponse response = app.process(request);

        assertEquals(200, response.getStatus());
        assertEquals("Hello from /path", response.getContent());
        assertEquals("text/plain", response.getContentType());
    }
}
