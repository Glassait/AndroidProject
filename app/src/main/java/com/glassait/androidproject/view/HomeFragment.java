package com.glassait.androidproject.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.glassait.androidproject.R;

public class HomeFragment extends Fragment {
    View mRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(
                R.layout.fragment_home,
                container,
                false
        );

        TextView in_the_area_see_all = mRoot.findViewById(R.id.fragment_home_see_all_in_area_tv);
        in_the_area_see_all.setOnClickListener(View -> {
            // TODO change the fragment to the SEARCH
            System.out.println("See all in the area");
        });

        TextView your_offer_see_all = mRoot.findViewById(R.id.fragment_home_see_all_your_offer_tv);
        your_offer_see_all.setOnClickListener(View -> {
            // TODO change the fragment to the Create offer
            System.out.println("See all the offer");
        });

        TextView my_reservation_see_all =
                mRoot.findViewById(R.id.fragment_home_see_all_my_reservation_tv);
        my_reservation_see_all.setOnClickListener(View -> {
            // TODO change to the fragment see my reservation
            System.out.println("See all the reservation");
        });

        ConstraintLayout add_offer = mRoot.findViewById(R.id.fragment_home_add_offer);
        add_offer.setOnClickListener(View -> {
            // TODO change to the fragment for creating an offer
            System.out.println("Add a offer");
        });

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
}