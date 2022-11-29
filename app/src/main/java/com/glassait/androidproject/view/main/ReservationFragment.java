package com.glassait.androidproject.view.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
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
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

import java.util.Calendar;

public class ReservationFragment extends Fragment {
    // Database part
    private final AppDatabase mAppDatabase = Builder.getInstance()
                                                    .getAppDatabase();
    private final OfferDao    mOfferDao    = mAppDatabase.offerDao();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(
                R.layout.fragment_reservation,
                container,
                false
        );

        NavController navController = NavHostFragment.findNavController(this);

        // Config for the calendar view
        Calendar calendar       = Calendar.getInstance();
        int      year           = calendar.get(Calendar.YEAR);
        int      monthOfTheYear = calendar.get(Calendar.MONTH);
        int      dayOfTheMonth  = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(
                Calendar.DAY_OF_MONTH,
                dayOfTheMonth
        );

        // Code for showing calendar when clicking on edit text from https://www.geeksforgeeks.org/how-to-popup-datepicker-while-clicking-on-edittext-in-android/
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                root.getContext(),
                null,
                year,
                monthOfTheYear,
                dayOfTheMonth
        );
        datePickerDialog.getDatePicker()
                        .setMinDate(calendar.getTimeInMillis());

        TextView backButton = root.findViewById(R.id.fragment_reservation_back_btn);
        backButton.setOnClickListener(v -> navController.navigate(R.id.offer_fragment));

        EditText borrowingDate = root.findViewById(R.id.fragment_reservation_borrowing_date_et);
        borrowingDate.setInputType(InputType.TYPE_NULL);
        borrowingDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePickerDialog.setOnDateSetListener((v1, yearPicked, monthOfYearPicked, dayOfMonthPicked) -> {
                    String date =
                            (dayOfMonthPicked + "/" + (monthOfYearPicked + 1) + "/" + yearPicked);
                    borrowingDate.setText(date);
                });
                datePickerDialog.show();
            }
        });

        EditText returnDate = root.findViewById(R.id.fragment_reservation_return_date_et);
        returnDate.setInputType(InputType.TYPE_NULL);
        returnDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePickerDialog.setOnDateSetListener((v1, yearPicked, monthOfYearPicked, dayOfMonthPicked) -> {
                    String date =
                            (dayOfMonthPicked + "/" + (monthOfYearPicked + 1) + "/" + yearPicked);
                    returnDate.setText(date);
                });
                datePickerDialog.show();
            }
        });

        TextView book = root.findViewById(R.id.fragment_reservation_book_button);
        book.setOnClickListener(v -> {
            Offer offer = StoreLocalData.getInstance()
                                        .getOffer();
            User user = StoreLocalData.getInstance()
                                      .getUser();

            offer.reservedBy = user.uid;
            offer.isReserved = true;
            offer.bookingDate = borrowingDate.getText()
                                             .toString();
            offer.returnDate = returnDate.getText()
                                         .toString();

            mOfferDao.update(offer)
                     .subscribe(
                             () -> {
                                 Toast.makeText(
                                              root.getContext(),
                                              "Reservation Send",
                                              Toast.LENGTH_SHORT
                                      )
                                      .show();
                                 navController.navigate(R.id.home_fragment);
                             },
                             Throwable::printStackTrace
                     )
                     .dispose();
        });

        return root;
    }
}