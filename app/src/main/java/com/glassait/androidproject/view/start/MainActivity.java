package com.glassait.androidproject.view.start;

import android.content.Intent;
import android.os.Bundle;
import android.os.LocaleList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.file.Cache;
import com.glassait.androidproject.common.utils.file.GetUserFromFile;
import com.glassait.androidproject.common.utils.secret.Secret;
import com.glassait.androidproject.common.utils.secret.StoreLocalData;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.User;
import com.glassait.androidproject.view.main.SecondActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Build the database
        Builder.getInstance()
               .buildDatabase(this);
        // Initialize the singleton
        StoreLocalData.getInstance();

        // Auto switch theme
        Cache themeCache = new Cache(
                Secret.THEME_NAME,
                getApplicationContext()
        );
        int theme = themeCache.readDateFromFile();
        if (theme != Secret.INVALID_INT) {
            AppCompatDelegate.setDefaultNightMode(theme);
        }

        // Automatic-connection part
        GetUserFromFile getUser = new GetUserFromFile(getApplicationContext());
        Thread          thread  = new Thread(getUser);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        User user = getUser.getUser();

        if (user != null) {
            Intent intent = new Intent(
                    getApplicationContext(),
                    SecondActivity.class
            );

            user.address.getLocation(getApplicationContext());
            StoreLocalData.getInstance()
                          .setUser(user);
            startActivity(intent);
        }

        // View part
        setContentView(R.layout.activity_main);
    }
}