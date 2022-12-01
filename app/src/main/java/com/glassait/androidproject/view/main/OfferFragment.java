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

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.button.BackButton;
import com.glassait.androidproject.common.utils.secret.StoreLocalData;
import com.glassait.androidproject.model.dao.OfferDao;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

import io.reactivex.rxjava3.functions.Action;

public class OfferFragment extends Fragment {
    // Database part
    private final AppDatabase mAppDatabase = Builder.getInstance()
                                                    .getAppDatabase();
    private final OfferDao    mOfferDao    = mAppDatabase.offerDao();
    private final UserDao     mUserDao     = mAppDatabase.userDao();
    // Common
    private       Offer       mOffer;
    private       User        mDisplayedUser;
    private       TextView    mRightBtn;
    private final Intent      mEmailIntent = new Intent(Intent.ACTION_SENDTO);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View part
        View root = inflater.inflate(
                R.layout.fragment_offer,
                container,
                false
        );

        NavController navController = NavHostFragment.findNavController(this);

        User currentUser = StoreLocalData.getInstance()
                                         .getUser();
        mOffer = StoreLocalData.getInstance()
                               .getOffer();

        TextView isReservedTv = root.findViewById(R.id.fragment_offer_is_reserved_tv);
        mRightBtn = root.findViewById(R.id.fragment_offer_right_btn);
        TextView leftBtn = root.findViewById(R.id.fragment_offer_left_btn);

        TextView borrowingDateTv = root.findViewById(R.id.fragment_offer_borrowing_date_tv);
        EditText borrowingDateEt = root.findViewById(R.id.fragment_offer_borrowing_date_et);
        TextView returnDateTv    = root.findViewById(R.id.fragment_offer_return_date_tv);
        EditText returnDateEt    = root.findViewById(R.id.fragment_offer_return_date_et);

        mRightBtn.setVisibility(View.GONE);
        leftBtn.setVisibility(View.GONE);

        if (mOffer.bookingDate == null) {
            borrowingDateTv.setVisibility(View.GONE);
            borrowingDateEt.setVisibility(View.GONE);
        }
        if (mOffer.returnDate == null) {
            returnDateTv.setVisibility(View.GONE);
            returnDateEt.setVisibility(View.GONE);
        }

        if (mOffer.isReserved && mOffer.reservedBy != -1) {
            // The offer is reserved
            if (!mOffer.validate) {
                // The offer is not validate is owner
                if (mOffer.reservedBy == currentUser.uid) {
                    // The offer is reserved by the current user
                    isReservedTv.setText(R.string.upper_label_owner_validate);
                    setButtonAction(
                            leftBtn,
                            R.string.upper_label_cancel,
                            v -> {
                                mOffer.resetReservation();
                                updateOffer(() -> {
                                    Toast.makeText(
                                                 root.getContext(),
                                                 root.getResources()
                                                     .getString(R.string.toast_reservation_canceled),
                                                 Toast.LENGTH_SHORT
                                         )
                                         .show();
                                    navController.navigate(R.id.home_fragment);
                                });
                            }
                    );
                    // See the owner information
                    setTheDisplayedUser(mOffer.creatorId);
                } else {
                    // The offer is checked by his owner
                    isReservedTv.setText(R.string.upper_label_you_need_to_confirm);
                    setButtonAction(
                            leftBtn,
                            R.string.upper_label_confirm,
                            v -> {
                                mOffer.validate = true;
                                updateOffer(() -> {
                                    Toast.makeText(
                                                 root.getContext(),
                                                 root.getResources()
                                                     .getString(R.string.toast_booking_validate),
                                                 Toast.LENGTH_SHORT
                                         )
                                         .show();
                                    navController.navigate(R.id.home_fragment);
                                });
                            }
                    );
                    // See the information of the booker
                    setTheDisplayedUser(mOffer.reservedBy);
                }
                rightBtnForEmail();
            } else {
                // The offer is validate by his owner
                if (mOffer.reservedBy == currentUser.uid) {
                    // The offer is reserved by the current user
                    isReservedTv.setText(R.string.upper_label_your_reservation_confirmed);
                    // See the information of the owner
                    setTheDisplayedUser(mOffer.creatorId);
                } else {
                    // The offer is checked is owner
                    isReservedTv.setText(R.string.upper_label_reserved_by);
                    // See the information of the booker
                    setTheDisplayedUser(mOffer.reservedBy);
                }
                rightBtnForEmail();
            }
        } else if (currentUser.uid == mOffer.creatorId) {
            // The offer is not reserved and the owner check is offer
            mDisplayedUser = currentUser;
            setButtonAction(
                    leftBtn,
                    R.string.upper_delete,
                    v -> mOfferDao.delete(mOffer)
                                  .subscribe(
                                          () -> navController.navigate(R.id.home_fragment),
                                          throwable -> Toast.makeText(
                                                  root.getContext(),
                                                  root.getResources()
                                                      .getString(R.string.toast_error_delete_offer),
                                                  Toast.LENGTH_SHORT
                                          )
                                  )
                                  .dispose()
            );
        } else {
            // See the mOffer of someone else (not reserved)
            setTheDisplayedUser(mOffer.creatorId);
            isReservedTv.setVisibility(View.GONE);
            setButtonAction(
                    leftBtn,
                    R.string.upper_text_book,
                    v -> navController.navigate(R.id.reservation_fragment)
            );
            rightBtnForEmail();
        }

