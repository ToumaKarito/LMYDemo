package com.lmy.lmydemo.net;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String url;
    private Map<String,String> headMap;
    private String method;
    private HttpBody body;

    private Request(Builder builder) {
        this.url = builder.url;
        this.headMap = builder.headMap;
        this.method = builder.method;
        this.body = builder.body;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeadMap() {
        return headMap;
    }

    public String getMethod() {
        return method;
    }

    public HttpBody getBody() {
        return body;
    }

    public static class Builder {
        private String url;
        private String method;
        private Map<String,String> headMap;
        private HttpBody body;
        public Builder() {
            headMap = new HashMap<>();
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder addHeader(String name, String value) {
            headMap.put(name, value);
            return this;
        }

        public Builder post(HttpBody body) {
            method = "POST";
            this.body = body;
            return this;
        }
        public Request builder() {
            return new Request(this);
        }
    }
}