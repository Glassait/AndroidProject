package com.glassait.androidproject.view.main.myOffer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.secret.StoreManager;
import com.glassait.androidproject.model.dao.OfferDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

public class MyOfferFragment extends Fragment {
    // Database part
    private final AppDatabase mAppDatabase = Builder.getInstance()
                                                    .getAppDatabase();
    private final OfferDao    mOfferDao    = mAppDatabase.offerDao();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View part
        View root = inflater.inflate(
                R.layout.fragment_my_offer,
                container,
                false
        );

        Offer offer = StoreManager.getOffer();
        User  user  = StoreManager.getUser();

        NavController navController = NavHostFragment.findNavController(this);

        TextView backButton = root.findViewById(R.id.fragment_my_offer_back_btn);
        backButton.setOnClickListener(view -> {
            System.out.println("Back button click on my offer fragment");
            navController.navigate(R.id.home_fragment);
            StoreManager.setOffer(null);
        });

        TextView offerTitleEt = root.findViewById(R.id.fragment_my_offer_title_tv);
        offerTitleEt.setText(offer.title);

        EditText firstNameEt = root.findViewById(R.id.fragment_my_offer_first_name_et);
        firstNameEt.setText(user.firstName);

        EditText lastNameEt = root.findViewById(R.id.fragment_my_offer_last_name_et);
        lastNameEt.setText(user.lastName);

        EditText emailEt = root.findViewById(R.id.fragment_my_offer_email_et);
        emailEt.setText(user.email);

        EditText phoneEt = root.findViewById(R.id.fragment_my_offer_phone_et);
        phoneEt.setText(user.phone);

        EditText addressEt = root.findViewById(R.id.fragment_my_offer_address_et);
        addressEt.setText(user.address);

        EditText postCodeEt = root.findViewById(R.id.fragment_my_offer_postal_code_et);
        postCodeEt.setText(user.postCode);

        EditText cityEt = root.findViewById(R.id.fragment_my_offer_city_et);
        cityEt.setText(user.city);

        EditText countryEt = root.findViewById(R.id.fragment_my_offer_country_et);
        countryEt.setText(user.country);

        EditText categoryEt = root.findViewById(R.id.fragment_my_offer_category_et);
        categoryEt.setText(offer.category);

        TextView deleteBtn = root.findViewById(R.id.fragment_my_offer_delete_btn);
        deleteBtn.setOnClickListener(v -> mOfferDao.delete(offer)
                                                   .subscribe(
                                                           () -> navController.navigate(R.id.home_fragment),
                                                           throwable -> Toast.makeText(
                                                                   root.getContext(),
                                                                   "Failed to delete offer, tyr later",
                                                                   Toast.LENGTH_SHORT
                                                           )
                                                   )
                                                   .dispose());

        return root;
    }
}