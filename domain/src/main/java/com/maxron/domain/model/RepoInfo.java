package com.maxron.domain.model;

public class RepoInfo {
    public final String name;
    public final Long id;

    public RepoInfo(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "RepoInfo{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
