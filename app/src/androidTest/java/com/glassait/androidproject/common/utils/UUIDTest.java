package com.glassait.androidproject.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.glassait.androidproject.common.utils.file.UUID;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class UUIDTest {
    private final Context mContext = InstrumentationRegistry.getInstrumentation()
                                                            .getTargetContext();
    private final String  mEmail   = "UnitTest";

    /**
     * Test the initialisation of the class
     * <p>
     * Check if the uuid is null and check if the file name is correctly created.
     *
     * @see UUID#UUID(Context, String)
     * @see UUID#getUuid()
     * @see UUID#getFileName()
     */
    @Test
    public void initOfTheClass() {
        UUID uuid = new UUID(
                mContext,
                mEmail
        );
        assertNull(uuid.getUuid());
        assertEquals(
                mEmail + "_uuid",
                uuid.getFileName()
        );
    }

    /**
     * Test the creation of UUID.
     *
     * @see UUID#UUID(Context, String)
     * @see UUID#generateUUID()
     */
    @Test
    public void generatingUUID() {
        UUID uuid = new UUID(
                mContext,
                mEmail
        );
        uuid.generateUUID();
        assertNotNull(uuid.getUuid());
    }

    /**
     * Test the file creation and storing process of the UUID without have generated UUID before.
     *
     * @see UUID#UUID(Context, String)
     * @see UUID#storeUUID()
     */
    @Test
    public void storeUUIDInFileWithoutGeneratingUUIDBefore() {
        UUID uuid = new UUID(
                mContext,
                mEmail
        );
        assertTrue(uuid.storeUUID());
    }

    /**
     * Test the file creation and storing process of the UUID with generated UUID before.
     *
     * @see UUID#UUID(Context, String)
     * @see UUID#generateUUID()
     * @see UUID#storeUUID()
     */
    @Test
    public void storeUUIDInFile() {
        UUID uuid = new UUID(
                mContext,
                mEmail
        );
        uuid.generateUUID();
        assertTrue(uuid.storeUUID());
    }

    /**
     * Test the reading file process of the UUID. Generate the file before.
     *
     * @see UUID#UUID(Context, String)
     * @see UUID#storeUUID()
     * @see UUID#readUUIDFile()
     */
    @Test
    public void readUUIDFromFile() {
        UUID uuid = new UUID(
                mContext,
                mEmail + "1"
        );
        assertTrue(uuid.storeUUID());
        assertNotNull(uuid.readUUIDFile());
    }

    /**
     * Test the reading file process of the UUID. Don't generate the file before.
     *
     * @see UUID#UUID(Context, String)
     * @see UUID#readUUIDFile()
     */
    @Test
    public void readUUIDFromFileNotCreated() {
        UUID uuid = new UUID(
                mContext,
                mEmail + "2"
        );
        assertNull(uuid.readUUIDFile());
    }

    /**
     * Test the deletion of none existing UUID file
     *
     * @see UUID#UUID(Context, String)
     * @see UUID#deleteUUIDFile()
     * @see UUID#readUUIDFile()
     */
    @Test
    public void deleteUUIDFileWithFileNotExists() {
        UUID uuid = new UUID(
                mContext,
                mEmail + "2"
        );
        assertFalse(uuid.deleteUUIDFile());
        assertNull(uuid.readUUIDFile());
    }

    /**
     * Test the deletion of file.
     * <p>
     * Delete all files uses for the test
     *
     * @see UUID#UUID(Context, String)
     * @see UUID#deleteUUIDFile()
     * @see UUID#readUUIDFile()
     */
    @After
    public void deleteUUIDFile() {
        UUID uuid = new UUID(
                mContext,
                mEmail
        );
        UUID uuid1 = new UUID(
                mContext,
                mEmail + "1"
        );
        UUID uuid2 = new UUID(
                mContext,
                "tendzinroffler@gmail.com"
        );

        uuid.deleteUUIDFile();
        uuid1.deleteUUIDFile();
        uuid2.deleteUUIDFile();
    }
}