package com.maxron.androidarchitecturepractice.data;

import com.maxron.androidarchitecturepractice.data.remote.GithubServiceApi;
import com.maxron.domain.model.RepoInfo;
import com.maxron.domain.repository.UserRepository;

import java.util.List;

import io.reactivex.Single;

public class UserRepositoryImpl implements UserRepository {

    private final GithubServiceApi githubService;

    public UserRepositoryImpl(GithubServiceApi githubService) {
        this.githubService = githubService;
    }

    @Override
    public Single<List<RepoInfo>> listUserRepository(String userName) {
        return this.githubService.listUserRepository(userName);
    }
}
