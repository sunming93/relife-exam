package com.tw.relife;

import java.util.List;

public class BindedActionHandler implements RelifeAppHandler {
    private List<Action> actions;

    public BindedActionHandler(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) throws Throwable {
        for(Action action : actions){
            if(request.getPath().equals(action.getPath()) && request.getMethod().equals(action.getMethod())){
                return action.getHandler().process(request);
            }
        }
        return new RelifeResponse(404);
    }
}
