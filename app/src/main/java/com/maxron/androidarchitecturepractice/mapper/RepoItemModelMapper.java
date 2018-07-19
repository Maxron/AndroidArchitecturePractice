package com.maxron.androidarchitecturepractice.mapper;

import com.maxron.androidarchitecturepractice.model.RepoItemModel;
import com.maxron.domain.model.RepoInfo;

public class RepoItemModelMapper {

    public RepoItemModel from(RepoInfo repoInfo) {
        return new RepoItemModel(repoInfo.id, repoInfo.name);
    }
}
