package com.glassait.androidproject.view.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.file.Cache;
import com.glassait.androidproject.common.utils.secret.Secret;
import com.glassait.androidproject.common.utils.secret.StoreLocalData;
import com.glassait.androidproject.model.entity.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class SecondActivity extends AppCompatActivity {
    private static SecondActivity       instance;
    // View part
    private        FloatingActionButton addBtn;
    private        ScrollView           mScrollView;

    public static synchronized SecondActivity getInstance() {
        if (instance == null) {
            instance = new SecondActivity();
        }
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_second);

        NavController navController =
                ((NavHostFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.second_activity_fragment_container_view))).getNavController();

        User currentUser = StoreLocalData.getInstance()
                                         .getUser();

        // DrawerLayout
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        // NavigationView
        NavigationView navigationView = findViewById(R.id.navigation_view);
        MenuItem theme = navigationView.getMenu()
                                       .findItem(R.id.nav_drawer_theme_mode);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            theme.setTitle(R.string.nav_drawer_light_mode);
        }
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_drawer_account) {
                // Todo
            } else if (item.getItemId() == R.id.nav_drawer_theme_mode) {
                Cache cacheTheme = new Cache(
                        Secret.THEME_NAME,
                        getApplicationContext()
                );
                cacheTheme.createFile();
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    cacheTheme.storeDataInFile(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    cacheTheme.storeDataInFile(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
            // Changing a language is to complex - WILL NOT BE IMPLEMENTED
            return true;
        });

        View     header              = navigationView.getHeaderView(0);
        TextView headerNavDrawerName = header.findViewById(R.id.header_nav_drawer_name);
        String   name                = currentUser.lastName + " " + currentUser.firstName;
        headerNavDrawerName.setText(name);
        TextView headerNavDrawerMail = header.findViewById(R.id.header_nav_drawer_mail);
        headerNavDrawerMail.setText(currentUser.email);

        // Store the scroll view to be able to change the position we navigation between fragment
        mScrollView = findViewById(R.id.second_activity_SV);
        // Floating button for create offer in the see all for the user offer
        addBtn = findViewById(R.id.second_activity_add_button);

        // Profile Button
        FloatingActionButton profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));

        // Bottom navigation bar
        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setSelectedItemId(R.id.bottom_navigation_menu_home);
        navigationBarView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_navigation_menu_home) {
                navController.navigate(R.id.home_fragment);
            } else if (item.getItemId() == R.id.bottom_navigation_menu_search) {
                System.out.println("Search item selected");
            } else if (item.getItemId() == R.id.bottom_navigation_menu_chat) {
                // WILL NOT BE IMPLEMENTED
                System.out.println("Chat item selected");
            }
            resetScroll();
            return true;
        });
    }

    /**
     * Enable the add floating action button and add a listener on it.
     *
     * @param clickListener The listener to add to the button, same form that  {@link View#setOnClickListener(View.OnClickListener)}
     *
     * @see FloatingActionButton#setVisibility(int)
     * @see FloatingActionButton#setOnClickListener(View.OnClickListener)
     */
    public void enableAddButton(View.OnClickListener clickListener) {
        addBtn.setVisibility(View.VISIBLE);
        addBtn.setOnClickListener(clickListener);
    }

    /**
     * Disable the add floating action button and remove the listener
     *
     * @see FloatingActionButton#setVisibility(int)
     * @see FloatingActionButton#setOnClickListener(View.OnClickListener)
     */
    public void disableAddButton() {
        addBtn.setVisibility(View.GONE);
        addBtn.setOnClickListener(null);
    }

    /**
     * Set the scroll view to the top
     */
    public void resetScroll() {
        mScrollView.smoothScrollTo(
                0,
                0
        );
    }
}