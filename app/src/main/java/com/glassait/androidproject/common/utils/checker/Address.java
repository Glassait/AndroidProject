package com.glassait.androidproject.common.utils.checker;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.glassait.androidproject.model.Converters;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.io.IOException;
import java.util.List;

/**
 * Class to define the address of the user
 */
public class Address {
    private Context  mContext;
    private String   mStreet;
    private String   mPostalCode;
    private String   mCity;
    private String   mCountry;
    private Location mLocation;

    /**
     * Constructor with all string values.
     * <p>
     * When constructing the class, the location is get with {@link Geocoder}.
     *
     * @param mContext   The context of the application
     * @param street     The street of the user
     * @param postalCode The postal code of the user
     * @param city       The city of the user
     * @param country    The country of the user
     *
     * @see #getLocationFromAddress()
     */
    @SafeParcelable.Constructor
    public Address(Context mContext, String street, String postalCode, String city,
                   String country) {
        this.mContext = mContext;
        this.mStreet = TextUtils.htmlEncode(street.trim());
        this.mPostalCode = TextUtils.htmlEncode(postalCode.trim());
        this.mCity = TextUtils.htmlEncode(city.trim());
        this.mCountry = TextUtils.htmlEncode(country.trim());
        getLocationFromAddress();
    }

    /**
     * Constructor to handle EditText.
     * All EditText are transform in string and pass to {@link Address#Address(Context, String, String, String, String)}
     *
     * @param context    The context of the application
     * @param street     The editText of the street
     * @param postalCode The editText of the postal code
     * @param city       The editText of the city
     * @param country    The editText of the country
     */
    @SafeParcelable.Constructor
    public Address(@NonNull Context context, @NonNull EditText street, @NonNull EditText postalCode,
                   @NonNull EditText city, @NonNull EditText country) {
        this(
                context,
                street.getText()
                      .toString(),
                postalCode.getText()
                          .toString(),
                city.getText()
                    .toString(),
                country.getText()
                       .toString()
        );
    }

    /**
     * Constructor for the Room Converter
     * <p>
     * The location is not get with this constructor because the {@link Converters} don't have
     * some context
     *
     * @param street     The street of the user
     * @param postalCode The postal code of the user
     * @param city       The city of the user
     * @param country    The country of the user
     *
     * @see Converters
     */
    @SafeParcelable.Constructor
    public Address(String street, String postalCode, String city, String country) {
        this.mStreet = TextUtils.htmlEncode(street.trim());
        this.mPostalCode = TextUtils.htmlEncode(postalCode.trim());
        this.mCity = TextUtils.htmlEncode(city.trim());
        this.mCountry = TextUtils.htmlEncode(country.trim());
    }

    /**
     * Transform a string of the address to a {@link Location} object
     * <p>
     * Code take from <a href="https://stackoverflow.com/a/42626972">Stack Overflow</a>
     * <p>
     * Some modification have be done by me on the code
     */
    private void getLocationFromAddress() {
        Geocoder                       coder = new Geocoder(mContext);
        List<android.location.Address> addressList;

        try {
            addressList = coder.getFromLocationName(
                    mStreet + " " + mPostalCode + " " + mCity + " " + mCountry,
                    1
            );
            if (addressList == null || addressList.size() == 0) {
                return;
            }

            android.location.Address address = addressList.get(0);

            mLocation = new Location("");
            mLocation.setLatitude(address.getLatitude());
            mLocation.setLongitude(address.getLongitude());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return The location of the address of the user
     */
    public Location getLocation() {
        if (mLocation == null) {
            getLocationFromAddress();
        }
        return mLocation;
    }

    /**
     * @param context The context of the application
     *
     * @return The location of the address of the user
     */
    public Location getLocation(Context context) {
        mContext = context;
        return getLocation();
    }

    /**
     * @return The street of the user
     */
    public String getStreet() {
        return mStreet;
    }

    /**
     * @param street The street of the user
     */
    public void setStreet(String street) {
        this.mStreet = street;
        getLocationFromAddress();
    }

    /**
     * @return The postal code of the user
     */
    public String getPostalCode() {
        return mPostalCode;
    }

    /**
     * @param postalCode The postalCode of the user
     */
    public void setPostalCode(String postalCode) {
        this.mPostalCode = postalCode;
        getLocationFromAddress();
    }

    /**
     * @return The city of the user
     */
    public String getCity() {
        return mCity;
    }

    /**
     * @param city The city of the user
     */
    public void setCity(String city) {
        this.mCity = city;
        getLocationFromAddress();
    }

    /**
     * @return The country of the user
     */
    public String getCountry() {
        return mCountry;
    }

    /**
     * @param country The country of the user
     */
    public void setCountry(String country) {
        this.mCountry = country;
        getLocationFromAddress();
    }
}
