package com.glassait.androidproject.view.main.myOffer;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class CreateOfferFragment extends Fragment {
    // View part
    private       View        mRoot;
    //    private       ImageView            mToolImage;
    private       Chip        mSelectedChip;
    //    private       ParcelFileDescriptor mParcelFileDescriptor;
    // Database part
    private final AppDatabase mAppDatabase = Builder.getInstance()
                                                    .getAppDatabase();
    private final OfferDao    mOfferDao    = mAppDatabase.offerDao();
    private       User        mUser;

     /*//Fail to implements the upload to the firebase
    // ResultLauncher part
    // Code from https://developer.android.com/training/permissions/requesting
    private final ActivityResultLauncher<String> requestPermissionLauncher       =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        if (isGranted) {
                            System.out.println("granted");
                        } else {
                            System.out.println("denied");
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
                            Uri uri = result.getData()
                                            .getData();
                            try {
                                mParcelFileDescriptor = mRoot.getContext()
                                                             .getContentResolver()
                                                             .openFileDescriptor(
                                                                     uri,
                                                                     "r",
                                                                     null
                                                             );
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            mToolImage.setImageURI(uri);
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
            );*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(
                R.layout.fragment_create_offer,
                container,
                false
        );

        mUser = StoreLocalData.getInstance().getUser();

        // Fail to implements the upload to the firebase
        // Create a Cloud Storage reference from the app
//        StorageReference storageRef = FirebaseStorage.getInstance()
//                                                     .getReference();

        NavController navController = NavHostFragment.findNavController(this);

        // On click listener to return to the previous fragment
        TextView backButton = mRoot.findViewById(R.id.fragment_create_offer_back_btn);
        backButton.setOnClickListener(view -> navController.navigate(R.id.home_fragment));

         /* //Fail to implements the upload to the firebase
        // On click listener to get a image from the gallery (also check the permission)
        mToolImage = mRoot.findViewById(R.id.fragment_create_offer_tool_image);
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
        });*/

        EditText title = mRoot.findViewById(R.id.fragment_create_offer_title_et);
        title.setOnFocusChangeListener((v, hasFocus) -> OnFocusChangeListener(
                title,
                hasFocus
        ));

        ChipGroup chipGroup = mRoot.findViewById(R.id.fragment_create_offer_chip_group);
        chipGroup.setSelectionRequired(true);
        chipGroup.setOnCheckedStateChangeListener((group, checkedId) -> OnCheckedStateChangeListener(
                chipGroup,
                checkedId
        ));

        TextView createOfferButton =
                mRoot.findViewById(R.id.fragment_create_offer_create_offer_btn);
        createOfferButton.setOnClickListener(v -> {
            /*if (mParcelFileDescriptor == null) {
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
            } else*/
            if (title.getText()
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
                Offer offer = new Offer(
                        title.getText()
                             .toString(),
                        mSelectedChip.getText()
                                     .toString(),
                        "",
                        mUser.uid
                );

                offer.storage_ref = mUser.uid + "_offer_" + offer.uid;
                /*// Fail to implements the upload to the firebase
                StorageReference offerRef = storageRef.child(firebaseImageRef);

                // Get the data from an ImageView as bytes
                // Code from https://firebase.google.com/docs/storage/android/upload-files#java
                InputStream stream = new FileInputStream(mParcelFileDescriptor.getFileDescriptor());
                UploadTask uploadTask = offerRef.putStream(stream);
                uploadTask.addOnFailureListener(exception -> {
                              // Handle unsuccessful uploads
                              System.out.println("Failed to upload");
                              exception.printStackTrace();
                          })
                          .addOnSuccessListener(taskSnapshot -> {
                              // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                              // ...
                              System.out.println("Success to upload");
                              mOfferDao.insert(offer)
                                       .subscribe(
                                               () -> System.out.println("Success to put inside the database"),
                                               Throwable::printStackTrace
                                       )
                                       .dispose();
                          });*/
                mOfferDao.insert(offer)
                         .subscribe(
                                 () -> System.out.println("Success to put inside the database"),
                                 Throwable::printStackTrace
                         )
                         .dispose();
                Toast.makeText(
                             mRoot.getContext(),
                             "Offer create",
                             Toast.LENGTH_LONG
                     )
                     .show();
                navController.navigate(R.id.home_fragment);
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
}
