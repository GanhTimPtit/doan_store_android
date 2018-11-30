package com.ptit.store.services;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class GoogleMapClient {
    private static Retrofit instance;

    public static Retrofit getClient(){
        if(instance == null) {
            instance = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://maps.googleapis.com")
                    .build();
        }
        return instance;
    }
}
