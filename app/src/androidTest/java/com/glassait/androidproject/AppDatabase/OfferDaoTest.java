package com.glassait.androidproject.AppDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.location.Location;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.glassait.androidproject.model.dao.OfferDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.Offer;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class OfferDaoTest {
    private static final Location    LOCATION    = new Location("");
    private static final Context     APP_CONTEXT = InstrumentationRegistry.getInstrumentation()
                                                                          .getTargetContext();
    private static final Builder     BUILDER     = Builder.getInstance();
    private static final AppDatabase APP_DATABASE;
    private final        OfferDao    mOfferDao   = APP_DATABASE.offerDao();

    static {
        LOCATION.setLongitude(0);
        LOCATION.setLatitude(0);
        BUILDER.buildDatabase(APP_CONTEXT);
        APP_DATABASE = BUILDER.getAppDatabase();
    }

    /**
     * Test the insert function of {@link OfferDao}
     *
     * @see Offer#Offer(String, String, String, Location, int)
     * @see OfferDao#insert(Offer)
     * @see OfferDao#getAll()
     */
    @Test
    public void insert() {
        Offer offer = new Offer(
                "title",
                "electric",
                "testing",
                LOCATION,
                1
        );
        offer.uid = 1;

        final boolean[] offerIsInsert = {false};
        mOfferDao.insert(offer)
                 .subscribe(
                         () -> offerIsInsert[0] = true,
                         Throwable::printStackTrace
                 )
                 .dispose();

        assertTrue(offerIsInsert[0]);
        assertTrue(mOfferDao.getAll()
                            .size() > 0);
    }

    /**
     * Try to insert a null offer into the database
     * Test the insert function of {@link Offer}
     *
     * @see OfferDao#insert(Offer)
     * @see OfferDao#getAll()
     */
    @Test
    public void insertWithNullUser() {
        final boolean[] offerIsInsert = {false};
        int numberOfOffersBefore = mOfferDao.getAll()
                                            .size();

        mOfferDao.insert(null)
                 .subscribe(
                         () -> offerIsInsert[0] = true,
                         throwable -> offerIsInsert[0] = false
                 )
                 .dispose();

        assertFalse(offerIsInsert[0]);
        assertEquals(
                numberOfOffersBefore,
                mOfferDao.getAll()
                         .size()
        );
    }

    /**
     * Test the update function of {@link OfferDao}
     * Set the offer uid to 1 because the constructor set to 0 and SQLLite start at 1
     *
     * @see Offer#Offer(String, String, String, Location, int)
     * @see OfferDao#update(Offer)
     * @see OfferDao#getAllOffersFromCreatorId(int)
     * @see OfferDao#insert(Offer)
     */
    @Test
    public void updateOneElement() {
        Offer offer = new Offer(
                "title",
                "electric",
                "testing",
                LOCATION,
                1
        );
        offer.uid = 1;

        mOfferDao.insert(offer)
                 .subscribe()
                 .dispose();

        String expectedTitle = "New title";
        offer.title = expectedTitle;
        final boolean[] userIsUpdate = {false};

        mOfferDao.update(offer)
                 .subscribe(
                         () -> userIsUpdate[0] = true,
                         Throwable::printStackTrace
                 )
                 .dispose();

        List<Offer> allOffers     = mOfferDao.getAllOffersFromCreatorId(1);
        Offer       modifiedOffer = allOffers.get(0);

        assertTrue(userIsUpdate[0]);
        assertEquals(
                expectedTitle,
                modifiedOffer.title
        );
    }

    /**
     * Test the update function of {@link OfferDao}
     * Set the user uid to 1 because the constructor set to 0 and SQLLite start at 1
     *
     * @see Offer#Offer(String, String, String, Location, int)
     * @see OfferDao#insert(Offer)
     * @see OfferDao#update(Offer)
     * @see OfferDao#getAllOffersFromCreatorId(int)
     */
    @Test
    public void updateMultipleElements() {
        Offer offer = new Offer(
                "title",
                "electric",
                "testing",
                LOCATION,
                1
        );
        offer.uid = 1;

        mOfferDao.insert(offer)
                 .subscribe()
                 .dispose();

        String  expectedTitle      = "New title";
        int     expectedCreatorId  = 2;
        boolean expectedIsReserved = true;

        offer.title = expectedTitle;
        offer.creatorId = expectedCreatorId;
        offer.isReserved = expectedIsReserved;

        final boolean[] userIsUpdate = {false};
        mOfferDao.update(offer)
                 .subscribe(
                         () -> userIsUpdate[0] = true,
                         Throwable::printStackTrace
                 )
                 .dispose();

        List<Offer> allOffers     = mOfferDao.getAllOffersFromCreatorId(2);
        Offer       modifiedOffer = allOffers.get(0);

        assertTrue(userIsUpdate[0]);
        assertEquals(
                expectedTitle,
                modifiedOffer.title
        );
        assertEquals(
                expectedCreatorId,
                modifiedOffer.creatorId
        );
        assertEquals(
                expectedIsReserved,
                modifiedOffer.isReserved
        );
    }

    /**
     * Test the update function of {@link OfferDao}
     */
    @Test
    public void updateWithNullUser() {
        final boolean[] userIsUpdate = {false};

        mOfferDao.update(null)
                 .subscribe(
                         () -> userIsUpdate[0] = true,
                         throwable -> userIsUpdate[0] = false
                 )
                 .dispose();

        assertFalse(userIsUpdate[0]);
    }

    /**
     * Test the getAllOffersFromCreatorId function of {@link OfferDao}
     * Set the user uid to 1 because the constructor set to 0 and SQLLite start at 1
     *
     * @see Offer#Offer(String, String, String, Location, int)
     * @see OfferDao#getAllOffersFromCreatorId(int)
     * @see OfferDao#insert(Offer)
     */
    @Test
    public void getAllOffersFromCreatorId() {
        Offer offer = new Offer(
                "title",
                "electric",
                "testing",
                LOCATION,
                1
        );
        offer.uid = 1;

        mOfferDao.insert(offer)
                 .subscribe()
                 .dispose();

        List<Offer> allOffers = mOfferDao.getAllOffersFromCreatorId(1);
        Offer       offerGet  = allOffers.get(0);

        assertEquals(
                offer.title,
                offerGet.title
        );
        assertEquals(
                offer.creatorId,
                offerGet.creatorId
        );
    }

    @Test
    public void getCountOffersOfCreator() {
        Offer offer = new Offer(
                "title",
                "electric",
                "testing",
                LOCATION,
                1
        );
        offer.uid = 1;
        mOfferDao.insert(offer)
                 .subscribe()
                 .dispose();

        assertTrue(mOfferDao.getCountOffersOfCreator(offer.creatorId) > 0);
    }

    /**
     * Delete one test offer
     * Test the delete function of {@link OfferDao}
     *
     * @see OfferDao#getAll()
     * @see OfferDao#delete(Offer)
     */
    @Test
    public void userDAO_deleteOneUser() {
        int numberOfUsersBefore = mOfferDao.getAll()
                                           .size();
        Offer offer = new Offer(
                "title",
                "electric",
                "testing",
                LOCATION,
                1
        );
        offer.uid = 1;

        final boolean[] userIsDeleted = {false};
        mOfferDao.delete(offer)
                 .subscribe(
                         () -> userIsDeleted[0] = true,
                         Throwable::printStackTrace
                 )
                 .dispose();

        assertTrue(userIsDeleted[0]);
        assertTrue(numberOfUsersBefore >= mOfferDao.getAll()
                                                   .size());
    }

    /**
     * Delete all test users
     * Test the delete function of {@link OfferDao}
     *
     * @see OfferDao#getAll()
     * @see OfferDao#delete(Offer)
     */
    @After
    public void deleteAllTestUsers() {
        List<Offer> allOffers = mOfferDao.getAll();

        for (Offer offer : allOffers) {
            if (offer.storage_ref.equals("testing")) {
                final boolean[] offerIsDeleted = {false};
                mOfferDao.delete(offer)
                         .subscribe(
                                 () -> offerIsDeleted[0] = true,
                                 Throwable::printStackTrace
                         )
                         .dispose();
                assertTrue(offerIsDeleted[0]);
            }
        }
    }
}
