package com.glassait.androidproject.common.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.secret.StoreManager;
import com.glassait.androidproject.model.dao.OfferDao;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.Offer;
import com.glassait.androidproject.model.entity.User;

import java.util.List;

public class CreateOfferLayout {
    // View part
    private final View          mView;
    private final NavController mNavController;
    // Database part
    private final AppDatabase   mAppDatabase = Builder.getInstance()
                                                      .getAppDatabase();
    private final UserDao       mUserDao     = mAppDatabase.userDao();
    private final OfferDao      mOfferDao    = mAppDatabase.offerDao();

    public CreateOfferLayout(View mView, NavController mNavController) {
        this.mView = mView;
        this.mNavController = mNavController;
    }

    /**
     * Create all the display for the {@link Offer} and add it to the given {@link LinearLayout}.
     * The process is a Thread.
     * <p>
     * Set a listener on the {@link ConstraintLayout}
     *
     * @param offers List of offers to display
     * @param parent The parent for putting the display offer
     *
     * @see Thread#start()
     * @see ConstraintLayout#ConstraintLayout(Context, AttributeSet, int, int)
     * @see ConstraintLayout#setId(int)
     * @see ConstraintLayout#setLayoutParams(ViewGroup.LayoutParams)
     * @see View#generateViewId()
     * @see ConstraintLayout.LayoutParams#LayoutParams(int, int)
     * @see ConstraintLayout.LayoutParams#setMarginStart(int)
     * @see ConstraintLayout.LayoutParams#setMarginEnd(int)
     * @see TextView#TextView(Context, AttributeSet, int, int)
     * @see ImageView#ImageView(Context, AttributeSet, int, int)
     * @see View#setOnClickListener(View.OnClickListener)
     * @see TypedArray#recycle()
     * @see ConstraintLayout#addView(View)
     * @see LinearLayout#addView(View, int)
     */
    public void createOfferLayout(List<Offer> offers, LinearLayout parent) {
        new Thread(() -> {
            for (int i = 0; i < offers.size(); i++) {
                Offer offer = offers.get(i);

                // Get the user who create the offer
                final User[] user = new User[1];
                mUserDao.getUserFromUid(offer.creatorId)
                        .subscribe(
                                u -> user[0] = u,
                                throwable -> user[0] = null
                        )
                        .dispose();

                // Check if the user still exists in the database
                if (user[0] != null) {
                    // Layout
                    ConstraintLayout myOfferCl = new ConstraintLayout(
                            mView.getContext(),
                            null,
                            0,
                            R.style.offer
                    );
                    myOfferCl.setId(View.generateViewId());
                    TypedArray styledAttributes = mView.getContext()
                                                       .obtainStyledAttributes(
                                                               R.style.offer,
                                                               R.styleable.layout
                                                       );
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                            getIntFromTypedArray(
                                    styledAttributes,
                                    R.styleable.layout_android_layout_width,
                                    "px"
                            ),
                            getIntFromTypedArray(
                                    styledAttributes,
                                    R.styleable.layout_android_layout_height,
                                    "px"
                            )
                    );
                    layoutParams.setMarginStart(getIntFromTypedArray(
                            styledAttributes,
                            R.styleable.layout_android_layout_marginStart,
                            "px"
                    ));
                    layoutParams.setMarginEnd(getIntFromTypedArray(
                            styledAttributes,
                            R.styleable.layout_android_layout_marginEnd,
                            "px"
                    ));
                    myOfferCl.setLayoutParams(layoutParams);

                    // Title
                    TextView myOfferTitle = new TextView(
                            mView.getContext(),
                            null,
                            0,
                            R.style.offer_title
                    );
                    myOfferTitle.setId(View.generateViewId());
                    myOfferTitle.setText(offer.title);
                    myOfferTitle.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    styledAttributes = mView.getContext()
                                            .obtainStyledAttributes(
                                                    R.style.offer_title,
                                                    R.styleable.layout
                                            );
                    ConstraintLayout.LayoutParams myOfferTitleLayoutParams =
                            new ConstraintLayout.LayoutParams(
                                    getIntFromTypedArray(
                                            styledAttributes,
                                            R.styleable.layout_android_layout_width,
                                            "px"
                                    ),
                                    getIntFromTypedArray(
                                            styledAttributes,
                                            R.styleable.layout_android_layout_height,
                                            "px"
                                    )
                            );
                    myOfferTitleLayoutParams.startToStart = myOfferCl.getId();
                    myOfferTitleLayoutParams.topToTop = myOfferCl.getId();
                    myOfferTitleLayoutParams.endToEnd = myOfferCl.getId();

                    // ImageView
                    ImageView myOfferImage = new ImageView(
                            mView.getContext(),
                            null,
                            0,
                            R.style.offer_image
                    );
                    myOfferImage.setId(View.generateViewId());
                    // If firebase work put the image of the tools
                    myOfferImage.setImageDrawable(ResourcesCompat.getDrawable(
                            mView.getResources(),
                            R.drawable.ic_launcher_foreground,
                            null
                    ));
                    styledAttributes = mView.getContext()
                                            .obtainStyledAttributes(
                                                    R.style.offer_image,
                                                    R.styleable.layout
                                            );
                    ConstraintLayout.LayoutParams myOfferImageLayoutParams =
                            new ConstraintLayout.LayoutParams(
                                    getIntFromTypedArray(
                                            styledAttributes,
                                            R.styleable.layout_android_layout_width,
                                            "px"
                                    ),
                                    getIntFromTypedArray(
                                            styledAttributes,
                                            R.styleable.layout_android_layout_height,
                                            "px"
                                    )
                            );
                    myOfferImageLayoutParams.startToStart = myOfferCl.getId();
                    myOfferImageLayoutParams.endToEnd = myOfferCl.getId();
                    myOfferImageLayoutParams.topToBottom = myOfferTitle.getId();

                    // Last and first name
                    TextView myOfferName = new TextView(
                            mView.getContext(),
                            null,
                            0,
                            R.style.offer_text
                    );
                    myOfferName.setId(View.generateViewId());
                    String name = user[0].lastName + " " + user[0].firstName;
                    myOfferName.setText(name);
                    myOfferName.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    styledAttributes = mView.getContext()
                                            .obtainStyledAttributes(
                                                    R.style.offer_text,
                                                    R.styleable.layout
                                            );
                    ConstraintLayout.LayoutParams myOfferNameLayoutParams =
                            new ConstraintLayout.LayoutParams(
                                    getIntFromTypedArray(
                                            styledAttributes,
                                            R.styleable.layout_android_layout_width,
                                            "px"
                                    ),
                                    getIntFromTypedArray(
                                            styledAttributes,
                                            R.styleable.layout_android_layout_height,
                                            "px"
                                    )
                            );
                    myOfferNameLayoutParams.startToStart = myOfferCl.getId();
                    myOfferNameLayoutParams.endToEnd = myOfferCl.getId();
                    myOfferNameLayoutParams.topToBottom = myOfferImage.getId();

                    // City
                    TextView myOfferCity = new TextView(
                            mView.getContext(),
                            null,
                            0,
                            R.style.offer_text
                    );
                    myOfferCity.setId(View.generateViewId());
                    myOfferCity.setText(user[0].city);
                    myOfferCity.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    ConstraintLayout.LayoutParams myOfferCityLayoutParams =
                            new ConstraintLayout.LayoutParams(
                                    getIntFromTypedArray(
                                            styledAttributes,
                                            R.styleable.layout_android_layout_width,
                                            "px"
                                    ),
                                    getIntFromTypedArray(
                                            styledAttributes,
                                            R.styleable.layout_android_layout_height,
                                            "px"
                                    )
                            );
                    myOfferCityLayoutParams.startToStart = myOfferCl.getId();
                    myOfferCityLayoutParams.endToEnd = myOfferCl.getId();
                    myOfferCityLayoutParams.topToBottom = myOfferName.getId();
                    myOfferCityLayoutParams.bottomToBottom = myOfferCl.getId();
                    myOfferCity.setLayoutParams(myOfferCityLayoutParams);
                    styledAttributes.recycle();

                    // End name parameters
                    myOfferNameLayoutParams.bottomToTop = myOfferCity.getId();
                    myOfferName.setLayoutParams(myOfferNameLayoutParams);

                    // End ImageView parameters
                    myOfferImageLayoutParams.bottomToTop = myOfferName.getId();
                    myOfferImage.setLayoutParams(myOfferImageLayoutParams);

                    // End Title parameters
                    myOfferTitleLayoutParams.bottomToTop = myOfferImage.getId();
                    myOfferTitle.setLayoutParams(myOfferTitleLayoutParams);

                    // Add all view to the constraint layout
                    myOfferCl.addView(myOfferTitle);
                    myOfferCl.addView(myOfferImage);
                    myOfferCl.addView(myOfferName);
                    myOfferCl.addView(myOfferCity);

                    // Click listener
                    myOfferCl.setOnClickListener(v -> {
                        StoreManager.setOffer(offer);
                        mNavController.navigate(R.id.my_offer_fragment);
                    });

                    // Add the offer in the HorizontalScrollView, the index is 0 to always put the
                    // offer in the start
                    parent.addView(
                            myOfferCl,
                            0
                    );
                    System.out.println("Add my offer n°" + offer.uid);
                } else {
                    mOfferDao.delete(offer);
                }
            }
        }).start();
    }

    /**
     * Get the values inside the {@link TypedArray} corresponding to the given R.styleable.
     * <p>
     * By default the unit of the output is in dip.
     *
     * @param array  The typed array create with {@link Context#obtainStyledAttributes(int[])}
     * @param resId  The styleable (e.g R.styleable)
     * @param output The unit of the output (e.g px or dip). By default the output is in dip
     *
     * @return The number in px or in dip
     *
     * @see TypedValue#applyDimension(int, float, DisplayMetrics)
     */
    public int getIntFromTypedArray(TypedArray array, int resId, String output) {
        float dim = Float.parseFloat(array.getString(resId)
                                          .replace(
                                                  "dip",
                                                  ""
                                          ));
        if (dim < 0) {
            return (int) dim;
        }
        if (output.equals("px")) {
            return (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dim,
                    mView.getResources()
                         .getDisplayMetrics()
            );
        }
        return (int) dim;
    }
}
