package com.glassait.androidproject.AppDatabase;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.glassait.androidproject.common.utils.checker.Address;
import com.glassait.androidproject.model.dao.OfferDao;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

@RunWith(AndroidJUnit4.class)
public class FillDatabaseTest {
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
    public void fillWithUser() {
        com.glassait.androidproject.common.utils.file.UUID uuid =
                new com.glassait.androidproject.common.utils.file.UUID(
                        APP_CONTEXT,
                        "mailbox" + "@gmail.com"
                );
        uuid.generateUUID();
        User user = new User(
                "Tendzin",
                "Roffler",
                "mailbox@gmail.com",
                "+33012345789",
                new Address(
                        APP_CONTEXT,
                        "31 Rue Cambronne",
                        "75015",
                        "Paris",
                        "France"
                ),
                uuid.getUuid()
        );
        uuid.storeUUID();
        user.uid = 5;
        mUserDao.insert(user)
                .subscribe()
                .dispose();
        user = new User(
                "Thomas",
                "Granier",
                "thgr@gmail.com",
                "+33012345789",
                new Address(
                        APP_CONTEXT,
                        "39 Rue Gassendi",
                        "75014",
                        "Paris",
                        "France"
                ),
                UUID.randomUUID()
        );
        user.uid = 6;
        mUserDao.insert(user)
                .subscribe()
                .dispose();
        user = new User(
                "Vincent",
                "Smith",
                "smith@gmail.com",
                "+33012345789",
                new Address(
                        APP_CONTEXT,
                        "29 Bd des Invalides",
                        "75007",
                        "Paris",
                        "France"
                ),
                UUID.randomUUID()
        );
        user.uid = 7;
        mUserDao.insert(user)
                .subscribe()
                .dispose();
        user = new User(
                "Nathalie",
                "Portman",
                "portmain@hotmain.com",
                "+33012345789",
                new Address(
                        APP_CONTEXT,
                        "64 Rue Manuel",
                        "59000",
                        "Lille",
                        "France"
                ),
                UUID.randomUUID()
        );
        user.uid = 8;
        mUserDao.insert(user)
                .subscribe()
                .dispose();
        user = new User(
                "Killian",
                "Dos",
                "killiandos@hotmail.com",
                "+33012345789",
                new Address(
                        APP_CONTEXT,
                        "Bd Professeur Louis Vialleton",
                        "34000",
                        "Montpellier",
                        "France"
                ),
                UUID.randomUUID()
        );
        user.uid = 9;
        mUserDao.insert(user)
                .subscribe()
                .dispose();
    }

    @Test
    public void fillWithOffer() {
        final User[] user = new User[1];
        for (int i = 5; i < 10; i++) {
            mUserDao.getUserFromUid(i)
                    .subscribe(u -> user[0] = u)
                    .dispose();
            mOfferDao.insert(new Offer(
                             "Axe",
                             "manual",
                             "no_storage_ref",
                             user[0].address.getLocation(APP_CONTEXT),
                             user[0].uid
                     ))
                     .subscribe()
                     .dispose();
            mOfferDao.insert(new Offer(
                             "Hammer",
                             "thermic",
                             "no_storage_ref",
                             user[0].address.getLocation(APP_CONTEXT),
                             user[0].uid
                     ))
                     .subscribe()
                     .dispose();
            mOfferDao.insert(new Offer(
                             "Screwdriver",
                             "electric",
                             "no_storage_ref",
                             user[0].address.getLocation(APP_CONTEXT),
                             user[0].uid
                     ))
                     .subscribe()
                     .dispose();
        }
    }
}
