package com.tw.relife;

import com.tw.relife.exceptions.SampleNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestBindToActionTests {
    @Test
    void should_match_the_given_process() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> new RelifeResponse(200, "Hello", "text/plain"))
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
        assertEquals("Hello", response.getContent());
        assertEquals("text/plain", response.getContentType());
    }

    @Test
    void should_return_response_404_if_path_is_not_correct() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> new RelifeResponse(200, "Hello", "text/plain"))
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(
                new RelifeRequest("/api/path", RelifeMethod.GET));

        assertEquals(404, response.getStatus());
    }

    @Test
    void should_return_response_404_if_method_is_not_correct() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> new RelifeResponse(200, "Hello", "text/plain"))
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.POST));

        assertEquals(404, response.getStatus());
    }

    @Test
    void should_throw_IllegalArgumentException_if_addAction_parameter_path_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addAction(
                                null,
                                RelifeMethod.GET,
                                request -> new RelifeResponse(200, "Hello", "text/plain"))
        );
    }

    @Test
    void should_throw_IllegalArgumentException_if_addAction_parameter_method_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addAction(
                                "/path",
                                null,
                                request -> new RelifeResponse(200, "Hello", "text/plain"))
        );
    }

    @Test
    void should_throw_IllegalArgumentException_if_addAction_parameter_handler_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addAction(
                                "/path",
                                RelifeMethod.GET,
                                null)
        );
    }

    @Test
    void should_return_response_200_if_handler_return_null() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path", RelifeMethod.GET, request -> null)
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
    }

    @Test
    void should_return_response_500_if_handler_throw_exception() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path",
                        RelifeMethod.GET,
                        request -> {
                            throw new IllegalArgumentException();
                })
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(500, response.getStatus());
    }

    @Test
    void should_return_response_status_code_for_the_exception() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path",
                        RelifeMethod.GET,
                        request -> {
                            throw new SampleNotFoundException();
                })
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(404, response.getStatus());
    }
}
