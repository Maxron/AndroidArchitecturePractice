package com.maxron.androidarchitecturepractice.model;

public class RepoItemModel {

    private final long id;
    private final String name;

    public RepoItemModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
