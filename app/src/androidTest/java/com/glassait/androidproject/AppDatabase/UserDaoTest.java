package com.glassait.androidproject.AppDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import kotlin.jvm.JvmStatic;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserDaoTest {
    private static final Context     APP_CONTEXT;
    private static final Builder     BUILDER;
    private final        UserDao     mUserDao = APP_DATABASE.userDao();
    private static final User        USER     = new User(
            "First name",
            "Last name",
            "boitemail@gmail.com",
            "0000000000",
            "15 road field",
            "city",
            "country",
            "password"
    );
    private static final AppDatabase APP_DATABASE;

    static {
        APP_CONTEXT = InstrumentationRegistry.getInstrumentation()
                                             .getTargetContext();
        BUILDER = Builder.getInstance();
        BUILDER.buildDatabase(APP_CONTEXT);
        APP_DATABASE = BUILDER.getAppDatabase();
    }

    /**
     * Insert the user into the database before each test
     * Test the insert function of {@link UserDao}
     *
     * @see UserDao#insert(User)
     * @see UserDao#getAll()
     */
    @Before
    public void userDAO_insert() {
        mUserDao.insert(USER)
                .subscribe()
                .dispose();
    }

    /**
     * Clear the database after each test
     *
     * @see AppDatabase#clearAllTables()
     */
    @After
    public void afterEachText() {
        APP_DATABASE.clearAllTables();
    }

    /**
     * Close the database after all test
     *
     * @see AppDatabase#close()
     */
    @AfterClass
    @JvmStatic
    public static void close() {
        APP_DATABASE.close();
    }

    /**
     * Test the update function of {@link UserDao}
     *
     * @see UserDao#update(User)
     * @see UserDao#getUserFromEmail(String)
     */
    @Test
    public void userDAO_update() {
        String expectedName = "New name";

        USER.firstName = expectedName;
        mUserDao.update(USER)
                .subscribe()
                .dispose();
        final User[] modifiedUser = new User[1];

        mUserDao.getUserFromEmail(USER.email)
                .subscribe(
                        user -> modifiedUser[0] = user,
                        Throwable::printStackTrace
                )
                .dispose();

        assertEquals(
                expectedName,
                modifiedUser[0].firstName
        );
    }

    /**
     * Test the getAll function of {@link UserDao}
     *
     * @see UserDao#getAll()
     */
    @Test
    public void userDAO_getAll() {
        int expectedLength = 1;
        assertEquals(
                expectedLength,
                mUserDao.getAll()
                        .size()
        );
    }

    /**
     * Test the getUserFromEmail function of {@link UserDao}
     *
     * @see UserDao#getUserFromEmail(String)
     */
    @Test
    public void userDAO_getUserFromEmail() {
        final User[] user = new User[1];
        mUserDao.getUserFromEmail(USER.email)
                .subscribe(userGet -> user[0] = userGet)
                .dispose();
        assertEquals(
                USER.email,
                user[0].email
        );
    }

    /**
     * Extend the previous test with error handling
     *
     * @see UserDao#getUserFromEmail(String)
     */
    @Test
    public void userDAO_getUserFromEmailWithErrorHandling() {
        final User[] user = new User[1];
        mUserDao.getUserFromEmail("newemail@mail.com")
                .subscribe(
                        userGet -> user[0] = userGet,
                        throwable -> user[0] = null
                )
                .dispose();

        if (user[0] == null) {
            System.out.println("User not found");
            assertNull(user[0]);
        } else {
            System.out.println("User found");
            assertEquals(
                    USER.email,
                    user[0].email
            );
        }
    }

    /**
     * Test the delete function of {@link UserDao}
     *
     * @see UserDao#delete(User)
     * @see UserDao#getAll()
     */
    @Test
    public void userDAO_delete() {
        int expectedLength = 0;
        mUserDao.delete(USER)
                .subscribe()
                .dispose();
        assertEquals(
                expectedLength,
                mUserDao.getAll()
                        .size()
        );
    }
}
