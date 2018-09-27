package com.tw.relife;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RelifeMvcHandlerBuilder{
    private Set<Action> actions = new HashSet<>();

    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod method, RelifeAppHandler handler) {
        if(path == null || method == null || handler == null){
            throw new IllegalArgumentException("the parameter of addAction can't be null");
        }

        actions.add(new Action(path, method, handler));

        return this;
    }

    public RelifeAppHandler build() {
        return new BindedHandler(new ArrayList<>(actions));
    }
}
