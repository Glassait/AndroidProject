package com.glassait.androidproject.AppDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.UUID;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class UserDaoTest {
    private static final Context     APP_CONTEXT = InstrumentationRegistry.getInstrumentation()
                                                                          .getTargetContext();
    private static final Builder     BUILDER     = Builder.getInstance();
    private static final AppDatabase APP_DATABASE;
    private final        UserDao     mUserDao    = APP_DATABASE.userDao();

    static {
        BUILDER.buildDatabase(APP_CONTEXT);
        APP_DATABASE = BUILDER.getAppDatabase();
    }

    /**
     * Test the insert function of {@link UserDao}
     *
     * @see User#User(String, String, String, String, String, String, String, String, UUID)
     * @see UserDao#insert(User)
     * @see UserDao#getAll()
     */
    @Test
    public void insert() {
        User user = new User(
                "First name",
                "Last name",
                "boitemail@gmail.com",
                "0000000000",
                "15 road field",
                "75000",
                "city",
                "country",
                UUID.randomUUID()
        );
        user.uid = 1;

        final boolean[] userIsInsert = {false};

        mUserDao.insert(user)
                .subscribe(
                        () -> userIsInsert[0] = true,
                        Throwable::printStackTrace
                )
                .dispose();

        assertTrue(userIsInsert[0]);
        assertTrue(mUserDao.getAll()
                           .size() > 0);
    }

    /**
     * Try to insert a null user into the database
     * Test the insert function of {@link UserDao}
     *
     * @see User#User(String, String, String, String, String, String, String, String, UUID)
     * @see UserDao#insert(User)
     * @see UserDao#getAll()
     */
    @Test
    public void insertWithNullUser() {
        final boolean[] userIsInsert = {false};
        int numberOfUsersBefore = mUserDao.getAll()
                                          .size();

        mUserDao.insert(null)
                .subscribe(
                        () -> userIsInsert[0] = true,
                        throwable -> userIsInsert[0] = false
                )
                .dispose();

        assertFalse(userIsInsert[0]);
        assertTrue(mUserDao.getAll()
                           .size() >= 0);
        assertEquals(
                numberOfUsersBefore,
                mUserDao.getAll()
                        .size()
        );
    }

    /**
     * Test the update function of {@link UserDao}
     * Set the user uid to 1 because the constructor set to 0 and SQLLite start at 1
     *
     * @see UserDao#update(User)
     * @see UserDao#getUserFromEmail(String)
     */
    @Test
    public void updateOneElement() {
        User user = new User(
                "First name",
                "Last name",
                "boitemail@gmail.com",
                "0000000000",
                "15 road field",
                "75000",
                "city",
                "country",
                UUID.randomUUID()
        );
        user.uid = 1;

        String expectedName = "New name";
        user.firstName = expectedName;
        final boolean[] userIsUpdate = {false};

        mUserDao.update(user)
                .subscribe(
                        () -> userIsUpdate[0] = true,
                        Throwable::printStackTrace
                )
                .dispose();

        final User[] modifiedUser = new User[1];
        mUserDao.getUserFromEmail(user.email)
                .subscribe(
                        u -> modifiedUser[0] = u,
                        Throwable::printStackTrace
                )
                .dispose();

        assertTrue(userIsUpdate[0]);
        assertEquals(
                expectedName,
                modifiedUser[0].firstName
        );
    }

    /**
     * Test the update function of {@link UserDao}
     * Set the user uid to 1 because the constructor set to 0 and SQLLite start at 1
     *
     * @see UserDao#update(User)
     * @see UserDao#getUserFromEmail(String)
     */
    @Test
    public void updateMultipleElements() {
        User user = new User(
                "First name",
                "Last name",
                "boitemail@gmail.com",
                "0000000000",
                "15 road field",
                "75000",
                "city",
                "country",
                UUID.randomUUID()
        );
        user.uid = 1;
        mUserDao.insert(user)
                .subscribe()
                .dispose();

        String expectedFirstName = "New name";
        String expectedLastName  = "New last name";
        String expectedCity      = "New city";
        user.firstName = expectedFirstName;
        user.lastName = expectedLastName;
        user.city = expectedCity;
        final boolean[] userIsUpdate = {false};

        mUserDao.update(user)
                .subscribe(
                        () -> userIsUpdate[0] = true,
                        Throwable::printStackTrace
                )
                .dispose();

        final User[] modifiedUser = new User[1];
        mUserDao.getUserFromEmail(user.email)
                .subscribe(
                        u -> modifiedUser[0] = u,
                        Throwable::printStackTrace
                )
                .dispose();

        assertTrue(userIsUpdate[0]);
        assertEquals(
                expectedFirstName,
                modifiedUser[0].firstName
        );
        assertEquals(
                expectedLastName,
                modifiedUser[0].lastName
        );
        assertEquals(
                expectedCity,
                modifiedUser[0].city
        );
    }

    /**
     * Test the update function of {@link UserDao}
     *
     * @see UserDao#update(User)
     * @see UserDao#getUserFromEmail(String)
     */
    @Test
    public void updateWithNullUser() {
        final boolean[] userIsUpdate = {false};

        mUserDao.update(null)
                .subscribe(
                        () -> userIsUpdate[0] = true,
                        throwable -> userIsUpdate[0] = false
                )
                .dispose();

        assertFalse(userIsUpdate[0]);
    }

    /**
     * Test the getUserFromEmail function of {@link UserDao}
     * Set the user uid to 1 because the constructor set to 0 and SQLLite start at 1
     *
     * @see User#User(String, String, String, String, String, String, String, String, UUID)
     * @see UserDao#getUserFromEmail(String)
     */
    @Test
    public void getUserFromEmail() {
        User user = new User(
                "First name",
                "Last name",
                "boitemail@gmail.com",
                "0000000000",
                "15 road field",
                "75000",
                "city",
                "country",
                UUID.randomUUID()
        );
        user.uid = 1;

        final User[] userGet = new User[1];
        mUserDao.getUserFromEmail(user.email)
                .subscribe(
                        u -> userGet[0] = u,
                        Throwable::printStackTrace
                )
                .dispose();

        assertEquals(
                user.email,
                userGet[0].email
        );
        assertEquals(
                user.lastName,
                userGet[0].lastName
        );
    }

    /**
     * Extend the previous test with error handling
     *
     * @see UserDao#getUserFromEmail(String)
     */
    @Test
    public void getUserFromEmailWithErrorHandling() {
        final User[] user = new User[1];
        mUserDao.getUserFromEmail("newemail@mail.com")
                .subscribe(
                        userGet -> user[0] = userGet,
                        throwable -> user[0] = null
                )
                .dispose();

        assertNull(user[0]);
    }

    /**
     * Test the getUserFromUid function of {@link UserDao}
     * Set the user uid to 1 because the constructor set to 0 and SQLLite start at 1
     *
     * @see User#User(String, String, String, String, String, String, String, String, UUID)
     * @see UserDao#getUserFromUid(int)
     * @see UserDao#insert(User)
     */
    @Test
    public void getUserFromUid() {
        User user = new User(
                "First name",
                "Last name",
                "boitemail@gmail.com",
                "0000000000",
                "15 road field",
                "75000",
                "city",
                "country",
                UUID.randomUUID()
        );
        user.uid = 2;
        mUserDao.insert(user)
                .subscribe()
                .dispose();

        final User[] userGet = new User[1];
        mUserDao.getUserFromUid(user.uid)
                .subscribe(
                        u -> userGet[0] = u,
                        Throwable::printStackTrace
                )
                .dispose();

        assertEquals(
                user.email,
                userGet[0].email
        );
        assertEquals(
                user.lastName,
                userGet[0].lastName
        );
    }

    /**
     * Extend the previous test with error handling
     *
     * @see UserDao#getUserFromUid(int)
     */
    @Test
    public void getUserFromUidWithErrorHandling() {
        final User[] user = new User[1];
        mUserDao.getUserFromUid(0)
                .subscribe(
                        userGet -> user[0] = userGet,
                        throwable -> user[0] = null
                )
                .dispose();

        assertNull(user[0]);
    }

    /**
     * Delete one test user
     * Test the delete function of {@link UserDao}
     *
     * @see User#User(String, String, String, String, String, String, String, String, UUID)
     * @see UserDao#delete(User)
     * @see UserDao#getAll()
     */
    @Test
    public void deleteOneUser() {
        int numberOfUsersBefore = mUserDao.getAll()
                                          .size();
        User user = new User(
                "First name",
                "Last name",
                "boitemail@gmail.com",
                "0000000000",
                "15 road field",
                "75000",
                "city",
                "country",
                UUID.randomUUID()
        );
        user.uid = 1;

        final boolean[] userIsDeleted = {false};
        mUserDao.delete(user)
                .subscribe(
                        () -> userIsDeleted[0] = true,
                        Throwable::printStackTrace
                )
                .dispose();

        assertTrue(userIsDeleted[0]);
        assertTrue(numberOfUsersBefore >= mUserDao.getAll()
                                                  .size());
    }

    /**
     * Delete all test users
     * Test the delete function of {@link UserDao}
     *
     * @see User#User(String, String, String, String, String, String, String, String, UUID)
     * @see UserDao#delete(User)
     * @see UserDao#getAll()
     */
    @Test
    public void deleteAllTestUsers() {
        List<User> listOfUsers         = mUserDao.getAll();
        int        numberOfUsersBefore = listOfUsers.size();

        for (User user : listOfUsers) {
            if (user.email.equals("boitemail@gmail.com")) {
                final boolean[] userIsDeleted = {false};
                mUserDao.delete(user)
                        .subscribe(
                                () -> userIsDeleted[0] = true,
                                Throwable::printStackTrace
                        )
                        .dispose();
                assertTrue(userIsDeleted[0]);
            }
        }

        assertTrue(numberOfUsersBefore > mUserDao.getAll()
                                                 .size());
    }
}
