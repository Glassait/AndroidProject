package com.glassait.androidproject.UITest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.Manifest;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import com.glassait.androidproject.R;
import com.glassait.androidproject.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(AndroidJUnit4.class)
public class CreateOfferUiTest {
    private final Random   random = new Random();
    private final String[] title  =
            new String[]{"Hammer", "Screwdriver", "Mallet", "Axe", "Saw", "Scissors"};

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule permissionRead =
            GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE);

    @Test
    public void createOffer() {
        // Got to create offer fragment
        onView(withId(R.id.fragment_home_add_offer)).perform(
                scrollTo(),
                click()
        );

        // Failed to upload image in firebase
        // Filled all fields for the offer
        /*onView(withId(R.id.fragment_create_offer_tool_image)).perform(
                scrollTo(),
                click()
        );

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        onView(withId(R.id.fragment_create_offer_title_et)).perform(
                scrollTo(),
                typeText(title[random.nextInt(title.length)]),
                closeSoftKeyboard()
        );

        onView(withId(R.id.fragment_create_offer_chip_electric)).perform(
                scrollTo(),
                click()
        );

        // Create offer button
        onView(withId(R.id.fragment_create_offer_create_offer_btn)).perform(
                scrollTo(),
                click()
        );
    }
}
