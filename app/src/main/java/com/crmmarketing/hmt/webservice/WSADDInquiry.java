package com.crmmarketing.hmt.webservice;

import android.content.Context;

import com.crmmarketing.hmt.R;

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

/**
 * Created by USER on 13-02-2017.
 */

public class WSADDInquiry {


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

    public WSADDInquiry(Context context) {
        this.context = context;

        okHttpClient = new OkHttpClient.Builder().connectTimeout(timeOut, TimeUnit.MILLISECONDS).readTimeout(timeOut, TimeUnit.MILLISECONDS).build();
    }


    public String postInquiry(String json) throws IOException {


        return ApiCall.POSTBODY(okHttpClient, context.getString(R.string.inq_insert_url), json);
    }

    public String editInquiry(String json) throws IOException {


        return ApiCall.POSTBODY(okHttpClient, context.getString(R.string.edit_inq), json);
    }


    public String addReamrk(String json,String url) throws IOException {


        return ApiCall.POSTBODY(okHttpClient, url, json);
    }

}
