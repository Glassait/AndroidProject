package com.glassait.androidproject.model.entity;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.glassait.androidproject.common.utils.checker.Checker;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.UUID;

/**
 * Class defining the user of the app
 */
@Entity
public class User {
    /**
     * Constructor for the {@link Entity} User
     * <br><br>
     * The {@link PrimaryKey} is a uid and is auto generate.
     * <br><br>
     * All other fields are encode with {@link TextUtils#htmlEncode(String)}, more information
     * {@link Checker#encode(String)}
     * <p>
     * And trim with {@link String#trim} (Remove spaces before and after the string).
     *
     * @param firstName The first name of the user
     * @param lastName  The last name of the user
     * @param email     The mail address of the user
     * @param phone     The phone number of the user
     * @param address   The postal address of the user
     * @param postCode  The postal code of the user
     * @param city      The city of the user
     * @param country   The country of the user
     * @param uuid      The uuid of the user
     *
     * @see TextUtils#htmlEncode(String)
     */
    @SafeParcelable.Constructor
    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String email,
                @NonNull String phone, @NonNull String address, @NonNull String postCode,
                @NonNull String city, @NonNull String country, UUID uuid) {
        this.firstName = TextUtils.htmlEncode(firstName.trim());
        this.lastName = TextUtils.htmlEncode(lastName.trim());
        this.email = TextUtils.htmlEncode(email.trim());
        this.phone = TextUtils.htmlEncode(phone.trim());
        this.address = TextUtils.htmlEncode(address.trim());
        this.postCode = TextUtils.htmlEncode(postCode.trim());
        this.city = TextUtils.htmlEncode(city.trim());
        this.country = TextUtils.htmlEncode(country.trim());
        this.uuid = uuid;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "post_code")
    public String postCode;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "uuid")
    public UUID uuid;
}
