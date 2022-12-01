package com.glassait.androidproject.common.utils.file;

import android.content.Context;

import com.glassait.androidproject.common.utils.secret.Secret;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Cache {
    private final String  mFileName;
    private final Context mContext;

    public Cache(String filename, Context context) {
        this.mFileName = filename;
        this.mContext = context;
    }

    /**
     * @return The name of the file
     */
    public String getFileName() {
        return mFileName;
    }

    /**
     * Create a file on the phone to store some data
     * <p><br>
     * Code come from : <a href="https://developer.android.com/training/data-storage/app-specific#internal-store-stream">Android Developers</a>
     *
     * @see File#File(String, String)
     * @see Context#getFilesDir()
     * @see <a href="https://developer.android.com/training/data-storage/app-specific#internal-store-stream">Android Developers</a>
     */
    public void createFile() {
        new File(
                mContext.getFilesDir(),
                mFileName
        );
    }

    /**
     * Store the data in the file. The file has to be created before storing.
     * <p><br>
     * Code come from : <a href="https://developer.android.com/training/data-storage/app-specific#internal-store-stream">Android Developers</a>
     *
     * @param data The data to store in the file
     *
     * @return True if the data is successfully store, false otherwise
     *
     * @see Context#openFileOutput(String, int)
     * @see FileOutputStream#write(byte[])
     * @see <a href="https://developer.android.com/training/data-storage/app-specific#internal-store-stream">Android Developers</a>
     */
    public boolean storeDataInFile(byte[] data) {
        try (FileOutputStream fileOutputStream = mContext.openFileOutput(
                mFileName,
                Context.MODE_PRIVATE
        )) {
            fileOutputStream.write(data);
        } catch (IOException e) {
            System.out.println("Failed to store the data in the file!");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Other form of the main methode {@link #storeDataInFile(byte[])}
     *
     * @param data The date to store in the file
     *
     * @return True if the data is successfully store, false otherwise
     *
     * @see #storeDataInFile(byte[])
     */
    public boolean storeDataInFile(String data) {
        return storeDataInFile(data.getBytes());
    }

    /**
     * Other form of the main methode {@link #storeDataInFile(byte[])}
     *
     * @param data The date to store in the file
     *
     * @return True if the data is successfully store, false otherwise
     *
     * @see #storeDataInFile(byte[])
     */
    @SuppressWarnings("all")
    public boolean storeDataInFile(int data) {
        return storeDataInFile(Integer.toString(data)
                                      .getBytes());
    }

    /**
     * Read the file for getting the stored data and return it.
     * <p>
     * If the file is not found return null.
     * <p>
     * If something append during the process of the uuid string then return null
     * <p><br>
     * Code come from : <a href="https://developer.android.com/training/data-storage/app-specific#internal-access-stream">Android Developers</a>
     *
     * @return The data if the file is found and the string correctly build, otherwise return null.
     *
     * @see Context#openFileInput(String)
     * @see InputStreamReader#InputStreamReader(InputStream, Charset)
     * @see StringBuilder#StringBuilder()
     * @see StringBuilder#append(String)
     * @see StringBuilder#toString()
     * @see BufferedReader#BufferedReader(Reader)
     * @see BufferedReader#readLine()
     * @see <a href="https://developer.android.com/training/data-storage/app-specific#internal-access-stream">Android Developers</a>
     */
    public String readDataFromFile() {
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

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int readDateFromFile() {
        String data = readDataFromFile();
        if (data == null) {
            return Secret.INVALID_INT;
        }
        return Integer.parseInt(data);
    }

    /**
     * Delete the file
     *
     * @return True if the file is correctly deleted, false otherwise.
     */
    public boolean deleteFile() {
        return mContext.deleteFile(mFileName);
    }
}
