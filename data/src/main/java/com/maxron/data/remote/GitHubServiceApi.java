package com.maxron.data.remote;

import com.maxron.data.remote.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubServiceApi {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepo(
            @Path("user") String user
    );
}
