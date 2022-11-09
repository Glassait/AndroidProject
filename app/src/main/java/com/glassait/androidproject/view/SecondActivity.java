package com.glassait.androidproject.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.glassait.androidproject.R;
import com.glassait.androidproject.model.entity.User;
import com.google.android.material.navigation.NavigationBarView;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setSelectedItemId(R.id.bottom_navigation_menu_home);
        navigationBarView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_navigation_menu_home) {
                System.out.println("Home item selected");
            } else if (item.getItemId() == R.id.bottom_navigation_menu_search) {
                System.out.println("Search item selected");
            } else if (item.getItemId() == R.id.bottom_navigation_menu_chat) {
                System.out.println("Chat item selected");
            }
            return true;
        });

        User user = getIntent().getSerializableExtra(
                "USER",
                User.class
        );
    }
}