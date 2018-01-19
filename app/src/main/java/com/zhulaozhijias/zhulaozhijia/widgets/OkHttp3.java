package com.zhulaozhijias.zhulaozhijia.widgets;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by D&LL on 2017/5/5.
 */

public class OkHttp3 {
    private static OkHttpClient client = new OkHttpClient();

    public static OkHttpClient getClient() {
        return client;
    }
}
