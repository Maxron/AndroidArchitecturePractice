package com.maxron.androidarchitecturepractice.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.maxron.androidarchitecturepractice.data.local.entity.RepoModel;

import java.util.List;

@Dao
public interface RepoDao {

    @Insert
    void insert(RepoModel repoModel);

    @Query("SELECT * FROM repo_table ORDER BY id ASC")
    List<RepoModel> getAllRepos();
}
