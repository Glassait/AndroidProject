package com.glassait.androidproject.view.start;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.button.BackButton;
import com.glassait.androidproject.common.utils.checker.Address;
import com.glassait.androidproject.common.utils.checker.Phone;
import com.glassait.androidproject.common.utils.file.Cache;
import com.glassait.androidproject.common.utils.secret.Secret;
import com.glassait.androidproject.common.utils.secret.StoreLocalData;
import com.glassait.androidproject.common.utils.validator.EmailValidator;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.User;
import com.glassait.androidproject.view.main.ScanningActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class SignUpFragment extends EmailValidator {
    // Database part
    private final AppDatabase         mAppDatabase      = Builder.getInstance()
                                                                 .getAppDatabase();
    private final UserDao             mUserDao          = mAppDatabase.userDao();
    // View part
    private       View                mRoot;
    private       EditText            mFirstNameEt;
    private       EditText            mLastNameEt;
    private       EditText            mEmailEt;
    private       EditText            mPhoneEt;
    private       EditText            mAddressEt;
    private       EditText            mPostCodeEt;
    private       EditText            mCityEt;
    private       EditText            mCountryEt;
    // Common part
    private final Phone               mPhone            = new Phone();
    private final ArrayList<EditText> editTextArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot = inflater.inflate(
                R.layout.fragment_sign_up,
                container,
                false
        );

        NavController navController = NavHostFragment.findNavController(this);

        new BackButton(
                mRoot,
                v -> navController.navigate(R.id.start_menu_fragment)
        );

        // First name editText
        mFirstNameEt = mRoot.findViewById(R.id.fragment_sign_up_first_name_et);
        editTextArrayList.add(mFirstNameEt);
        mFirstNameEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mFirstNameEt
        ));

        // Last name editText
        mLastNameEt = mRoot.findViewById(R.id.fragment_sign_up_last_name_et);
        editTextArrayList.add(mLastNameEt);
        mLastNameEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mLastNameEt
        ));

        // Email editText
        mEmailEt = mRoot.findViewById(R.id.fragment_sign_up_email_et);
        editTextArrayList.add(mEmailEt);
        mEmailEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) checkEmail(
                    mEmailEt,
                    mRoot
            );
        });

        // Phone editText
        mPhoneEt = mRoot.findViewById(R.id.fragment_sign_up_phone_et);
        editTextArrayList.add(mPhoneEt);
        mPhoneEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) checkPhone();
        });

        // Address editText
        mAddressEt = mRoot.findViewById(R.id.fragment_sign_up_address_et);
        editTextArrayList.add(mAddressEt);
        mAddressEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mAddressEt
        ));

        // Postal code editText
        mPostCodeEt = mRoot.findViewById(R.id.fragment_sign_up_postal_code_et);
        editTextArrayList.add(mPostCodeEt);
        mPostCodeEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mPostCodeEt
        ));

        // City editText
        mCityEt = mRoot.findViewById(R.id.fragment_sign_up_city_et);
        editTextArrayList.add(mCityEt);
        mCityEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mCityEt
        ));

        // Country editText
        mCountryEt = mRoot.findViewById(R.id.fragment_sign_up_country_et);
        editTextArrayList.add(mCountryEt);
        mCountryEt.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(
                v,
                hasFocus,
                mCountryEt
        ));

        TextView signUpButton = mRoot.findViewById(R.id.fragment_sign_up_register_btn);
        signUpButton.setOnClickListener(this::onClickListener);

        return mRoot;
    }

    /**
     * Logic for the sign up button on click event.
     * <p>
     * First of all check if the email is already registered. After check if all EditText are
     * filled. After check if the mail and the phone are correctly formed. Finally create the user
     * in the database.
     * <p>
     * All error are display with {@link Toast} message.
     *
     * @param view View
     *
     * @see #checkIfEmailIsAlreadyUse()
     * @see #checkIfAllEtAreFilled()
     * @see #checkEmail(EditText, View)
     * @see #checkPhone()
     * @see #createAndInsertUserInDb()
     * @see Toast#makeText(Context, int, int)
     */
    private void onClickListener(View view) {
        if (checkIfEmailIsAlreadyUse()) {
            Toast.makeText(
                         mRoot.getContext(),
                         R.string.error_email_already_used,
                         Toast.LENGTH_SHORT
                 )
                 .show();
            mEmailEt.setError(mRoot.getResources()
                                   .getString(R.string.error_email_already_used));
        } else if (checkIfAllEtAreFilled()) {
            if (checkEmail(
                    mEmailEt,
                    mRoot
            )) {
                if (checkPhone()) {
                    createAndInsertUserInDb();
                } else {
                    Toast.makeText(
                                 mRoot.getContext(),
                                 R.string.error_incorrect_phone,
                                 Toast.LENGTH_SHORT
                         )
                         .show();
                }
            } else {
                Toast.makeText(
                             mRoot.getContext(),
                             R.string.error_incorrect_email,
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
     * Check if all EditText of the screen are filled
     *
     * @return True if all EditText of the screen are filled, false otherwise
     *
     * @see #checkIfEtIsFilled(EditText)
     */
    private boolean checkIfAllEtAreFilled() {
        for (EditText et : editTextArrayList) {
            if (!checkIfEtIsFilled(et)) return false;
        }
        return true;
    }

    /**
     * Check if the email given is already in the database.
     * <p>
     * Before checking if the email is used we check if the email editText is empty or not.
     * <p>
     * Do a search of the mail in the database, if a user with this email if found return false,
     * otherwise return true.
     *
     * @return True if the email is already in the database, false otherwise
     *
     * @see #checkIfEtIsFilled(EditText)
     * @see UserDao#getUserFromEmail(String)
     */
    private boolean checkIfEmailIsAlreadyUse() {
        final User[] user = new User[1];
        if (checkIfEtIsFilled(mEmailEt)) {
            mUserDao.getUserFromEmail(email.getEmail())
                    .subscribe(
                            //Success
                            userGet -> user[0] = userGet,
                            //Failure
                            throwable -> user[0] = null
                    )
                    .dispose();
        }

        return user[0] != null;
    }

    /**
     * Logic for handling the onFocusChange event.
     * <p>
     * Is the element lose the focus then we check if is was filled or not.
     *
     * @param ignoredView View
     * @param hasFocus    If the element is focused or not
     * @param input       The editText where the event is applied
     *
     * @see #checkIfEtIsFilled(EditText)
     */
    private void onFocusChange(View ignoredView, boolean hasFocus, EditText input) {
        if (!hasFocus) checkIfEtIsFilled(input);
    }

    /**
     * Check if the phone is correctly formatted and encode it.
     * <p>
     * If not set an error on the input and return false.
     * <p>
     * Use the {@link Phone#checkPhone()} to realise the check
     *
     * @return True if the phone is correctly formatted, false otherwise
     *
     * @see Phone#setPhone(String)
     * @see Phone#encode(String)
     * @see Phone#checkPhone()
     * @see EditText#setError(CharSequence)
     */
    private boolean checkPhone() {
        mPhone.setPhone(mPhone.encode(mPhoneEt.getText()
                                              .toString()));
        if (!mPhone.checkPhone()) {
            mPhoneEt.setError(mRoot.getResources()
                                   .getString(R.string.error_incorrect_phone) + ":" + " "
                    + "+calling_codes_phone_number");
            return false;
        }
        return true;
    }

    /**
     * Create the new user and insert it in the database
     * <p>
     * Create also a uuid for the user and store it in a file.
     * <p>
     * If the insert is successful/fail then display a {@link Toast} message
     * <p><br>
     * The function is also creating a file with the email and the uuid of the user for automatic
     * connection. The data is stored in JSONObject shape.
     *
     * @see User#User(EditText, EditText, String, EditText, Address, UUID)
     * @see UserDao#insert(User)
     * @see com.glassait.androidproject.common.utils.file.UUID#UUID(Context, String)
     * @see com.glassait.androidproject.common.utils.file.UUID#generateUUID()
     * @see com.glassait.androidproject.common.utils.file.UUID#storeUUID()
     * @see com.glassait.androidproject.common.utils.file.UUID#getUuid()
     * @see Toast#makeText(Context, int, int)
     * @see JSONObject#JSONObject()
     * @see JSONObject#put(String, int)
     * @see Cache#Cache(String, Context)
     * @see Cache#createFile()
     * @see Cache#storeDataInFile(String)
     */
    private void createAndInsertUserInDb() {
        com.glassait.androidproject.common.utils.file.UUID uuid =
                new com.glassait.androidproject.common.utils.file.UUID(
                        mRoot.getContext(),
                        email.getEmail()
                );
        uuid.generateUUID();
        uuid.storeUUID();

        User user = new User(
                mFirstNameEt,
                mLastNameEt,
                email.getEmail(),
                mPhoneEt,
                new Address(
                        mRoot.getContext(),
                        mAddressEt,
                        mPostCodeEt,
                        mCityEt,
                        mCountryEt
                ),
                uuid.getUuid()
        );

        mUserDao.insert(user)
                .subscribe(
                        //Success
                        () -> {
                            // Store user id in file for auto connection
                            JSONObject data = new JSONObject().put(
                                                                      "email",
                                                                      user.email
                                                              )
                                                              .put(
                                                                      "uuid",
                                                                      user.uuid
                                                              )
                                                              .put(
                                                                      "uid",
                                                                      user.uid
                                                              );
                            Cache cache = new Cache(
                                    Secret.USER_FILE,
                                    mRoot.getContext()
                            );
                            cache.createFile();
                            cache.storeDataInFile(data.toString());

                            StoreLocalData.getInstance()
                                          .setUser(user);

                            // Launch the second activity
                            Intent intent = new Intent(
                                    mRoot.getContext(),
                                    ScanningActivity.class
                            );
                            startActivity(intent);
                        },
                        //Failure
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