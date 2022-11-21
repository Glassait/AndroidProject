package com.glassait.androidproject.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.glassait.androidproject.model.dao.OfferDao;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

/**
 * This class defined the local database of the app
 * For each new {@link androidx.room.Entity} and DAO
 * Add the {@link androidx.room.Entity} inside the @Database
 * And the DAO in methode
 */
@Database(version = 1, entities = {User.class, Offer.class})
public abstract class AppDatabase extends RoomDatabase {
    // DAO for user table
    public abstract UserDao userDao();

    // DAO for offer table
    public abstract OfferDao offerDao();
}