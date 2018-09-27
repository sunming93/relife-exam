package com.tw.relife;

import com.tw.relife.classTools.AbstractClass;
import com.tw.relife.classTools.InterfaceClass;
import com.tw.relife.classTools.NoControllerClass;
import com.tw.relife.controller.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void should_throw_IllegalArgumentException_if_addController_parameter_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(null)
        );
    }

    @Test
    void should_throw_IllegalArgumentException_if_addController_parameter_is_abstract() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(AbstractClass.class)
        );
    }

    @Test
    void should_throw_IllegalArgumentException_if_addController_parameter_is_interface() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(InterfaceClass.class)
        );
    }

    @Test
    void should_throw_IllegalArgumentException_if_addController_parameter_has_no_RelifeController() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(NoControllerClass.class)
        );
    }

    @Test
    void should_throw_IllegalArgumentException_if_action_has_more_than_one_parameter() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(TwoParameterController.class)
        );
    }

    @Test
    void should_throw_IllegalArgumentException_if_action_parameter_is_not_RelifeRequest() {
        assertThrows(IllegalArgumentException.class,
                () -> new RelifeMvcHandlerBuilder()
                        .addController(NotRelifeRequestController.class)
        );
    }

    @Test
    void should_return_different_response_for_different_action() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(MultipleActionsController.class)
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
    void should_return_the_first_response_for_duplicate_action() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(DuplicateActionsController.class)
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeRequest getRequest = new RelifeRequest("/path", RelifeMethod.GET);
        RelifeResponse responseForGet = app.process(getRequest);
        assertEquals(200, responseForGet.getStatus());
    }

    @Test
    void should_return_response_200_if_handler_return_null() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(HandlerNullController.class)
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
    }

    @Test
    void should_return_response_status_code_for_the_exception() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(StatusCodeController.class)
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(404, response.getStatus());
    }

    @Test
    void should_call_the_handler_of_action_if_addAction_first() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> new RelifeResponse(202, "Hi", "text/plain"))
                .addController(OneActionController.class)
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(202, response.getStatus());
        assertEquals("Hi", response.getContent());
        assertEquals("text/plain", response.getContentType());
    }

    @Test
    void should_call_the_handler_of_controller_if_addController_first() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(OneActionController.class)
                .addAction(
                        "/path",
                        RelifeMethod.GET,
                        request -> new RelifeResponse(202, "Hi", "text/plain"))
                .build();
        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200, response.getStatus());
        assertEquals("Hello from /path", response.getContent());
        assertEquals("text/plain", response.getContentType());
    }
}
