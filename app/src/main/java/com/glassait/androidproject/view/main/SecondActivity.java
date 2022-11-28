package com.glassait.androidproject.view.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class SecondActivity extends AppCompatActivity {
    // View part
    private static FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        NavController navController =
                ((NavHostFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.second_activity_fragment_container_view))).getNavController();

        addBtn = findViewById(R.id.second_activity_add_button);

        // Bottom navigation bar
        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setSelectedItemId(R.id.bottom_navigation_menu_home);
        navigationBarView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_navigation_menu_home) {
                navController.navigate(R.id.home_fragment);
            } else if (item.getItemId() == R.id.bottom_navigation_menu_search) {
                System.out.println("Search item selected");
            } else if (item.getItemId() == R.id.bottom_navigation_menu_chat) {
                System.out.println("Chat item selected");
            }
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
    public static void enableAddButton(View.OnClickListener clickListener) {
        addBtn.setVisibility(View.VISIBLE);
        addBtn.setOnClickListener(clickListener);
    }

    /**
     * Disable the add floating action button and remove the listener
     *
     * @see FloatingActionButton#setVisibility(int)
     * @see FloatingActionButton#setOnClickListener(View.OnClickListener)
     */
    public static void disableAddButton() {
        addBtn.setVisibility(View.GONE);
        addBtn.setOnClickListener(null);
    }
}