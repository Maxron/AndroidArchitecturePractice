package com.maxron.domain.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RepoInfoTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testRepoInfo() {
        RepoInfo repoInfo = new RepoInfo();
        Assert.assertEquals(RepoInfoTestData.NAME, repoInfo.name);
    }
}