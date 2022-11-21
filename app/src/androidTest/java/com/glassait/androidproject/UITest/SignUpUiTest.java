package com.glassait.androidproject.UITest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.glassait.androidproject.R;
import com.glassait.androidproject.view.start.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignUpUiTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void signUp() {
        // Go to the sign up fragment
        onView(withId(R.id.fragment_start_menu_sign_up_button)).perform(click());

        // Filled all EditText
        onView(withId(R.id.fragment_sign_up_first_name_et)).perform(
                scrollTo(),
                typeText("Tendzin")
        );

        onView(withId(R.id.fragment_sign_up_last_name_et)).perform(
                scrollTo(),
                typeText("Roffler")
        );

        onView(withId(R.id.fragment_sign_up_email_et)).perform(
                scrollTo(),
                typeText("mailbox@gmail.com")
        );

        onView(withId(R.id.fragment_sign_up_phone_et)).perform(
                scrollTo(),
                typeText("+330123456789")
        );

        onView(withId(R.id.fragment_sign_up_address_et)).perform(
                scrollTo(),
                typeText("1 street of Jeanne")
        );

        onView(withId(R.id.fragment_sign_up_postal_code_et)).perform(
                scrollTo(),
                typeText("75000")
        );

        onView(withId(R.id.fragment_sign_up_city_et)).perform(
                scrollTo(),
                typeText("Paris")
        );

        onView(withId(R.id.fragment_sign_up_country_et)).perform(
                scrollTo(),
                typeText("France"),
                closeSoftKeyboard()
        );

        // Click on the button
        onView(withId(R.id.fragment_sign_up_register_btn)).perform(
                scrollTo(),
                click()
        );
    }
}
