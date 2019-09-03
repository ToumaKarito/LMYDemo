package com.lmy.lmydemo.net;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HttpBody {
    private Map<String,BodyEntiy> formData;

    private HttpBody(Builder builder) {
        formData = builder.formData;
    }

    public Map<String, BodyEntiy> getFormData() {
        return formData;
    }

    public static class Builder{
        private Map<String,BodyEntiy> formData;
        public Builder() {
            formData = new HashMap<>();
        }
        public Builder addFormData(String name,String value) {
            formData.put(name, new BodyEntiy(name,value, BodyEnum.string));
            return this;
        }

        public Builder addFormData(String name, String fileName, File file) {
            formData.put(name, new BodyEntiy(fileName, file, BodyEnum.file));
            return this;
        }

        public HttpBody builder() {
            return new HttpBody(this);
        }
    }

    public static class BodyEntiy{
        String name;
        Object value;
        BodyEnum aEnum;

        public BodyEntiy(String name,Object value, BodyEnum aEnum) {
            this.name = name;
            this.value = value;
            this.aEnum = aEnum;
        }
    }
    public enum BodyEnum{
        string,
        file
    }
}
