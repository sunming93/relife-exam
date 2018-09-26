package com.tw.relife;

public class RelifeResponse {
    private final int status;
    private final String content;
    private final String contentType;

    public RelifeResponse(int status) {
        this(status, null, null);
    }

    public RelifeResponse(int status, String content, String contentType) {
        this.status = status;
        this.content = content;
        this.contentType = contentType;
    }

    public int getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
