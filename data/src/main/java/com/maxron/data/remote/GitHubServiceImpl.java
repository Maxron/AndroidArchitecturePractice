package com.maxron.data.remote;

import com.maxron.data.remote.model.Repo;

import io.reactivex.Single;

public class GitHubServiceImpl implements GitHubService {

    final GitHubServiceApi serviceApi;

    public GitHubServiceImpl(GitHubServiceApi serviceApi) {
        this.serviceApi = serviceApi;
    }


    @Override
    public Single<Repo> listRepost(String user) {
        return null;
    }
}
