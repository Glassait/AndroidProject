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
import com.glassait.androidproject.common.utils.file.Cache;
import com.glassait.androidproject.common.utils.file.UUID;
import com.glassait.androidproject.common.utils.secret.Secret;
import com.glassait.androidproject.common.utils.secret.StoreLocalData;
import com.glassait.androidproject.common.utils.validator.EmailValidator;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.User;
import com.glassait.androidproject.view.main.SecondActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInFragment extends EmailValidator {
    // Database part
    private final AppDatabase   mAppDatabase = Builder.getInstance()
                                                      .getAppDatabase();
    private final UserDao       mUserDao     = mAppDatabase.userDao();
    // View
    private       View          mRoot;
    private       NavController mNavigation;
    private       EditText      mEmailEt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot = inflater.inflate(
                R.layout.fragment_sign_in,
                container,
                false
        );
        mNavigation = NavHostFragment.findNavController(this);

        TextView backButton = mRoot.findViewById(R.id.fragment_sign_in_back_btn);
        backButton.setOnClickListener(view -> mNavigation.navigate(R.id.start_menu_fragment));

        // Email editText
        mEmailEt = mRoot.findViewById(R.id.fragment_sign_in_email_et);
        mEmailEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) checkEmail(
                    mEmailEt,
                    mRoot
            );
        });

        TextView signInButton = mRoot.findViewById(R.id.fragment_sign_in_connection_button);
        signInButton.setOnClickListener(this::onClickListener);

        return mRoot;
    }

    /**
     * Logic for the click event
     * <p><br>
     * First of all check if the EditText is filled and the email is correctly formatted
     * <p><br>
     * After we get the user for the database with the email. If no user found send
     * {@link Toast#makeText(Context, int, int)}.
     * <p><br>
     * After if there is some uuid store on the phone and if it's the same in the database. If
     * not generate a new UUID and update the database.
     * <p>
     * This part normally would be a email send to the user with a link for creating a new uuid
     * and verifies the use but I didn't find how implementing some API for sending a email.
     * <p><br>
     * The function is also creating a file with the email and the uuid of the user for automatic
     * connection. The data is stored in JSONObject shape.
     *
     * @see #checkIfEtIsFilled(EditText)
     * @see #checkEmail(EditText, View)
     * @see UserDao#getUserFromEmail(String)
     * @see UserDao#update(User)
     * @see UUID#UUID(Context, String)
     * @see UUID#generateUUID()
     * @see UUID#storeUUID()
     * @see UUID#readUUIDFile()
     * @see UUID#getUuid()
     * @see Toast#makeText(Context, int, int)
     * @see NavController#navigate(int)
     * @see JSONObject#JSONObject()
     * @see JSONObject#put(String, int)
     * @see Cache#Cache(String, Context)
     * @see Cache#createFile()
     * @see Cache#storeDataInFile(byte[])
     */
    private void onClickListener(View ignoredView) {
        if (checkIfEtIsFilled(mEmailEt) && checkEmail(
                mEmailEt,
                mRoot
        )) {
            final User[] user = new User[1];
            mUserDao.getUserFromEmail(email.getEmail())
                    .subscribe(
                            //Success
                            userGet -> user[0] = userGet,
                            //Failure
                            throwable -> user[0] = null
                    )
                    .dispose();

            if (user[0] != null) {
                UUID uuid_cls = new UUID(
                        mRoot.getContext(),
                        email.getEmail()
                );
                java.util.UUID uuid = uuid_cls.readUUIDFile();

                if (uuid != null && uuid.equals(user[0].uuid)) {
                    // Store user id in file for auto connection
                    JSONObject data;
                    try {
                        data = new JSONObject().put(
                                                       "email",
                                                       user[0].email
                                               )
                                               .put(
                                                       "uuid",
                                                       user[0].uuid
                                               )
                                               .put(
                                                       "uid",
                                                       user[0].uid
                                               );
                        Cache cache = new Cache(
                                Secret.USER_FILE,
                                mRoot.getContext()
                        );
                        cache.createFile();
                        cache.storeDataInFile(data.toString()
                                                  .getBytes());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    StoreLocalData.getInstance()
                                  .setUser(user[0]);

                    //Launch the second activity
                    Intent intent = new Intent(
                            mRoot.getContext(),
                            SecondActivity.class
                    );
                    startActivity(intent);
                } else {
                    uuid_cls.generateUUID();
                    uuid_cls.storeUUID();

                    User updatedUser = user[0];
                    updatedUser.uuid = uuid_cls.getUuid();
                    mUserDao.update(updatedUser)
                            .subscribe()
                            .dispose();

                    mNavigation.navigate(R.id.new_location_fragment);
                }
            } else {
                Toast.makeText(
                             mRoot.getContext(),
                             mRoot.getResources()
                                  .getString(R.string.error_failed_to_connect),
                             Toast.LENGTH_SHORT
                     )
                     .show();
            }
        }
    }
}