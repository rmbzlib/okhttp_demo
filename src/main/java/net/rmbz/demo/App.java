package net.rmbz.demo;

import okhttp3.*;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Request request = new Request();
    }

    private Request networkRequest(Request request) throws IOException {
        Request.Builder result = request.newBuilder();

        OkHttpClient client = new OkHttpClient();

        List<Cookie> cookies = client.cookieJar().loadForRequest(request.url());
        if (!cookies.isEmpty()) {
            result.header("Cookie", cookieHeader(cookies));
        }

        //例行省略....

        return result.build();
    }

    private String cookieHeader(List<Cookie> cookies) {
        StringBuilder cookieHeader = new StringBuilder();
        for (int i = 0, size = cookies.size(); i < size; i++) {
            if (i > 0) {
                cookieHeader.append("; ");
            }
            Cookie cookie = cookies.get(i);
            cookieHeader.append(cookie.name()).append('=').append(cookie.value());
        }
        return cookieHeader.toString();
    }

    public void receiveHeaders(Request request, Headers headers) throws IOException {
        OkHttpClient client = new OkHttpClient();
        if (client.cookieJar() == CookieJar.NO_COOKIES) return;

        List<Cookie> cookies = Cookie.parseAll(request.url(), headers);
        if (cookies.isEmpty()) return;

        client.cookieJar().saveFromResponse(request.url(), cookies);
    }

}
