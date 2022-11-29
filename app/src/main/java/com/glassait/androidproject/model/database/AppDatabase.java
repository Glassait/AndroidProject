package com.glassait.androidproject.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.glassait.androidproject.model.Converters;
import com.glassait.androidproject.model.dao.OfferDao;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

/**
 * This class defined the local database of the app.
 * <p>
 * For each new {@link androidx.room.Entity} and {@link androidx.room.Dao}.
 * <p>
 * Add the {@link androidx.room.Entity} inside the @Database and the DAO in methode
 * <p>
 * If there is some complex object to put inside the database use the {@link Converters} to
 * convert is in {@link String}
 */
@Database(version = 1, entities = {User.class, Offer.class})
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    // DAO for user table
    public abstract UserDao userDao();

    // DAO for offer table
    public abstract OfferDao offerDao();
}