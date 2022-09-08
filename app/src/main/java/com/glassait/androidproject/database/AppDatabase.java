package com.glassait.androidproject.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.glassait.androidproject.dao.UserDao;
import com.glassait.androidproject.entity.User;

/**
 * This class defined the local database of the app
 * For each new {@link androidx.room.Entity} and DAO
 * Add the {@link androidx.room.Entity} inside the @Database
 * And the DAO in methode
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // DAO for user table
    public abstract UserDao userDao();
}
