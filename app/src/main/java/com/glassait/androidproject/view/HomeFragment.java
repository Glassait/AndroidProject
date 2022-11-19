package com.glassait.androidproject.view;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.file.GetUserFromFile;
import com.glassait.androidproject.model.dao.OfferDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

import java.util.List;

public class HomeFragment extends Fragment {
    // View part
    private       View        mRoot;
    // Database part
    private final AppDatabase mAppDatabase      = Builder.getInstance()
                                                         .getAppDatabase();
    private final OfferDao    mOfferDao         = mAppDatabase.offerDao();
    private       User        mUser;
    // Common
    private       boolean     mMyOfferIsDisplay = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(
                R.layout.fragment_home,
                container,
                false
        );

        getUser();

        NavController navController = NavHostFragment.findNavController(this);

        TextView in_the_area_see_all = mRoot.findViewById(R.id.fragment_home_see_all_in_area_tv);
        in_the_area_see_all.setOnClickListener(View -> {
            // TODO change the fragment to the SEARCH
            System.out.println("See all in the area");
        });

        TextView    your_offer_see_all = mRoot.findViewById(R.id.fragment_home_see_all_my_offer_tv);
        List<Offer> allOffersOfUsers   = mOfferDao.getAllOffersFromCreatorId(mUser.uid);
        if (allOffersOfUsers.size() == 0) {
            your_offer_see_all.setVisibility(View.INVISIBLE);
        } else {
            int[] myOfferIdCl =
                    {R.id.fragment_home_my_offer_1_layout, R.id.fragment_home_my_offer_2_layout, R.id.fragment_home_my_offer_3_layout};
            int[] myOfferIdTitle =
                    {R.id.fragment_home_title_my_offer_1_tv, R.id.fragment_home_title_my_offer_2_tv, R.id.fragment_home_title_my_offer_3_tv};
            HorizontalScrollView myOfferHsv = mRoot.findViewById(R.id.fragment_home_my_offer_HSV);
            for (int i = 0; i < 3; i++) {
                Offer offer = allOffersOfUsers.get(i);

                // Layout
                ContextThemeWrapper myOfferClThemeWrapper = new ContextThemeWrapper();
//                myOfferClThemeWrapper.setTheme(R.style.Box_full_radius);
                ConstraintLayout myOfferCl = new ConstraintLayout(myOfferClThemeWrapper);
                ConstraintLayout.LayoutParams myOfferClLayoutParams =
                        new ConstraintLayout.LayoutParams(
                                320,
                                ConstraintLayout.LayoutParams.MATCH_PARENT
                        );
                myOfferClLayoutParams.setMarginStart(30);
                myOfferClLayoutParams.setMarginEnd(30);
                myOfferCl.setLayoutParams(myOfferClLayoutParams);
                myOfferCl.setId(myOfferIdCl[i]);

                // Title
                ContextThemeWrapper myOfferTitleThemeWrapper = new ContextThemeWrapper();
//                myOfferTitleThemeWrapper.setTheme(R.style.bold24);
                TextView myOfferTitle = new TextView(myOfferTitleThemeWrapper);
                myOfferTitle.setBackgroundColor(mRoot.getResources()
                                                     .getColor(
                                                             R.color.transparent,
                                                             null
                                                     ));
                myOfferTitle.setId(myOfferIdTitle[i]);
                myOfferTitle.setText(offer.title);
                ConstraintLayout.LayoutParams myOfferTitleLayoutParams =
                        new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                ConstraintLayout.LayoutParams.WRAP_CONTENT
                        );
                myOfferTitleLayoutParams.setMargins(
                        13,
                        22,
                        0,
                        0
                );
                myOfferTitleLayoutParams.startToStart = myOfferCl.getId();
                myOfferTitleLayoutParams.topToTop = myOfferCl.getId();
                myOfferCl.addView(myOfferTitle);

                myOfferHsv.addView(
                        myOfferCl,
                        0
                );
            }
     /*       <androidx.constraintlayout.widget.ConstraintLayout
            style="@style/offer"
            android:id="@+id/fragment_home_offer_1_layout">*/

          /*          <TextView
            android:text="@string/offer_title"
            android:id="@+id/fragment_home_offer_title_in_area_1_tv"
            style="@style/offer.title"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            android:autoSizeTextType="uniform"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintBottom_toTopOf="@+id/fragment_home_tools_image_in_area_1_IV" />*/

           /*         <ImageView
            android:src="@drawable/ic_launcher_foreground"
            tools:srcCompat="@tools:sample/avatars"
            android:id="@+id/fragment_home_tools_image_in_area_1_IV"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/fragment_home_offer_title_in_area_1_tv"
            android:contentDescription="@string/offer_image_of_the_tool"
            tools:ignore="ImageContrastCheck"
            app:layout_constraintBottom_toTopOf="@+id/fragment_home_name_in_area_1_tv"
            style="@style/offer.image" />*/

       /*             <TextView
            android:text="@string/offer_last_first_name"
            android:id="@+id/fragment_home_name_in_area_1_tv"
            app:layout_constraintStart_toStartOf="parent"
            style="@style/offer.text"
            android:autoSizeTextType="uniform"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/fragment_home_tools_image_in_area_1_IV"
            app:layout_constraintBottom_toTopOf="@+id/fragment_home_city_in_area_1_tv" />*/

           /*         <TextView
            android:text="@string/offer_city"
            android:id="@+id/fragment_home_city_in_area_1_tv"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/fragment_home_name_in_area_1_tv"
            style="@style/offer.text"
            android:autoSizeTextType="uniform" />
                </androidx.constraintlayout.widget.ConstraintLayout>*/

            your_offer_see_all.setOnClickListener(View -> {
                // TODO change the fragment to the Create offer
                System.out.println("See all the offer");
            });
        }

        TextView my_reservation_see_all =
                mRoot.findViewById(R.id.fragment_home_see_all_my_reservation_tv);
        my_reservation_see_all.setOnClickListener(View -> {
            // TODO change to the fragment see my reservation
            System.out.println("See all the reservation");
        });

        // On click listener to navigate to the creation of offer
        ConstraintLayout add_offer = mRoot.findViewById(R.id.fragment_home_add_offer);
        add_offer.setOnClickListener(View -> navController.navigate(R.id.create_offer_fragment));

        ConstraintLayout do_reservation = mRoot.findViewById(R.id.fragment_home_do_reservation);
        do_reservation.setOnClickListener(View -> {
            // TODO change to the fragment to search
            System.out.println("Do a reservation");
        });

        // TODO Add some offer in the area part
        // TODO add the offer that the user has created
        // TODO add the offer that the user have reserved

        return mRoot;
    }

    /**
     * Get the user from data inside file.
     *
     * @see GetUserFromFile#GetUserFromFile(Context)
     * @see GetUserFromFile#getUser()
     * @see Thread#Thread()
     * @see Thread#start()
     */
    private void getUser() {
        GetUserFromFile getUser = new GetUserFromFile(mRoot.getContext());
        Thread          thread  = new Thread(getUser);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mUser = getUser.getUser();
    }
}