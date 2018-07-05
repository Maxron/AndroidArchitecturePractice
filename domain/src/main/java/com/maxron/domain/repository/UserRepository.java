package com.maxron.domain.repository;

import com.maxron.domain.model.RepoInfo;

import java.util.List;

import io.reactivex.Single;

public interface UserRepository {

    Single<List<RepoInfo>> listUserRepository(String userName);
}
