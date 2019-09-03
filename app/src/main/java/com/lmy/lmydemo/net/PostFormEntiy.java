package com.lmy.lmydemo.net;

import java.util.HashMap;

public class PostFormEntiy {

    private boolean isSaveLoc;

    private String fileName;
    private String filePath;
    private final HashMap<String, String> fromMap;

    public PostFormEntiy() {
        fromMap = new HashMap<String, String>();
    }

    public void putData(String key, String value) {
        fromMap.put(key, value);
    }

    public HashMap<String, String> getFromMap() {
        return fromMap;
    }

    public String getValue(String key) {
        return fromMap.get(key);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isSaveLoc() {
        return isSaveLoc;
    }

    public void setSaveLoc(boolean saveLoc) {
        isSaveLoc = saveLoc;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PostFormEntiy) {
            PostFormEntiy entiy = (PostFormEntiy) obj;
            return entiy.isSaveLoc == isSaveLoc;
        }
        return false;
    }
}
