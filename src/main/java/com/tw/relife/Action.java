package com.tw.relife;

public class Action {
    private String path;
    private RelifeMethod method;
    private RelifeAppHandler handler;

    public Action(String path, RelifeMethod method, RelifeAppHandler handler) {
        this.path = path;
        this.method = method;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public RelifeMethod getMethod() {
        return method;
    }

    public RelifeAppHandler getHandler() {
        return handler;
    }
}
