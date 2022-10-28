package com.glassait.androidproject.view;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.Email;
import com.glassait.androidproject.common.utils.Phone;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.User;

public class SignUpFragment extends Fragment {
    // Database part
    private final AppDatabase mAppDatabase = Builder.getInstance()
                                                    .getAppDatabase();
    private final UserDao     mUserDao     = mAppDatabase.userDao();
    // View part
    private       View        mRoot;
    private       EditText    mFirstNameEt;
    private       EditText    mLastNameEt;
    private       EditText    mEmailEt;
    private       EditText    mPhoneEt;
    private       EditText    mAddressEt;
    private       EditText    mPostCodeEt;
    private       EditText    mCityEt;
    private       EditText    mCountryEt;
    private       EditText    mPasswordEt;
    // Common part
    private final Email       mEmail       = new Email();
    private final Phone       mPhone       = new Phone();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot = inflater.inflate(
                R.layout.fragment_sign_up,
                container,
                false
        );

        TextView backButton = mRoot.findViewById(R.id.sign_up_back_btn);

        NavController navController = NavHostFragment.findNavController(this);
        backButton.setOnClickListener(view -> navController.navigate(R.id.startMenu));

        mFirstNameEt = mRoot.findViewById(R.id.sign_up_first_name_et);
        mFirstNameEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mFirstNameEt
        ));

        mLastNameEt = mRoot.findViewById(R.id.sign_up_last_name_et);
        mLastNameEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mLastNameEt
        ));

        mEmailEt = mRoot.findViewById(R.id.sign_up_email_et);
        mEmailEt.setOnKeyListener((v, key, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP || key == 67) {
                checkEmail();
            }
            return false;
        });

        mPhoneEt = mRoot.findViewById(R.id.sign_up_phone_et);
        mPhoneEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mPhoneEt
        ));

        mAddressEt = mRoot.findViewById(R.id.sign_up_address_et);
        mAddressEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mAddressEt
        ));

        mPostCodeEt = mRoot.findViewById(R.id.sign_up_postal_code_et);
        mPostCodeEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mPostCodeEt
        ));

        mCityEt = mRoot.findViewById(R.id.sign_up_city_et);
        mCityEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mCityEt
        ));

        mCountryEt = mRoot.findViewById(R.id.sign_up_country_et);
        mCountryEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mCountryEt
        ));

        mPasswordEt = mRoot.findViewById(R.id.sign_up_password_et);
        mPasswordEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mPasswordEt
        ));

        TextView mSignUpButton = mRoot.findViewById(R.id.sign_up_register_btn);
        mSignUpButton.setOnClickListener(this::onClickRegister);

        return mRoot;
    }

    private void onClickRegister(View view) {
        if (checkIfEmailIsAlreadyUse()) {
            Toast.makeText(
                         mRoot.getContext(),
                         R.string.error_email_already_used,
                         Toast.LENGTH_SHORT
                 )
                 .show();
        } else if (checkIfEtIsFilled(mFirstNameEt) && checkIfEtIsFilled(mLastNameEt)
                && checkIfEtIsFilled(mEmailEt) && checkIfEtIsFilled(mPhoneEt)
                && checkIfEtIsFilled(mAddressEt) && checkIfEtIsFilled(mCityEt)
                && checkIfEtIsFilled(mCountryEt) && checkIfEtIsFilled(mPasswordEt)) {

            if (checkEmail()) {
                createAndInsertUserInDb();
            } else {
                Toast.makeText(
                             mRoot.getContext(),
                             R.string.error_inccorect_email,
                             Toast.LENGTH_SHORT
                     )
                     .show();
            }
        } else {
            Toast.makeText(
                         mRoot.getContext(),
                         R.string.error_all_fields_not_filled,
                         Toast.LENGTH_SHORT
                 )
                 .show();
        }
    }

    /**
     * Check if the EditText field is not empty
     *
     * @param et EditText
     *
     * @return boolean
     */
    private boolean checkIfEtIsFilled(@NonNull EditText et) {
        if (et.getText()
              .toString()
              .length() == 0) {
            et.setError(getString(R.string.error_cannot_be_empty));
            return false;
        }
        return true;
    }

    /**
     * Check if the email given is already in the database
     *
     * @return boolean
     *
     * @see #checkIfEtIsFilled(EditText)
     * @see UserDao#getUserFromEmail(String)
     */
    private boolean checkIfEmailIsAlreadyUse() {
        final User[] user = new User[1];
        if (checkIfEtIsFilled(mEmailEt)) {
            mUserDao.getUserFromEmail(mEmailEt.getText()
                                              .toString())
                    .subscribe(
                            userGet -> user[0] = userGet,
                            throwable -> user[0] = null
                    )
                    .dispose();
        }

        return user[0] != null;
    }

    /**
     * Logic for handling the onFocusChange event
     *
     * @param ignoredView View
     * @param hasFocus    boolean
     * @param input       EditText
     *
     * @see #checkIfEtIsFilled(EditText)
     */
    private void onFocusChange(View ignoredView, boolean hasFocus, EditText input) {
        if (hasFocus) checkIfEtIsFilled(input);
    }

    /**
     * Check if the email is correctly formatted
     * If not set an error on the input
     *
     * @return boolean
     *
     * @see Email#checkEmail()
     * @see Email#setEmail(EditText)
     * @see EditText#setError(CharSequence)
     */
    private boolean checkEmail() {
        mEmail.setEmail(mEmailEt);
        if (!mEmail.checkEmail()) {
            mEmailEt.setError(mRoot.getResources()
                                   .getString(R.string.error_inccorect_email) + ": "
                    + "id@domain_name.domain_extension");
            return false;
        }
        return true;
    }

    /**
     * Create the new user and insert it in the database
     *
     * @see User#User(String, String, String, String, String, String, String, String)
     * @see UserDao#insert(User)
     */
    private void createAndInsertUserInDb() {
        User user = new User(
                mFirstNameEt.getText()
                            .toString(),
                mLastNameEt.getText()
                           .toString(),
                mEmailEt.getText()
                        .toString(),
                mPhoneEt.getText()
                        .toString(),
                mAddressEt.getText()
                          .toString(),
                mCityEt.getText()
                       .toString(),
                mCountryEt.getText()
                          .toString(),
                mPasswordEt.getText()
                           .toString()
        );

        // TODO: Change the complete part in the subscribe when the second activity is created
        mUserDao.insert(user)
                .subscribe(
                        () -> Toast.makeText(
                                           mRoot.getContext(),
                                           "User insert in database",
                                           Toast.LENGTH_SHORT
                                   )
                                   .show(),
                        throwable -> Toast.makeText(
                                                  mRoot.getContext(),
                                                  R.string.error_went_wrong_database,
                                                  Toast.LENGTH_SHORT
                                          )
                                          .show()
                )
                .dispose();
    }
}