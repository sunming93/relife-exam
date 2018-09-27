package com.tw.relife;

import java.util.List;

public class BindedHandler implements RelifeAppHandler {
    private List<Action> actions;

    public BindedHandler(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) {
        for(Action action : actions){
            if(request.getPath().equals(action.getPath()) && request.getMethod().equals(action.getMethod())){
                return action.getHandler().process(request);
            }
        }
        return new RelifeResponse(404);
    }
}
