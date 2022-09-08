package com.glassait.androidproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.glassait.androidproject.entity.User;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Interface defining all {@link Query} for the @Entity {@link User}
 *
 * Some query are async query and return a {@link Completable} to handle it we need to transform the
 * {@link Completable} in {@link Disposable} with {@link Completable#subscribe()} and after to dispose
 * it with {@link Disposable#dispose()}
 */
@Dao
public interface UserDao {
    /**
     * Return all data of all users inside the table
     *
     * @return List<User>
     */
    @Query("SELECT * FROM user")
    List<User> getAll();


    /**
     * Query the {@link User} with the uid
     *
     * @param uid int - The uid of the {@link User}
     * @return Single<User>
     */
    @Query("SELECT * FROM user WHERE uid = :uid")
    Single<User> getUserFromUID(int uid);

    /**
     * Insert an user inside the table
     * This is a asynchronous one-shot queries: https://developer.android.com/training/data-storage/room/async-queries#one-shot
     *
     * @param user {@link User} - The user to put inside the table
     * @return Completable
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(User user);

    /**
     * @param user {@link User} The user to update
     * @return Completable
     */
    @Update
    Completable update(User user);

    /**
     * Delete an user inside the table
     * This is a asynchronous one-shot queries: https://developer.android.com/training/data-storage/room/async-queries#one-shot
     *
     * @param user {@link User} - The user to delete inside the table
     * @return Completable
     */
    @Delete
    Completable delete(User user);
}
