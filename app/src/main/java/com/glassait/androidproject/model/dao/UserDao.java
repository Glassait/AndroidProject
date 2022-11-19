package com.glassait.androidproject.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Interface defining all {@link Query} for the @Entity {@link User}
 * <br><br>
 * Some query are async query and return a {@link Completable} to handle it we need to transform the {@link Completable} in {@link Disposable} with {@link Completable#subscribe()} and after to dispose it with {@link Disposable#dispose()}
 */
@Dao
public interface UserDao {
    /**
     * Return all {@link User} inside the table
     *
     * @return All {@link User} in the table
     */
    @Query("SELECT * FROM user")
    List<User> getAll();

    /**
     * Query the {@link User} with the email
     *
     * @param email The mail of the {@link User}
     *
     * @return The user with the mail, else throw the
     * {@link androidx.room.rxjava3.EmptyResultSetException} exception
     */
    @Query("SELECT * FROM user WHERE email = :email")
    Single<User> getUserFromEmail(String email);

    /**
     * Insert an {@link User} inside the table
     * <p>
     * This is a asynchronous one-shot queries: <a href="https://developer.android.com/training/data-storage/room/async-queries#one-shot">Room</a>
     *
     * @param user The {@link User} to put inside the table
     *
     * @return The completable to subscribe
     *
     * @see <a href="https://developer.android.com/training/data-storage/room/async-queries#one-shot">Room</a>
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(User user);

    /**
     * Update the {@link Offer} in the table
     *
     * @param user The {@link User} to update
     *
     * @return The completable to subscribe
     */
    @Update
    Completable update(User user);

    /**
     * Delete the {@link User} inside the table
     * <p>
     * This is a asynchronous one-shot queries: <a href="https://developer.android.com/training/data-storage/room/async-queries#one-shot">Room</a>
     *
     * @param user The {@link User} to delete inside the table
     *
     * @return The completable to subscribe
     *
     * @see <a href="https://developer.android.com/training/data-storage/room/async-queries#one-shot">Room</a>
     */
    @Delete
    Completable delete(User user);

    /**
     * Delete all the {@link User} inside the table
     * <p>
     * This is a asynchronous one-shot queries: <a href="https://developer.android.com/training/data-storage/room/async-queries#one-shot">Room</a>
     *
     * @return The completable to subscribe
     *
     * @see <a href="https://developer.android.com/training/data-storage/room/async-queries#one-shot">Room</a>
     */
    @Query("DELETE FROM user")
    Completable deleteAll();
}
