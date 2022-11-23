package com.glassait.androidproject.common.utils;

import java.util.HashMap;
import java.util.Map;

public class LocalData {
    private static LocalData              instance;
    private final  Map<Integer, String[]> database;
    private        String                 scanningCode;

    private LocalData() {
        database = new HashMap<Integer, String[]>();
        database.put(
                65784,
                new String[]{"milk", "2.30", "cheese", "3.60"}
        );
        database.put(
                57648,
                new String[]{"banana", "2.60", "water", "1.20"}
        );
    }

    ;

    public static LocalData getInstance() {
        if (instance == null) {
            instance = new LocalData();
        }
        return instance;
    }

    public Map<Integer, String[]> getDatabase() {
        return database;
    }

    public void setScanningCode(String scanningCode) {
        this.scanningCode = scanningCode;
    }

    public String getScanningCode() {
        return scanningCode;
    }

}
