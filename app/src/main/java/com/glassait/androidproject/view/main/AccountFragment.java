package com.glassait.androidproject.view.main;

import android.content.Intent;
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
import com.glassait.androidproject.common.utils.button.BackButton;
import com.glassait.androidproject.common.utils.file.Cache;
import com.glassait.androidproject.common.utils.file.UUID;
import com.glassait.androidproject.common.utils.secret.Secret;
import com.glassait.androidproject.common.utils.secret.StoreLocalData;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.User;

import org.json.JSONObject;

public class AccountFragment extends Fragment {
    // Database part
    private final AppDatabase mAppDatabase = Builder.getInstance()
                                                    .getAppDatabase();
    private final UserDao     mUserDao     = mAppDatabase.userDao();
    // View part
    private       EditText    mFirstNameEt;
    private       EditText    mLastNameEt;
    private       EditText    mEmailEt;
    private       EditText    mPhoneEt;
    private       EditText    mAddressEt;
    private       EditText    mPostCodeEt;
    private       EditText    mCityEt;
    private       EditText    mCountryEt;
    // Common
    private       String      mFirstName;
    private       String      mLastName;
    private       String      mEmail;
    private       String      mPhone;
    private       String      mAddress;
    private       String      mPostCode;
    private       String      mCity;
    private       String      mCountry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(
                R.layout.fragment_account,
                container,
                false
        );

        NavController navController = NavHostFragment.findNavController(this);

        new BackButton(
                root,
                v -> navController.navigate(R.id.home_fragment)
        );

        User currentUser = StoreLocalData.getInstance()
                                         .getUser();
        UUID uuidBefore = new UUID(
                root.getContext(),
                currentUser.email
        );

        TextView modifyButton = root.findViewById(R.id.fragment_account_modify_btn);
        modifyButton.setOnClickListener(v -> {
            var ref = new Object() {
                boolean uuidDeleted = false;
            };
            if (mFirstName != null) {
                currentUser.firstName = mFirstName;
            }
            if (mLastName != null) {
                currentUser.lastName = mLastName;
            }
            if (mEmail != null) {
                currentUser.email = mEmail;
                ref.uuidDeleted = uuidBefore.deleteUUIDFile();
            }
            if (mPhone != null) {
                currentUser.phone = mPhone;
            }
            if (mAddress != null) {
                currentUser.address.setStreet(mAddress);
            }
            if (mCity != null) {
                currentUser.address.setCity(mCity);
            }
            if (mPostCode != null) {
                currentUser.address.setPostalCode(mPostCode);
            }
            if (mCountry != null) {
                currentUser.address.setCountry(mCountry);
            }
            mUserDao.update(currentUser)
                    .subscribe(
                            () -> {
                                // Store user id in file for auto connection
                                JSONObject data = new JSONObject().put(
                                                                          "email",
                                                                          currentUser.email
                                                                  )
                                                                  .put(
                                                                          "uuid",
                                                                          currentUser.uuid
                                                                  )
                                                                  .put(
                                                                          "uid",
                                                                          currentUser.uid
                                                                  );
                                Cache cache = new Cache(
                                        Secret.USER_FILE,
                                        root.getContext()
                                );
                                cache.createFile();
                                cache.storeDataInFile(data.toString());

                                // If the mail is change modify the UUID file
                                if (ref.uuidDeleted) {
                                    UUID uuidAfter = new UUID(
                                            root.getContext(),
                                            currentUser.email
                                    );
                                    uuidAfter.setUUID(currentUser.uuid);
                                    uuidAfter.generateUUID();
                                }

                                // Store the new user in the StoreLocalData
                                StoreLocalData.getInstance()
                                              .setUser(currentUser);

                                Toast.makeText(
                                             root.getContext(),
                                             root.getResources()
                                                 .getString(R.string.toast_account_updated),
                                             Toast.LENGTH_SHORT
                                     )
                                     .show();

                                // Reset the Activity
                                Intent intent = new Intent(
                                        root.getContext(),
                                        SecondActivity.class
                                );
                                startActivity(intent);
                            },
                            Throwable::printStackTrace
                    )
                    .dispose();
        });

        mFirstNameEt = root.findViewById(R.id.fragment_account_first_name_et);
        mFirstNameEt.setText(currentUser.firstName);
        mFirstNameEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !mFirstNameEt.getText()
                                          .toString()
                                          .equals(currentUser.firstName)) {
                mFirstName = mFirstNameEt.getText()
                                         .toString();
                modifyButton.setVisibility(View.VISIBLE);
            }
        });

        mLastNameEt = root.findViewById(R.id.fragment_account_last_name_et);
        mLastNameEt.setText(currentUser.lastName);
        mLastNameEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !mLastNameEt.getText()
                                         .toString()
                                         .equals(currentUser.lastName)) {
                mLastName = mLastNameEt.getText()
                                       .toString();
                modifyButton.setVisibility(View.VISIBLE);
            }
        });

        mEmailEt = root.findViewById(R.id.fragment_account_email_et);
        mEmailEt.setText(currentUser.email);
        mEmailEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !mEmailEt.getText()
                                      .toString()
                                      .equals(currentUser.email)) {
                mEmail = mEmailEt.getText()
                                 .toString();
                modifyButton.setVisibility(View.VISIBLE);
            }
        });

        mPhoneEt = root.findViewById(R.id.fragment_account_phone_et);
        mPhoneEt.setText(currentUser.phone);
        mPhoneEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !mPhoneEt.getText()
                                      .toString()
                                      .equals(currentUser.phone)) {
                mPhone = mPhoneEt.getText()
                                 .toString();
                modifyButton.setVisibility(View.VISIBLE);
            }
        });

        mAddressEt = root.findViewById(R.id.fragment_account_address_et);
        mAddressEt.setText(currentUser.address.getStreet());
        mAddressEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !mAddressEt.getText()
                                        .toString()
                                        .equals(currentUser.address.getStreet())) {
                mAddress = mAddressEt.getText()
                                     .toString();
                modifyButton.setVisibility(View.VISIBLE);
            }
        });

        mPostCodeEt = root.findViewById(R.id.fragment_account_postal_code_et);
        mPostCodeEt.setText(currentUser.address.getPostalCode());
        mPostCodeEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !mPostCodeEt.getText()
                                         .toString()
                                         .equals(currentUser.address.getPostalCode())) {
                mPostCode = mPostCodeEt.getText()
                                       .toString();
                modifyButton.setVisibility(View.VISIBLE);
            }
        });

        mCityEt = root.findViewById(R.id.fragment_account_city_et);
        mCityEt.setText(currentUser.address.getCity());
        mCityEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !mCityEt.getText()
                                     .toString()
                                     .equals(currentUser.address.getCity())) {
                mCity = mCityEt.getText()
                               .toString();
                modifyButton.setVisibility(View.VISIBLE);
            }
        });

        mCountryEt = root.findViewById(R.id.fragment_account_country_et);
        mCountryEt.setText(currentUser.address.getCountry());
        mCountryEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !mCountryEt.getText()
                                        .toString()
                                        .equals(currentUser.address.getCountry())) {
                mCountry = mCountryEt.getText()
                                     .toString();
                modifyButton.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }
}