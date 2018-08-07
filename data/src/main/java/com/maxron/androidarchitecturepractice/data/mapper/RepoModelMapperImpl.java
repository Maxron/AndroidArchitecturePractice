package com.maxron.androidarchitecturepractice.data.mapper;

import com.maxron.androidarchitecturepractice.data.local.entity.RepoModel;
import com.maxron.androidarchitecturepractice.data.remote.model.RepoDetail;
import com.maxron.domain.model.RepoInfo;

public class RepoModelMapperImpl implements RepoModelMapper {
    @Override
    public RepoInfo repodetailToRepoInfo(RepoDetail repoDetail) {
        return new RepoInfo(new Long(repoDetail.getId()), repoDetail.getName());
    }

    @Override
    public RepoModel repodetailToRepoModel(RepoDetail repoDetail) {
        return new RepoModel(new Long(repoDetail.getId()), repoDetail.getName());
    }
}