        // Modify the view part
        new BackButton(
                root,
                v -> navController.navigate(R.id.home_fragment)
        );

        TextView offerTitleEt = root.findViewById(R.id.fragment_offer_title_tv);
        offerTitleEt.setText(mOffer.title);

        EditText firstNameEt = root.findViewById(R.id.fragment_offer_first_name_et);
        firstNameEt.setText(mDisplayedUser.firstName);

        EditText lastNameEt = root.findViewById(R.id.fragment_offer_last_name_et);
        lastNameEt.setText(mDisplayedUser.lastName);

        EditText emailEt = root.findViewById(R.id.fragment_offer_email_et);
        emailEt.setText(mDisplayedUser.email);

        EditText phoneEt = root.findViewById(R.id.fragment_offer_phone_et);
        phoneEt.setText(mDisplayedUser.phone);

        EditText addressEt = root.findViewById(R.id.fragment_offer_address_et);
        addressEt.setText(mDisplayedUser.address.getStreet());

        EditText postCodeEt = root.findViewById(R.id.fragment_offer_postal_code_et);
        postCodeEt.setText(mDisplayedUser.address.getPostalCode());

        EditText cityEt = root.findViewById(R.id.fragment_offer_city_et);
        cityEt.setText(mDisplayedUser.address.getCity());

        EditText countryEt = root.findViewById(R.id.fragment_offer_country_et);
        countryEt.setText(mDisplayedUser.address.getCountry());

        EditText categoryEt = root.findViewById(R.id.fragment_offer_category_et);
        categoryEt.setText(mOffer.category);

        borrowingDateEt.setText(mOffer.bookingDate != null ? mOffer.bookingDate : "");
        returnDateEt.setText(mOffer.returnDate != null ? mOffer.returnDate : "");

        return root;
    }

    /**
     * Function to simplify the number of use of the update query (i.e. juste call updateOffer
     * instead of .update.subscribe.dispose)
     *
     * @param action The action to perform is the query is complete
     */
    public void updateOffer(Action action) {
        mOfferDao.update(mOffer)
                 .subscribe(
                         action,
                         Throwable::printStackTrace
                 )
                 .dispose();
    }

    /**
     * Get the information of the given user and store it for display it later
     *
     * @param userId The is of the user to query
     */
    public void setTheDisplayedUser(int userId) {
        final User[] displayUser = new User[1];
        mUserDao.getUserFromUid(userId)
                .subscribe(
                        u -> displayUser[0] = u,
                        Throwable::printStackTrace
                )
                .dispose();
        mDisplayedUser = displayUser[0];
    }

    /**
     * Set the visibility to {@link View#VISIBLE} and set the text and the listener
     *
     * @param btn      The button to set action
     * @param resId    The text for the button
     * @param listener the listener for the click listener
     */
    public void setButtonAction(TextView btn, @StringRes int resId, View.OnClickListener listener) {
        btn.setVisibility(View.VISIBLE);
        btn.setText(resId);
        btn.setOnClickListener(listener);
    }

    /**
     * Set the right button in the offer layout to send email to the displayed user
     *
     * @see #setButtonAction(TextView, int, View.OnClickListener)
     */
    public void rightBtnForEmail() {
        setButtonAction(
                mRightBtn,
                R.string.upper_label_email,
                v -> {
                    mEmailIntent.setData(Uri.parse("mailto:" + mDisplayedUser.email));
                    startActivity(mEmailIntent);
                }
        );
    }
}