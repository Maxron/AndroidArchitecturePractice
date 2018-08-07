package com.maxron.androidarchitecturepractice.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "repo_table")
public class RepoModel {

    @PrimaryKey
    @NonNull
    private long id;

    private String name;

    public RepoModel(@NonNull long id, String name) {
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
