package com.glassait.androidproject.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.CreateOfferLayout;
import com.glassait.androidproject.common.utils.secret.StoreManager;
import com.glassait.androidproject.model.dao.OfferDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

import java.util.ArrayList;
import java.util.List;

// TODO Add some offer in the area part
// TODO add the offer that the user have reserved
public class HomeFragment extends Fragment {
    // View part
    private       View        mRoot;
    // Database part
    private final AppDatabase mAppDatabase = Builder.getInstance()
                                                    .getAppDatabase();
    private final OfferDao    mOfferDao    = mAppDatabase.offerDao();
    private       User        mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(
                R.layout.fragment_home,
                container,
                false
        );
        mUser = StoreManager.getUser();

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
            List<Offer> displayedOffers = new ArrayList<>();
            for (int i = 0; i < Math.min(
                    allOffersOfUsers.size(),
                    3
            ); i++) {
                displayedOffers.add(allOffersOfUsers.get(i));
            }
            new CreateOfferLayout(
                    mRoot,
                    navController
            ).createOfferLayout(
                    displayedOffers,
                    mRoot.findViewById(R.id.fragment_home_my_offer_LL)
            );

            your_offer_see_all.setOnClickListener(View -> {
                navController.navigate(R.id.see_all_my_offer_fragment);
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

        return mRoot;
    }
}