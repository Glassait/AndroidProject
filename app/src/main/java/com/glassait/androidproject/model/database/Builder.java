package com.glassait.androidproject.model.database;

import android.content.Context;

import androidx.room.Room;

public class Builder {
    private        AppDatabase mAppDatabase;
    private static Builder     sInstance;

    public static Builder getInstance() {
        if (sInstance == null) {
            sInstance = new Builder();
        }
        return sInstance;
    }

    public void buildDatabase(Context context) {
        String name = "Android Project";
        mAppDatabase = Room.databaseBuilder(
                                   context.getApplicationContext(),
                                   AppDatabase.class,
                                   name
                           )
                           .fallbackToDestructiveMigration()
                           .allowMainThreadQueries()
                           .build();
    }

    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }
}
