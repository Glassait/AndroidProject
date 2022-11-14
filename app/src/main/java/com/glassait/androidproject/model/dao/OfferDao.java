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
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Interface defining all {@link Query} for the @Entity {@link Offer}
 * <br><br>
 * Some query are async query and return a {@link Completable} to handle it we need to transform the {@link Completable} in {@link Disposable} with {@link Completable#subscribe()} and after to dispose it with {@link Disposable#dispose()}
 */
@Dao
public interface OfferDao {
    /**
     * Return all {@link Offer} inside the table
     *
     * @return All {@link Offer} in the table
     */
    @Query("SELECT * FROM offer")
    List<Offer> getAll();

    /**
     * Query the {@link Offer} with the creator id
     *
     * @param creatorId The mail of the {@link User}
     *
     * @return The user with the mail, else throw the
     * {@link androidx.room.rxjava3.EmptyResultSetException} exception
     */
    @Query("SELECT * FROM offer WHERE creator_id = :creatorId")
    List<Offer> getAllOffersFromCreatorId(int creatorId);

    /**
     * Insert an {@link Offer} inside the table
     * <p>
     * This is a asynchronous one-shot queries: <a href="https://developer.android.com/training/data-storage/room/async-queries#one-shot">Room</a>
     *
     * @param offer The {@link Offer} to put inside the table
     *
     * @return The completable to subscribe
     *
     * @see <a href="https://developer.android.com/training/data-storage/room/async-queries#one-shot">Room</a>
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Offer offer);

    /**
     * Update the {@link Offer} in the table
     *
     * @param offer The {@link Offer} to update
     *
     * @return The completable to subscribe
     */
    @Update
    Completable update(Offer offer);

    /**
     * Delete the {@link Offer} inside the table
     * <p>
     * This is a asynchronous one-shot queries: <a href="https://developer.android.com/training/data-storage/room/async-queries#one-shot">Room</a>
     *
     * @param offer The {@link Offer} to delete inside the table
     *
     * @return The completable to subscribe
     *
     * @see <a href="https://developer.android.com/training/data-storage/room/async-queries#one-shot">Room</a>
     */
    @Delete
    Completable delete(Offer offer);
}