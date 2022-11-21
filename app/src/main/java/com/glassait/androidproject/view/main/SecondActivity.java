package com.glassait.androidproject.view.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        NavController navController =
                ((NavHostFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.second_activity_fragment_container_view))).getNavController();

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setSelectedItemId(R.id.bottom_navigation_menu_home);
        navigationBarView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_navigation_menu_home) {
                navController.navigate(R.id.home_fragment);
                System.out.println("Home item selected");
            } else if (item.getItemId() == R.id.bottom_navigation_menu_search) {
                System.out.println("Search item selected");
            } else if (item.getItemId() == R.id.bottom_navigation_menu_chat) {
                System.out.println("Chat item selected");
            }
            return true;
        });
    }
}