package com.glassait.androidproject.common.utils.secret;

import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

/**
 * This class is a singleton to store the live data for the application
 */
public class StoreLocalData {
    // Singleton instance
    private static StoreLocalData mInstance;
    // DataStore
    private static User           mUser;
    private static Offer          mOffer;

    private StoreLocalData() {}

    @SuppressWarnings("all")
    public static StoreLocalData getInstance() {
        if (mInstance == null) {
            mInstance = new StoreLocalData();
        }
        return mInstance;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        StoreLocalData.mUser = user;
    }

    public Offer getOffer() {
        return mOffer;
    }

    public void setOffer(Offer offer) {
        StoreLocalData.mOffer = offer;
    }
}
