package com.glassait.androidproject.common.utils.secret;

import com.glassait.androidproject.model.entity.User;

import java.util.HashMap;
import java.util.Map;

public class StoreLocalData {
    private static StoreLocalData         mInstance;
    private final  Map<Integer, String[]> mDatabase;
    private        String                 mScanningCode;
    // The user of the phone
    private static User                   mUser;

    private StoreLocalData() {
        mDatabase = new HashMap<Integer, String[]>();
        mDatabase.put(
                65784,
                new String[]{"milk", "2.30", "milk products", "cheese", "3.60", "milk products", "water", "1.20", "drinks", "banana", "2.60", "fruits & vegetables"}
        );
        mDatabase.put(
                57648,
                new String[]{"banana", "2.60", "fruits & vegetables", "water", "1.20", "drinks"}
        );
    }

    public static StoreLocalData getInstance() {
        if (mInstance == null) {
            mInstance = new StoreLocalData();
        }
        return mInstance;
    }

    public Map<Integer, String[]> getDatabase() {
        return mDatabase;
    }

    public void setScanningCode(String scanningCode) {
        this.mScanningCode = scanningCode;
    }

    public String getScanningCode() {
        return mScanningCode;
    }

    public String[] getPurchaseByLastSC() {
        int scanning = Integer.parseInt(this.mScanningCode);
        return mDatabase.get(scanning);
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
}
