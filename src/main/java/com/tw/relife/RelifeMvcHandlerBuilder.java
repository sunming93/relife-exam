package com.tw.relife;

import java.util.ArrayList;
import java.util.List;

public class RelifeMvcHandlerBuilder{
    private List<Action> actions = new ArrayList<>();

    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod method, RelifeAppHandler handler) {
        if(path == null || method == null || handler == null){
            throw new IllegalArgumentException("the parameter of addAction can't be null");
        }

        actions.add(new Action(path, method, handler));

        return this;
    }

    public RelifeAppHandler build() {
        return new BindedHandler(actions);
    }
}
