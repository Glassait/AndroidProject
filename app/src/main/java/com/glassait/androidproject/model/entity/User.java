package com.glassait.androidproject.model.entity;

import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.glassait.androidproject.common.utils.checker.Address;
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
     * @param uuid      The uuid of the user
     *
     * @see TextUtils#htmlEncode(String)
     */
    @SafeParcelable.Constructor
    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String email,
                @NonNull String phone, @NonNull Address address, UUID uuid) {
        this.firstName = TextUtils.htmlEncode(firstName.trim());
        this.lastName = TextUtils.htmlEncode(lastName.trim());
        this.email = TextUtils.htmlEncode(email.trim());
        this.phone = TextUtils.htmlEncode(phone.trim());
        this.address = address;
        this.uuid = uuid;
    }

    /**
     * Constructor for {@link EditText}
     *
     * @param firstName The editText of the first name
     * @param lastName  The editText of the last name
     * @param email     The email
     * @param phone     The editText of the phone
     * @param address   The address of the user
     * @param uuid      The {@link UUID}
     *
     * @see User#User(String, String, String, String, Address, UUID)
     */
    public User(EditText firstName, EditText lastName, String email, EditText phone,
                Address address, UUID uuid) {
        this(
                firstName.getText()
                         .toString(),
                lastName.getText()
                        .toString(),
                email,
                phone.getText()
                     .toString(),
                address,
                uuid
        );
    }

    // The id of the user, auto-generated
    @PrimaryKey(autoGenerate = true)
    public int uid;

    // The first name of the user
    @ColumnInfo(name = "first_name")
    public String firstName;

    // The last name of the user
    @ColumnInfo(name = "last_name")
    public String lastName;

    // The email of the user
    @ColumnInfo(name = "email")
    public String email;

    // The phone of the user
    @ColumnInfo(name = "phone")
    public String phone;

    // The address of the user
    @ColumnInfo(name = "address")
    public Address address;

    // The uuid password of the user
    @ColumnInfo(name = "uuid")
    public UUID uuid;
}
