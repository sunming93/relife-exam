package com.tw.relife;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        Action other = (Action)object;
        return other.path.equals(path) && other.method.equals(method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }
}
