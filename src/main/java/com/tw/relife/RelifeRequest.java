package com.tw.relife;

public class RelifeRequest {
    private final String path;
    private final RelifeMethod method;
    private final String content;
    private final String contentType;

    public RelifeRequest(String path, RelifeMethod method) {
        this(path, method, null, null);
    }

    public RelifeRequest(String path, RelifeMethod method, String content, String contentType) {
        this.path = path;
        this.method = method;
        this.content = content;
        this.contentType = contentType;
    }

    public String getPath() {
        return path;
    }

    public RelifeMethod getMethod() {
        return method;
    }

    public String getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
