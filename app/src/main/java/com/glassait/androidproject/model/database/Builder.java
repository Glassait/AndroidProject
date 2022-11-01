package com.glassait.androidproject.model.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Builder class is used to build the room database
 * <p>
 * This class in a singleton
 */
public class Builder {
    private        AppDatabase mAppDatabase;
    private static Builder     sInstance;

    /**
     * If the class is not initialized then initialize it.
     *
     * @return The builder class
     */
    public static Builder getInstance() {
        if (sInstance == null) {
            sInstance = new Builder();
        }
        return sInstance;
    }

    /**
     * Build the database if not already build.
     * <p><br>
     * To simplify the management of the database, the build first recreate the database tables.
     * More information: {@link RoomDatabase.Builder#fallbackToDestructiveMigration()}
     * <p><br>
     * The allowMainThreadQueries is used for allow using query in the main program, if not put
     * the application crash
     *
     * @param context The context of the database builder
     *
     * @see Room#databaseBuilder(Context, Class, String)
     * @see RoomDatabase.Builder#fallbackToDestructiveMigration()
     * @see RoomDatabase.Builder#build(
     */
    public void buildDatabase(Context context) {
        if (mAppDatabase == null) {
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
    }

    /**
     * @return The database
     */
    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }
}
