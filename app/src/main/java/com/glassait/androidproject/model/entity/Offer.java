package com.glassait.androidproject.model.entity;

import android.text.TextUtils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.glassait.androidproject.common.utils.checker.Checker;

/**
 * Class defining the offer of the application
 */
@Entity
public class Offer {
    /**
     * Constructor for the {@link Entity} Offer
     * <br><br>
     * The {@link PrimaryKey} is a uid and is auto generate.
     * <br><br>
     * The title is encode with {@link TextUtils#htmlEncode(String)}, more information
     * {@link Checker#encode(String)}
     * <p>
     * And trim with {@link String#trim} (Remove spaces before and after the string).
     *
     * @param title       The title of the offer
     * @param category    The category of the offer
     * @param storage_ref The storage reference for the firebase storage
     * @param creatorId   The id of the creator
     *
     * @see TextUtils#htmlEncode(String)
     */
    public Offer(String title, String category, String storage_ref, int creatorId) {
        this.title = TextUtils.htmlEncode(title.trim());
        this.storage_ref = storage_ref;
        this.category = category;
        this.creatorId = creatorId;
        this.isReserved = false;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "storage_ref")
    public String storage_ref;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "is_reserved")
    public boolean isReserved;

    @ColumnInfo(name = "creator_id")
    public int creatorId;
}
