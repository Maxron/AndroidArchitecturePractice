package com.maxron.androidarchitecturepractice;

import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientProvider {

    private final String baseUrl;
    private boolean withLogger;

    public ApiClientProvider(@NonNull String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private ApiClientProvider(String baseUrl, Builder builder) {
        this.baseUrl = baseUrl;
        withLogger = builder.withLogger;
    }

    public Retrofit provide() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        if (withLogger) {
            builder.client(getClient());
        }

        return builder.build();
    }

    private OkHttpClient getClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return  new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
    }

    public static final class Builder {
        private String baseUrl;
        private boolean withLogger;

        public Builder(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public Builder withLogger() {
            withLogger = true;
            return this;
        }

        public ApiClientProvider build() {
            return new ApiClientProvider(baseUrl, this);
        }
    }
}
