package com.maxron.androidarchitecturepractice.data.remote;

import com.maxron.domain.model.RepoInfo;

import java.util.List;

import io.reactivex.Single;

public interface GithubServiceApi {
    Single<List<RepoInfo>> listUserRepository(String user);
}
