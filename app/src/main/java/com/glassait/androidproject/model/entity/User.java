package com.glassait.androidproject.model.entity;

import android.text.TextUtils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class defining the user of the app
 */
@Entity
public class User {
    /**
     * Constructor for the {@link Entity} User
     * <br><br>
     * The {@link PrimaryKey} is a uid and is auto generate.
     * Inside the constructor the uid is check to not be less than 0
     * <br><br>
     * All other fields are encode with {@link TextUtils#htmlEncode(String)}
     * And trim with {@link String#trim}
     *
     * @param firstName String
     * @param lastName  String
     * @param email     String
     * @param phone     String
     * @param address   String
     * @param city      String
     * @param country   String
     * @param password  String
     *
     * @see TextUtils#htmlEncode(String)
     */
    public User(String firstName, String lastName, String email, String phone, String address,
                String city, String country, String password) {
        uid = uid <= 0 ? 1 : uid;
        this.firstName = TextUtils.htmlEncode(firstName.trim());
        this.lastName = TextUtils.htmlEncode(lastName.trim());
        this.email = TextUtils.htmlEncode(email.trim());
        this.phone = TextUtils.htmlEncode(phone.trim());
        this.address = TextUtils.htmlEncode(address.trim());
        this.city = TextUtils.htmlEncode(city.trim());
        this.country = TextUtils.htmlEncode(country.trim());
        this.password = TextUtils.htmlEncode(password.trim());
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

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "password")
    public String password;
}
