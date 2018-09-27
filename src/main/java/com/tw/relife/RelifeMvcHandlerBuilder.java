package com.tw.relife;

import java.util.ArrayList;
import java.util.List;

public class RelifeMvcHandlerBuilder{
    private List<Action> actions = new ArrayList<>();

    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod method, RelifeAppHandler handler) {
        actions.add(new Action(path, method, handler));

        return this;
    }

    public RelifeAppHandler build() {
        return new BindedHandler(actions);
    }
}
