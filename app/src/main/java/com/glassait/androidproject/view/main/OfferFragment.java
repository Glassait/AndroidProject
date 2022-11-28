package com.glassait.androidproject.view.main;

import android.content.Intent;
import android.net.Uri;
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
import com.glassait.androidproject.common.utils.secret.StoreLocalData;
import com.glassait.androidproject.model.dao.OfferDao;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

public class OfferFragment extends Fragment {
    // Database part
    private final AppDatabase mAppDatabase = Builder.getInstance()
                                                    .getAppDatabase();
    private final OfferDao    mOfferDao    = mAppDatabase.offerDao();
    private final UserDao     mUserDao     = mAppDatabase.userDao();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View part
        View root = inflater.inflate(
                R.layout.fragment_offer,
                container,
                false
        );

        NavController navController = NavHostFragment.findNavController(this);

        final User[] displayUser = new User[1];
        User storedUser = StoreLocalData.getInstance()
                                        .getUser();
        Offer offer = StoreLocalData.getInstance()
                                    .getOffer();

        TextView isReservedTv = root.findViewById(R.id.fragment_offer_is_reserved_tv);
        TextView rightBtn     = root.findViewById(R.id.fragment_offer_right_btn);
        TextView leftBtn      = root.findViewById(R.id.fragment_offer_left_btn);

        rightBtn.setVisibility(View.GONE);
        leftBtn.setVisibility(View.GONE);

        if (offer.isReserved && offer.reservedBy != -1) {
            mUserDao.getUserFromUid(offer.reservedBy)
                    .subscribe(
                            u -> displayUser[0] = u,
                            Throwable::printStackTrace
                    )
                    .dispose();

            isReservedTv.setText(R.string.upper_label_reserved_by);
        } else if (storedUser.uid == offer.creatorId) {
            displayUser[0] = storedUser;

            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setOnClickListener(v -> mOfferDao.delete(offer)
                                                      .subscribe(
                                                              () -> navController.navigate(R.id.home_fragment),
                                                              throwable -> Toast.makeText(
                                                                      root.getContext(),
                                                                      "Failed to delete offer, tyr later",
                                                                      Toast.LENGTH_SHORT
                                                              )
                                                      )
                                                      .dispose());
        } else {
            mUserDao.getUserFromUid(offer.creatorId)
                    .subscribe(
                            u -> displayUser[0] = u,
                            Throwable::printStackTrace
                    )
                    .dispose();
            isReservedTv.setVisibility(View.GONE);

            leftBtn.setVisibility(View.VISIBLE);
            leftBtn.setText(R.string.upper_text_book);
            leftBtn.setOnClickListener(v -> {
                // TODO Go to the reservation screen
            });

            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setText(R.string.upper_label_email);
            rightBtn.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + displayUser[0].email));
                intent.putExtra(
                        Intent.EXTRA_SUBJECT,
                        "Information for the Offer : " + offer.title
                );
                startActivity(intent);
            });
        }


        TextView backButton = root.findViewById(R.id.fragment_offer_back_btn);
        backButton.setOnClickListener(view -> {
            System.out.println("Back button click on my offer fragment");
            navController.navigate(R.id.home_fragment);
            StoreLocalData.getInstance()
                          .setOffer(null);
        });

        TextView offerTitleEt = root.findViewById(R.id.fragment_offer_title_tv);
        offerTitleEt.setText(offer.title);

        EditText firstNameEt = root.findViewById(R.id.fragment_offer_first_name_et);
        firstNameEt.setText(displayUser[0].firstName);

        EditText lastNameEt = root.findViewById(R.id.fragment_offer_last_name_et);
        lastNameEt.setText(displayUser[0].lastName);

        EditText emailEt = root.findViewById(R.id.fragment_offer_email_et);
        emailEt.setText(displayUser[0].email);

        EditText phoneEt = root.findViewById(R.id.fragment_offer_phone_et);
        phoneEt.setText(displayUser[0].phone);

        EditText addressEt = root.findViewById(R.id.fragment_offer_address_et);
        addressEt.setText(displayUser[0].address.getStreet());

        EditText postCodeEt = root.findViewById(R.id.fragment_offer_postal_code_et);
        postCodeEt.setText(displayUser[0].address.getPostalCode());

        EditText cityEt = root.findViewById(R.id.fragment_offer_city_et);
        cityEt.setText(displayUser[0].address.getCity());

        EditText countryEt = root.findViewById(R.id.fragment_offer_country_et);
        countryEt.setText(displayUser[0].address.getCountry());

        EditText categoryEt = root.findViewById(R.id.fragment_offer_category_et);
        categoryEt.setText(offer.category);

        return root;
    }
}