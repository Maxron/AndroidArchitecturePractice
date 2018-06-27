package com.maxron.data.remote;

import com.maxron.data.remote.model.Repo;

import io.reactivex.Single;

public interface GitHubService {

    Single<Repo> listRepost(String user);
}
