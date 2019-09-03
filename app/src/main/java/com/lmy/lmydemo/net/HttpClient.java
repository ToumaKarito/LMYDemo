package com.lmy.lmydemo.net;

import android.content.Context;
import android.text.TextUtils;

import com.lmy.lmydemo.LG;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class HttpClient {
    private final int CONNECT_TIME_OUT = 10 * 1000;
    private final String ENTER = "\r\n";
    private final String BOUNDARY = "vargo-bugly";
    private Context mContext;
    private Request request;

    public HttpClient(Context context) {
        this.mContext = context;
    }

    public HttpClient newCall(Request request) {
        this.request = request;
        return this;
    }

    public Response execute() throws IOException{
        if (request == null) {
            throw new NullPointerException("request is null.");
        }
        URL url = new URL(request.getUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(request.getMethod());
        conn.setConnectTimeout(CONNECT_TIME_OUT);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        //设置请求头
        Iterator<String> headIterator = request.getHeadMap().keySet().iterator();
        while (headIterator.hasNext()) {
            String key = headIterator.next();
            if (TextUtils.isEmpty(key)) {
                continue;
            }
            String value = request.getHeadMap().get(key);
            if (key.equals("Content-Type")) {
                conn.setRequestProperty(key, value + ";boundary=" + BOUNDARY);
            } else {
                conn.setRequestProperty(key, value);
            }
        }
        //设置请求体
        HttpBody body = request.getBody();
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        if (body != null) {
            Iterator<String> formIterator = body.getFormData().keySet().iterator();
            LG.d(body.getFormData().size()+"");
            String part="--"+BOUNDARY;
            while (formIterator.hasNext()) {
                String key = formIterator.next();
                if (TextUtils.isEmpty(key)) {
                    continue;
                }
                HttpBody.BodyEntiy entiy = body.getFormData().get(key);
                if (entiy == null) {
                    continue;
                }
                if (entiy.aEnum == HttpBody.BodyEnum.file) {
                    part += ENTER
                            + "Content-Type: text/plain" + ENTER
                            + "Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + entiy.name + "\"" + ENTER+ ENTER
                            + "--" + BOUNDARY;
                    FileInputStream stream = null;
                    try {
                        File value = (File) entiy.value;
                        if (value != null) {
                            stream = new FileInputStream(value);
                            byte[] bytes = new byte[stream.available()];
                            stream.read(bytes);
                            out.write(bytes);
                            out.flush();
                        }
                    } catch (Exception e) {
                        LG.d("lmy","写入文件错误 ",e);
                    }
                    finally {
                        if (stream != null) {
                            stream.close();
                        }
                        LG.d("写入文件成功");
                    }
                } else if (entiy.aEnum == HttpBody.BodyEnum.string) {
                    part += ENTER
                            + "Content-Type: text/plain" + ENTER
                            + "Content-Disposition: form-data; name=\"" + entiy.name + "\"" + ENTER + ENTER
                            + (entiy.value==null?"null":entiy.value.toString()) + ENTER
                            + "--" + BOUNDARY;
                }
            }
            part += "--";
            LG.d("lmy",request.getUrl()+part);
            out.writeBytes(part);
            out.flush();
            int code = conn.getResponseCode();
            LG.d("code="+code);
            //获取返回值
            InputStream input = conn.getInputStream();
            byte[] b = new byte[1024];
            int len;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while((len=input.read(b))>0) {
                bos.write(b, 0, len);
            }
            String result = bos.toString("UTF-8");
            LG.d("result=" + result);
            return new Response(code, result);
        }
        if (out != null) {
            out.close();
        }
        return null;
    }
}
