package com.maxron.androidarchitecturepractice.data.mapper;

import com.maxron.androidarchitecturepractice.data.remote.model.RepoDetail;
import com.maxron.domain.model.RepoInfo;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class RepoModelMapperImplTest {

    private RepoModelMapperImpl mapper;
    private RepoDetail repoDetail;

    @Before
    public void setUp() throws Exception {
        mapper = new RepoModelMapperImpl();
        repoDetail = new RepoDetailTestData().stub();
    }

    @Test
    public void repodetailToRepoInfo() {
        RepoInfo repoInfo = mapper.repodetailToRepoInfo(repoDetail);

        assertEquals(repoDetail.getId(), repoInfo.id.intValue());
        assertEquals(repoDetail.getName(), repoInfo.name);
    }
}