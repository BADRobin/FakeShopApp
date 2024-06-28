package com.oleh.fakeshopapp;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpService implements NetworkService{

    private final OkHttpClient client = new OkHttpClient();
    @Override
    public String getJSON(String url) {

        Request request= new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
