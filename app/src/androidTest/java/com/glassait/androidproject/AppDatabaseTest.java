package com.glassait.androidproject;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.glassait.androidproject.dao.UserDao;
import com.glassait.androidproject.database.AppDatabase;
import com.glassait.androidproject.database.Builder;
import com.glassait.androidproject.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppDatabaseTest {
    private UserDao userDao;
    private User user;
    private AppDatabase database;

    /**
     * Set up the {@link AppDatabase} for all DAO function
     *
     * @see Builder#buildDatabase(Context)
     * @see AppDatabase#userDao()
     * @see User#User(int, String, String)
     */
    @Before
    public void setup() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        this.database = new Builder().buildDatabase(appContext);
        this.userDao = database.userDao();
        this.user = new User(1, "test", "family name");
    }

    /**
     * Test the insert function of {@link UserDao}
     *
     * @see UserDao#insert(User)
     * @see UserDao#getAll()
     */
    @Test
    public void userDAO_insert() {
        int expectedLength = 1;
        this.userDao.insert(user).subscribe().dispose();
        assertEquals(expectedLength, this.userDao.getAll().size());
    }

    /**
     * Test the update function of {@link UserDao}
     *
     * @see UserDao#update(User)
     * @see UserDao#getUserFromUID(int)
     */
    @Test
    public void userDAO_update() {
        String expectedName = "New name";

        this.user.firstName = expectedName;
        this.userDao.update(this.user).subscribe().dispose();
        final User[] modifiedUser = {null};

        this.userDao.getUserFromUID(1).subscribe(user -> modifiedUser[0] = user, Throwable::printStackTrace).dispose();
        assertEquals(expectedName, modifiedUser[0].firstName);
    }

    /**
     * Test the getAll function of {@link UserDao}
     * Plus print the list of user inside the table
     *
     * @see UserDao#getAll()
     */
    @Test
    public void userDAO_getAll() {
        int expectedLength = 0;
        assertEquals(expectedLength, this.userDao.getAll().size());
    }

    /**
     * Test the delete function of {@link UserDao}
     *
     * @see UserDao#delete(User)
     * @see UserDao#getAll()
     */
    @Test
    public void userDAO_delete() {
        this.userDao.delete(this.user).subscribe().dispose();
        int expectedLength = 0;
        assertEquals(expectedLength, this.userDao.getAll().size());
    }

    /**
     * Close the database after all test
     *
     * @see AppDatabase#close()
     */
    @After
    public void close() {
        this.database.close();
    }
}
