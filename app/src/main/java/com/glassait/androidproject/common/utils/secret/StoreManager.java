package com.glassait.androidproject.common.utils.secret;

import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

/**
 * This class is a singleton to store the live data for the application
 */
public class StoreManager {
    // Singleton instance
    private static StoreManager mInstance;
    // DataStore
    private static User         mUser;
    private static Offer        mOffer;

    private StoreManager() {}

    @SuppressWarnings("all")
    public static StoreManager getInstance() {
        if (mInstance == null) {
            mInstance = new StoreManager();
        }
        return mInstance;
    }

    public static User getUser() {
        return mUser;
    }

    public static void setUser(User user) {
        StoreManager.mUser = user;
    }

    public static Offer getOffer() {
        return mOffer;
    }

    public static void setOffer(Offer offer) {
        StoreManager.mOffer = offer;
    }
}
