package com.maxron.domain.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RepoInfoTest {

    private RepoInfo repoInfo;

    @Before
    public void setUp() throws Exception {
        repoInfo = new RepoInfo(RepoInfoTestData.ID, RepoInfoTestData.NAME);
    }

    @Test
    public void testRepoInfo() {
        Assert.assertEquals(RepoInfoTestData.NAME, repoInfo.name);
        Assert.assertEquals(RepoInfoTestData.ID, repoInfo.id);
        Assert.assertEquals(makeRepoInfoString(RepoInfoTestData.ID, RepoInfoTestData.NAME), repoInfo.toString());
    }

    private String makeRepoInfoString(long id, String name) {
        return String.format("RepoInfo{name='%s', id=%d}", name, id);
    }
}