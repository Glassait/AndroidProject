package com.glassait.androidproject.model.entity;

import android.location.Location;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.glassait.androidproject.common.utils.checker.Checker;
import com.google.android.material.chip.Chip;

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
     * @param location    The location of the offer
     * @param creatorId   The id of the creator
     *
     * @see TextUtils#htmlEncode(String)
     */
    public Offer(String title, String category, String storage_ref, Location location,
                 int creatorId) {
        this.title = TextUtils.htmlEncode(title.trim());
        this.storage_ref = storage_ref;
        this.category = category;
        this.location = location;
        this.creatorId = creatorId;
        isReserved = false;
        reservedBy = -1;
        takenDate = null;
        returnDate = null;
    }

    public Offer(EditText title, Chip category, String storage_ref, Location location,
                 int creatorId) {
        this(
                title.getText()
                     .toString(),
                category.getText()
                        .toString(),
                storage_ref,
                location,
                creatorId
        );
    }

    // The uid of the offer, auto-generated
    @PrimaryKey(autoGenerate = true)
    public int uid;

    // The title of the offer
    @ColumnInfo(name = "title")
    public String title;

    // The storage reference of the offer, (used for firebase). Not used
    @ColumnInfo(name = "storage_ref")
    public String storage_ref;

    // The category of the offer
    @ColumnInfo(name = "category")
    public String category;

    // The location of the offer
    @ColumnInfo(name = "location")
    public Location location;

    // If the offer is reserved of not
    @ColumnInfo(name = "is_reserved")
    public boolean isReserved;

    // The id of the user who reserved the offer
    @ColumnInfo(name = "reserved_by")
    public int reservedBy;

    // The id of the user who create the offer
    @ColumnInfo(name = "creator_id")
    public int creatorId;

    // The date of the start of the reservation
    @ColumnInfo(name = "taken_date")
    public String takenDate;

    // The date of the end of the reservation
    @ColumnInfo(name = "return_date")
    public String returnDate;
}
