package com.maxron.androidarchitecturepractice.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.maxron.androidarchitecturepractice.data.local.entity.RepoModel;

@Database(entities = {RepoModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "repo_database";
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract RepoDao repoDao();

}
