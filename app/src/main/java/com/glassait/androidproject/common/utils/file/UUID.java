package com.glassait.androidproject.common.utils.file;

import android.content.Context;

/**
 * UUID is a unique solution to replace passwords.
 * <p>
 * The idea is when the user create an account on it's phone the application will create a UUID
 * and store it on the phone.
 * <p>
 * During the connection process the app will check if there is some uuid related to the account.
 * <p>
 * An UUID is a <strong>Universally unique identifier</strong> with 128-bit value generated using
 * crypto generator. For more information check {@link java.util.UUID} and {@link java.util.UUID#randomUUID()}
 */
public class UUID extends Cache {
    private java.util.UUID mUUID;

    /**
     * @param context The context is use to create and delete file and get the directory
     * @param email   The mail address of the user, use for the file name.
     */
    public UUID(Context context, String email) {
        super(
                email + "_uuid",
                context
        );
    }

    /**
     * @return The uuid store in the class
     */
    public java.util.UUID getUuid() {
        return mUUID;
    }

    /**
     * @param uuid The UUID of the user
     */
    public void setUUID(java.util.UUID uuid) {
        this.mUUID = uuid;
    }

    /**
     * Generate a unique UUID and store it in the UUID field
     *
     * @see java.util.UUID#randomUUID()
     */
    public void generateUUID() {
        mUUID = java.util.UUID.randomUUID();
    }

    /**
     * Store the generated UUID on a file in the phone.
     * <p>
     * If no UUID was generated then create one.
     *
     * @return True if the file was successfully created and the UUID correctly store; else false.
     *
     * @see Cache#createFile()
     * @see #generateUUID()
     * @see Cache#storeDataInFile(String)
     */
    public boolean storeUUID() {
        createFile();
        if (mUUID == null) generateUUID();
        return storeDataInFile(mUUID.toString());
    }

    /**
     * Read the UUID file and get the UUID store and return it.
     * <p>
     * If the file is not found return null.
     * <p>
     * If something append during the process of the uuid string then return null
     *
     * @return The uuid if the file is found and the string correctly build, otherwise return null.
     *
     * @see Cache#readDataFromFile()
     * @see java.util.UUID#fromString(String)
     */
    public java.util.UUID readUUIDFile() {
        String data = readDataFromFile();
        return data != null ? java.util.UUID.fromString(data) : null;
    }

    /**
     * Delete the uuid store file
     *
     * @return True if the file is correctly deleted, else false
     *
     * @see Cache#deleteFile()
     */
    public boolean deleteUUIDFile() {
        return deleteFile();
    }
}