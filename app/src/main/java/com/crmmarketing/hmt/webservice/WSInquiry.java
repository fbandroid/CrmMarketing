package com.crmmarketing.hmt.webservice;

import android.content.Context;

import com.crmmarketing.hmt.R;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;



public class WSInquiry {
    private Context context;
    private String url;


    private String response = null;
    private FormBody formBody;

    private OkHttpClient okHttpClient;
    private HttpUrl httpUrl;
    private long timeOut = 5000;


    public WSInquiry() {

        okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).connectTimeout(timeOut, TimeUnit.MILLISECONDS).readTimeout(timeOut, TimeUnit.MILLISECONDS).build();


    }

    public String getInquiry(String url) throws IOException {


        HttpUrl httpUrl = HttpUrl.parse(url);
        return ApiCall.GET(okHttpClient, httpUrl);
    }


    public String getAllPostInquuiry(String url, String id,String role,String bid) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("id", id)
                .add("role",role)
                .add("branch",bid)
                .build();

        return ApiCall.POSTFORMBODY(okHttpClient, formBody, url);

    }


    public String getFollowUpInquiry(String url, String id,String role,String bid) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("id", id)
                .add("role",role)
                .add("branch",bid)
                .build();

        return ApiCall.POSTFORMBODY(okHttpClient, formBody, url);
    }

    public String getInQBYDate(String url, String id,String role,String caseOf,String filter,String branch) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("id", id)
                .add("case",caseOf)
                .add("role",role)
                .add("filter",filter)
                .add("branch",branch)
                .build();

        return ApiCall.POSTFORMBODY(okHttpClient, formBody, url);
    }


    public String getTargetList(String url, String id) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("id", id)
                .build();

        return ApiCall.POSTFORMBODY(okHttpClient, formBody, url);

    }


    public String getStatistic(String url,String startdate,String enddate,String prdId,String empId) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("id", empId)
                .add("p_id",prdId)
                .add("start",startdate)
                .add("end",enddate)
                .build();

        return ApiCall.POSTFORMBODY(okHttpClient, formBody, url);
    }


    public String getEmpList(String url, String id,String root) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("id", id)
                .add("root",root)
                .build();

        return ApiCall.POSTFORMBODY(okHttpClient, formBody, url);

    }

    public String getPrList(String id,String root,String url) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("id", id)
                .add("root",root)
                .build();

        return ApiCall.POSTFORMBODY(okHttpClient, formBody, url);
    }

}
