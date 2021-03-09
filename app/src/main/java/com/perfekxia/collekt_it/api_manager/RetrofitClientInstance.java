package com.perfekxia.collekt_it.api_manager;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.perfekxia.collekt_it.preference.PrefManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    Context context;
    private PrefManager prefManager;
    private Retrofit retrofit;
    private String BASE_URL;
    public RetrofitClientInstance(Context context) {
        this.context = context;
        prefManager = new PrefManager(this.context);
        BASE_URL = prefManager.getServerAddress();
//        BASE_URL = prefManager.getServerAddress();
    }

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .enableComplexMapKeySerialization()
            .setLenient()
            .create();

    public Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
