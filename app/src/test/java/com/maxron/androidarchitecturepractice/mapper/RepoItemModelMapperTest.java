package com.maxron.androidarchitecturepractice.mapper;

import com.maxron.androidarchitecturepractice.model.RepoItemModel;
import com.maxron.domain.model.RepoInfo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RepoItemModelMapperTest {

    private long id = 123456789L;
    private String name = "SampleName";
    private RepoInfo repoInfo;

    @Before
    public void setUp() throws Exception {
        repoInfo = new RepoInfo(id, name);
    }

    @Test
    public void shouldConvertRepoItemModelFromRepoInfo() {
        RepoItemModel repoItemModel = new RepoItemModelMapper().from(repoInfo);

        assertEquals(id, repoItemModel.getId());
        assertEquals(name, repoItemModel.getName());
    }
}