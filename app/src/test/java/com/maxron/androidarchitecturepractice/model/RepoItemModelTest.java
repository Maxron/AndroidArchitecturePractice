package com.maxron.androidarchitecturepractice.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RepoItemModelTest {

    private long id = 123456789L;
    private String name = "SampleRepository";
    private RepoItemModel model;

    @Before
    public void setUp() throws Exception {
        model = new RepoItemModel(id, name);
    }

    @Test
    public void getId() {
        assertEquals(id, model.getId());
    }

    @Test
    public void getName() {
        assertEquals(name, model.getName());
    }
}