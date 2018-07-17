package com.maxron.androidarchitecturepractice.data.mapper;

import com.maxron.androidarchitecturepractice.data.remote.model.RepoDetail;

public class RepoDetailTestData {

    public static final int id = 123282909;
    public static final String name = "ArchitectureSample";

    public RepoDetail stub() {
        RepoDetail repoDetail = new RepoDetail();
        repoDetail.setId(id);
        repoDetail.setName(name);
        return repoDetail;
    }
}
