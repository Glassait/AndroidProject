package com.glassait.androidproject.model;

import android.location.Location;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.glassait.androidproject.common.utils.checker.Address;
import com.glassait.androidproject.common.utils.secret.Secret;

/**
 * Class used to convert complex object for the Room database
 * <p>
 * Converters used for {@link Address}, {@link Location}
 */
@ProvidedTypeConverter
@SuppressWarnings("all")
public class Converters {
    @TypeConverter
    public static String fromAddressToString(Address address) {
        return address.getStreet() + Secret.SEPARATOR + address.getPostalCode() + Secret.SEPARATOR
                + address.getCity() + Secret.SEPARATOR + address.getCountry();
    }

    @TypeConverter
    public static Address fromStringToAddress(String string) {
        String[] stringList = string.split(Secret.SEPARATOR);
        return new Address(
                stringList[0],
                stringList[1],
                stringList[2],
                stringList[3]
        );
    }

    @TypeConverter
    public static String fromLocationToString(Location location) {
        return location.getLatitude() + Secret.SEPARATOR + location.getLongitude();
    }

    @TypeConverter
    public static Location fromStringToLocation(String string) {
        String[] stringList = string.split(Secret.SEPARATOR);
        Location location   = new Location("");
        location.setLatitude(Double.parseDouble(stringList[0]));
        location.setLongitude(Double.parseDouble(stringList[1]));
        return location;
    }
}