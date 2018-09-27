package com.tw.relife;

import com.tw.relife.annotations.RelifeController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RelifeMvcHandlerBuilder{
    private Set<Action> actions = new HashSet<>();
    private List<Class> controllers = new ArrayList<>();

    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod method, RelifeAppHandler handler) {
        if(path == null || method == null || handler == null){
            throw new IllegalArgumentException("the parameter of addAction can't be null");
        }

        actions.add(new Action(path, method, handler));

        return this;
    }

    public RelifeMvcHandlerBuilder addController(Class controller) {

        controllers.add(controller);
        return this;
    }

    public RelifeAppHandler build() {
        if(actions.size() > 0){
            return new BindedActionHandler(new ArrayList<>(actions));
        }
        return new BindedControllerHandler(controllers);
    }

}
