package com.crmmarketing.hmt.webservice;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.crmmarketing.hmt.cons.Constants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class ApiCall {

    //GET network request
    static String GET(OkHttpClient client, HttpUrl url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    //POST network request
    static String POST(OkHttpClient client, HttpUrl url, RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body().string();
    }

    //post json body

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    static String POSTBODY(OkHttpClient okHttpClient, String url, String json) throws IOException {

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("root", Constants.root)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }


    //post parameter in url
    static String POSTFORMBODY(OkHttpClient okHttpClient, RequestBody formBody, String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();


        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body().string();


    }


    static String POSTMULTIPARTBODY(OkHttpClient okHttpClient, RequestBody requestBody, String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body().toString();

    }
}