package com.glassait.androidproject.common.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
public class UUID {
    private       java.util.UUID mUuid;
    private final String         mFileName;
    private final Context        mContext;

    /**
     * @param context The context is use to create and delete file and get the directory
     * @param email   The mail address of the user, use for the file name.
     */
    public UUID(Context context, String email) {
        mFileName = email + "_uuid";
        mContext = context;
    }

    /**
     * @return The uuid store in the class
     */
    public java.util.UUID getUuid() {
        return mUuid;
    }

    /**
     * @return The file name for the UUID
     */
    public String getFileName() {
        return mFileName;
    }

    /**
     * Generate a unique UUID and store it in the UUID field
     *
     * @see java.util.UUID#randomUUID()
     */
    public void generateUUID() {
        mUuid = java.util.UUID.randomUUID();
    }

    /**
     * Store the generated UUID on a file in the phone.
     * <p>
     * If no UUID was generated then create one.
     * <p><br>
     * Code come from : <a href="https://developer.android.com/training/data-storage/app-specific#internal-store-stream">Android Developers</a>
     *
     * @return True if the file was successfully created and the UUID correctly store; else false.
     *
     * @see File
     * @see FileOutputStream
     * @see FileOutputStream#write(byte[])
     * @see Context#openFileOutput(String, int)
     * @see #generateUUID()
     * @see <a href="https://developer.android.com/training/data-storage/app-specific#internal-store-stream">Android Developers</a>
     */
    public boolean storeUUIDInFile() {
        new File(
                mContext.getFilesDir(),
                mFileName
        );

        try (FileOutputStream fileOutputStream = mContext.openFileOutput(
                mFileName,
                Context.MODE_PRIVATE
        )) {
            if (mUuid == null) generateUUID();

            fileOutputStream.write(mUuid.toString()
                                        .getBytes());
        } catch (IOException e) {
            System.out.println("Failed to store the UUID in the file!");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Read the UUID file and get the UUID store and return it.
     * <p>
     * If the file is not found return null.
     * <p>
     * If something append during the process of the uuid string then return null
     * <p><br>
     * Code come from : <a href="https://developer.android.com/training/data-storage/app-specific#internal-access-stream">Android Developers</a>
     *
     * @return The uuid if the file is found and the string correctly build, otherwise return null.
     *
     * @see FileInputStream
     * @see Context#openFileInput(String)
     * @see InputStreamReader
     * @see StringBuilder
     * @see StringBuilder#append(String)
     * @see StringBuilder#toString()
     * @see BufferedReader
     * @see BufferedReader#readLine()
     * @see java.util.UUID#fromString(String)
     * @see <a href="https://developer.android.com/training/data-storage/app-specific#internal-access-stream">Android Developers</a>
     */
    public java.util.UUID readUUIDFromFile() {
        FileInputStream fis;
        try {
            fis = mContext.openFileInput(mFileName);
        } catch (FileNotFoundException e) {
            return null;
        }

        InputStreamReader inputStreamReader = new InputStreamReader(
                fis,
                StandardCharsets.UTF_8
        );

        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }

            String contents = stringBuilder.toString();
            return java.util.UUID.fromString(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Delete the uuid store file
     *
     * @return True if the file is correctly deleted, else false
     *
     * @see Context#deleteFile(String)
     */
    public boolean deleteUUIDFile() {
        return mContext.deleteFile(mFileName);
    }
}
