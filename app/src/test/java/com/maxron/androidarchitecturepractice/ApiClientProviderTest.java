package com.maxron.androidarchitecturepractice;

import org.junit.Test;

import retrofit2.Retrofit;

import static junit.framework.Assert.assertEquals;

public class ApiClientProviderTest {

    private String BASE_URL = "http://example.com/";

    @Test
    public void shouldProvideRetrofit() {
        ApiClientProvider clientProvider = new ApiClientProvider(BASE_URL);
        Retrofit retrofit = clientProvider.provide();

        assertEquals(BASE_URL, retrofit.baseUrl().toString());
    }

    @Test
    public void shouldProvideRetrofitByBuilder() {
        ApiClientProvider clientProvider =
                new ApiClientProvider.Builder(BASE_URL)
                        .withLogger()
                        .build();
        Retrofit retrofit = clientProvider.provide();

        assertEquals(BASE_URL, retrofit.baseUrl().toString());
    }
}