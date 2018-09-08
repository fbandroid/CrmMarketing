package com.crmmarketing.hmt.webservice;


import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class WSLogin {

    private Context context;
    private String url;
    private String username;
    private String password;

    private String response = null;
    private FormBody formBody;

    private OkHttpClient okHttpClient;
    private HttpUrl httpUrl;
    private long timeOut = 10000;

    private String appKey;
    private String cityName;

    public WSLogin(Context context, String url, String username, String password) {
        this.context = context;
        this.url = url;
        this.password = password;
        this.username = username;
        okHttpClient = new OkHttpClient.Builder().connectTimeout(timeOut, TimeUnit.MILLISECONDS).readTimeout(timeOut, TimeUnit.MILLISECONDS).build();
    }


    public WSLogin() {
        okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                System.out.println("Authenticating for response: " + response);
                System.out.println("Challenges: " + response.challenges());
                String credential = Credentials.basic("root", "xyz");

                return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
            }
        })
                .connectTimeout(timeOut, TimeUnit.MILLISECONDS).readTimeout(timeOut, TimeUnit.MILLISECONDS).build();

//        this.context=context;
//        this.appKey=appKey;
//        this.cityName=cityName;


    }

    public String postLoginData(String url, String json) throws IOException {

//        formBody=new FormBody.Builder().add("action", "login")
//                .add("format", "json")
//                .add("username", username)
//                .add("password", password)
//                .build();

//        httpUrl=new HttpUrl.Builder().
//                 scheme("https") //http
//                .host("www.somehostname.com")
//                .addPathSegment("pathSegment")//adds "/pathSegment" at the end of hostname
//                .addQueryParameter("param1", "value1") //add query parameters to the URL
//                .addEncodedQueryParameter("encodedName", "encodedValue")//add encoded query parameters to the URL
//                .build();

        return ApiCall.POSTBODY(okHttpClient, url, json);
    }

    public String postDummyData() throws IOException {
//        formBody=new FormBody.Builder()
//                .add("format", "xml")
//                .add("username", username)
//                .add("password", password)
//                .build();

        httpUrl = new HttpUrl.Builder().
                scheme("http") //http
                .host("api.openweathermap.org")
                .addPathSegment("data/2.5/weather")//adds "/pathSegment" at the end of hostname
                .addQueryParameter("q", "London")
                //add query parameters to the URL
                // .addEncodedQueryParameter("encodedName", "encodedValue")//add encoded query parameters to the URL
                .addQueryParameter("appid", "0eafdd080b34d0efb495161e39e895c2")
                .addQueryParameter("mode", "xml")
                .build();

        return getData();
    }

    public String getData() throws IOException {

        return ApiCall.GET(okHttpClient, httpUrl);

    }


    // post form body data

    public String postFormBody(String username, String password,String bid,String root, String url) throws IOException, IllegalArgumentException {

        RequestBody formBody = new FormBody.Builder()
                .add("email", username)
                .add("password", password)
                .add("b_id",bid)
                .add("root",root)
                .build();

        return ApiCall.POSTFORMBODY(okHttpClient, formBody, url);
    }

}



