package com.glassait.androidproject.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.file.GetUserFromFile;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.User;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class CreateOfferFragment extends Fragment {
    // View part
    private       View        mRoot;
    private       ImageView   mToolImage;
    private       Uri         mImageUri;
    private       Chip        mSelectedChip;
    // Database part
    private final AppDatabase mAppDatabase = Builder.getInstance()
                                                    .getAppDatabase();
    private final UserDao     mUserDao     = mAppDatabase.userDao();
    private       User        mUser;

    // ResultLauncher part
    // Code from https://developer.android.com/training/permissions/requesting
    private final ActivityResultLauncher<String> requestPermissionLauncher       =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        if (isGranted) {
                            System.out.println("granted");
                            // Permission is granted. Continue the action or workflow in your
                            // app.
                        } else {
                            System.out.println("denied");
                            // Explain to the user that the feature is unavailable because the
                            // feature requires a permission that the user has denied. At the
                            // same time, respect the user's decision. Don't link to system
                            // settings in an effort to convince the user to change their
                            // decision.
                        }
                    }
            );
    // Code from https://stackoverflow.com/a/42605330 with modification of myself.
    private final ActivityResultLauncher<Intent> requestImageFromGalleryLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK
                                && result.getData() != null) {
                            mImageUri = result.getData()
                                              .getData();
                            mToolImage.setImageURI(mImageUri);
                            mToolImage.setBackgroundColor(mRoot.getResources()
                                                               .getColor(
                                                                       R.color.transparent,
                                                                       null
                                                               ));
                        } else {
                            Toast.makeText(
                                         mRoot.getContext(),
                                         mRoot.getResources()
                                              .getString(R.string.error_during_upload_image),
                                         Toast.LENGTH_SHORT
                                 )
                                 .show();
                        }
                    }
            );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(
                R.layout.fragment_create_offer,
                container,
                false
        );

        getUser();

        // Create a Cloud Storage reference from the app
        StorageReference storageRef =
                FirebaseStorage.getInstance("gs://androidproject-227a9.appspot.com")
                               .getReference();

        NavController navController = NavHostFragment.findNavController(this);

        // On click listener to return to the previous fragment
        TextView backButton = mRoot.findViewById(R.id.create_offer_back_btn);
        backButton.setOnClickListener(view -> navController.navigate(R.id.home_fragment));

        // On click listener to get a image from the gallery (also check the permission)
        mToolImage = mRoot.findViewById(R.id.create_offer_tool_image);
        mToolImage.setOnClickListener(View -> {
            //Code from https://developer.android.com/training/permissions/requesting
            int permission = ContextCompat.checkSelfPermission(
                    mRoot.getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
            );

            if (permission == PackageManager.PERMISSION_DENIED) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            // Code from https://stackoverflow.com/a/42605330 with modification of myself.
            if (permission == PackageManager.PERMISSION_GRANTED
                    || Build.MODEL.contains("sdk_gphone")) {
                requestImageFromGalleryLauncher.launch(new Intent(Intent.ACTION_PICK).setType("image/*"));
            }
        });

        EditText title = mRoot.findViewById(R.id.create_offer_title_et);
        title.setOnFocusChangeListener((v, hasFocus) -> OnFocusChangeListener(
                title,
                hasFocus
        ));

        ChipGroup chipGroup = mRoot.findViewById(R.id.create_offer_chip_group);
        chipGroup.setSelectionRequired(true);
        chipGroup.setOnCheckedStateChangeListener((group, checkedId) -> OnCheckedStateChangeListener(
                chipGroup,
                checkedId
        ));

        TextView createOfferButton = mRoot.findViewById(R.id.create_offer_create_offer_btn);
        createOfferButton.setOnClickListener(v -> {
            if (mImageUri == null) {
                Toast.makeText(
                             mRoot.getContext(),
                             "You must upload an image",
                             Toast.LENGTH_SHORT
                     )
                     .show();
                mToolImage.setBackgroundColor(mRoot.getResources()
                                                   .getColor(
                                                           R.color.error,
                                                           null
                                                   ));
            } else if (title.getText()
                            .toString()
                            .length() < 1) {
                title.setError(mRoot.getResources()
                                    .getString(R.string.error_cannot_be_empty));
            } else if (title.getText()
                            .toString()
                            .length() > 30) {
                title.setError(mRoot.getResources()
                                    .getString(R.string.error_to_long_30));
            } else if (mSelectedChip == null) {
                Toast.makeText(
                             mRoot.getContext(),
                             "You have to select one category",
                             Toast.LENGTH_SHORT
                     )
                     .show();
                chipGroup.setBackgroundColor(mRoot.getResources()
                                                  .getColor(
                                                          R.color.error,
                                                          null
                                                  ));
            } else {
                System.out.println("Everything is ok");
                System.out.println(mImageUri);
                StorageReference mountainImagesRef = storageRef.child(String.valueOf(mImageUri));
            }
        });

        return mRoot;
    }

    /**
     * Get the id of checked chip and store it.
     * <p>
     * Set the background color of the group transparent
     *
     * @param chipGroup The chipGroup to attache the listener
     * @param checkedId The id of the checked chip
     *
     * @see ChipGroup#setBackgroundColor(int)
     */
    private void OnCheckedStateChangeListener(ChipGroup chipGroup, List<Integer> checkedId) {
        mSelectedChip = mRoot.findViewById(checkedId.get(0));
        chipGroup.setBackgroundColor(mRoot.getResources()
                                          .getColor(
                                                  R.color.transparent,
                                                  null
                                          ));
    }

    /**
     * If the editText doesn't have the focus, we check if it's filled and if the field length is
     * less than 30 characters.
     * <p>
     * Set an error message on the editText when one of this conditions is false.
     * <p>
     * Also close the keyboard when the editText lose the focus.
     *
     * @param editText The editText to attache the listener
     * @param hasFocus The boolean value to now if the editText has the focus
     *
     * @see EditText#setError(CharSequence)
     * @see InputMethodManager#hideSoftInputFromWindow(IBinder, int)
     */
    private void OnFocusChangeListener(EditText editText, boolean hasFocus) {
        if (!hasFocus) {
            if (editText.getText()
                        .toString()
                        .length() < 1) {
                editText.setError(mRoot.getResources()
                                       .getString(R.string.error_cannot_be_empty));
            } else if (editText.getText()
                               .toString()
                               .length() > 30) {
                editText.setError(mRoot.getResources()
                                       .getString(R.string.error_to_long_30));
            }
            InputMethodManager imm = (InputMethodManager) mRoot.getContext()
                                                               .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(
                    editText.getWindowToken(),
                    0
            );
        }
    }

    /**
     * Get the user from data inside file.
     * <p>
     * Use thread to let the main thread handle the listener. The thread used is a daemon.
     *
     * @see GetUserFromFile#GetUserFromFile(Context)
     * @see GetUserFromFile#getUser()
     * @see Thread#Thread()
     * @see Thread#setDaemon(boolean)
     * @see Thread#start()
     */
    private void getUser() {
        GetUserFromFile getUser = new GetUserFromFile(mRoot.getContext());
        Thread          thread  = new Thread(getUser);
        thread.setDaemon(true);

        new Thread(() -> {
            thread.start();
            while (mUser == null) {
                mUser = getUser.getUser();
            }
        }).start();
    }
}
