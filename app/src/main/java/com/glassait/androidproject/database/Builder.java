package com.glassait.androidproject.database;

import android.content.Context;

import androidx.room.Room;

public class Builder {
    public AppDatabase buildDatabase(Context context) {
        String name = "Android Project";
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, name).allowMainThreadQueries().build();
    }
}
