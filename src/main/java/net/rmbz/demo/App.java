package net.rmbz.demo;

import okhttp3.*;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    private final static String URL_GET = "http://www.baidu.com";

    private final static String URL_POST = "http://requestb.in/17sbe751";

    private final static String COOKIE_KEY = "cookie";

    private final static String SET_COOKIE_KEY = "set-cookie";

    private final static MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/html; charset=utf-8");

    private static ResultVo result = new ResultVo();

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        get();
        post();
    }

    public static void get() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(URL_GET).build();
        okhttp3.Response response = client.newCall(request).execute();
        String cookies = response.header(SET_COOKIE_KEY, null);
        result.setBody(response.body().string());
        result.setCookies(cookies);
        if (response.isSuccessful()) {
            System.out.println("##### " + result);
        } else {
            System.err.println("##### okHttp is request error");
        }
    }

    public static void post() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL_POST)
                .header(COOKIE_KEY, result.getCookies())
                .post(RequestBody.create(MEDIA_TYPE_TEXT, result.getBody()))
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println("##### " + response.body().string());
        } else {
            System.err.println("##### okHttp is request error");
        }
    }

    protected static class ResultVo {
        private String body;
        private String cookies;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getCookies() {
            return cookies;
        }

        public void setCookies(String cookies) {
            this.cookies = cookies;
        }

        @Override
        public String toString() {
            return "ResultVo{" +
                    "body='" + body + '\'' +
                    ", cookies='" + cookies + '\'' +
                    '}';
        }
    }

}
