package com.maxron.androidarchitecturepractice.data.remote;

import com.maxron.androidarchitecturepractice.data.remote.model.RepoDetail;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubServiceApi {

    @GET("users/{user}/repos")
    Single<List<RepoDetail>> listUserRepository(@Path("user") String user);
}
