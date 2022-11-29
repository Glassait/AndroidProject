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
    // The user of the phone
    private static User           mUser;
    // The offer clicked
    private static Offer          mOffer;
    // The see all clicked
    private static String         mSeeAll;

    private StoreLocalData() {}

    @SuppressWarnings("all")
    public static StoreLocalData getInstance() {
        if (mInstance == null) {
            mInstance = new StoreLocalData();
        }
        return mInstance;
    }

    /**
     * @return The user of the phone
     */
    public User getUser() {
        return mUser;
    }

    /**
     * @param user The user of the phone
     */
    public void setUser(User user) {
        StoreLocalData.mUser = user;
    }

    /**
     * @return The offer clicked
     */
    public Offer getOffer() {
        return mOffer;
    }

    /**
     * @param offer The offer clicked
     */
    public void setOffer(Offer offer) {
        StoreLocalData.mOffer = offer;
    }

    /**
     * @return The see all clicked
     */
    public String getSeeAll() {
        return mSeeAll;
    }

    /**
     * @param seeAll The see all clicked
     */
    public void setSeeAll(String seeAll) {
        StoreLocalData.mSeeAll = seeAll;
    }
}
