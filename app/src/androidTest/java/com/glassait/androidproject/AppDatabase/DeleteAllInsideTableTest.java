package com.glassait.androidproject.AppDatabase;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.glassait.androidproject.model.dao.OfferDao;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DeleteAllInsideTableTest {
    private static final Context     APP_CONTEXT = InstrumentationRegistry.getInstrumentation()
                                                                          .getTargetContext();
    private static final Builder     BUILDER     = Builder.getInstance();
    private static final AppDatabase APP_DATABASE;
    private final        OfferDao    mOfferDao   = APP_DATABASE.offerDao();
    private final        UserDao     mUserDao    = APP_DATABASE.userDao();

    static {
        BUILDER.buildDatabase(APP_CONTEXT);
        APP_DATABASE = BUILDER.getAppDatabase();
    }

    @Test
    public void insideUserTable() {
        mUserDao.deleteAll()
                .subscribe()
                .dispose();
        assertEquals(
                0,
                mUserDao.getAll()
                        .size()
        );
    }

    @Test
    public void insideOfferTable() {
        mOfferDao.deleteAll()
                 .subscribe()
                 .dispose();
        assertEquals(
                0,
                mOfferDao.getAll()
                         .size()
        );
    }
}
