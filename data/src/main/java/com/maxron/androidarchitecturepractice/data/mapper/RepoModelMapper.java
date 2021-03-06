package com.maxron.androidarchitecturepractice.data.mapper;

import com.maxron.androidarchitecturepractice.data.local.entity.RepoModel;
import com.maxron.androidarchitecturepractice.data.remote.model.RepoDetail;
import com.maxron.domain.model.RepoInfo;

public interface RepoModelMapper {

    RepoInfo repodetailToRepoInfo(RepoDetail repoDetail);
    RepoModel repodetailToRepoModel(RepoDetail repoDetail);
}
