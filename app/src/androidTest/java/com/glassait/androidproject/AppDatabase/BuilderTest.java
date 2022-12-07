package com.glassait.androidproject.AppDatabase;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class BuilderTest {
    private static final Context APP_CONTEXT = InstrumentationRegistry.getInstrumentation()
                                                                      .getTargetContext();

    /**
     * Test to get an instance of the {@link Builder} class
     *
     * @see Builder#getInstance()
     */
    @Test
    public void getInstance() {
        Builder builder = Builder.getInstance();

        assertNotNull(builder);
    }

    /**
     * Test to build the database
     *
     * @see Builder#getInstance()
     * @see Builder#buildDatabase(Context)
     */
    @Test
    public void buildDatabase() {
        Builder builder = Builder.getInstance();
        builder.buildDatabase(APP_CONTEXT);

        assertNotNull(builder);
        assertNotNull(builder.getAppDatabase());
    }

    /**
     * Test to get dao from the build database
     *
     * @see Builder#getInstance()
     * @see Builder#buildDatabase(Context)
     * @see AppDatabase#userDao()
     */
    @Test
    public void getDaoFromDatabase() {
        Builder builder = Builder.getInstance();
        builder.buildDatabase(APP_CONTEXT);

        assertNotNull(builder.getAppDatabase()
                             .userDao());
    }

    /**
     * Try to get the dao from the database without building the database
     *
     * @see Builder#getInstance()
     * @see Builder#getAppDatabase()
     * @see AppDatabase#userDao()
     */
    @Test
    public void getDaoWithBuildingDatabase() {
        Builder builder = Builder.getInstance();
        UserDao userDao;

        try {
            userDao = builder.getAppDatabase()
                             .userDao();
        } catch (NullPointerException e) {
            userDao = null;
        }

        assertNull(userDao);
    }
}