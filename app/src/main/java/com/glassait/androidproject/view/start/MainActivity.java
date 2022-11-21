package com.glassait.androidproject.view.start;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.file.GetUserFromFile;
import com.glassait.androidproject.common.utils.secret.StoreManager;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.User;
import com.glassait.androidproject.view.main.SecondActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Build the database
        Builder.getInstance()
               .buildDatabase(this);
        // Initialize the singleton
        StoreManager.getInstance();

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
            StoreManager.setUser(user);
            startActivity(intent);
        }

        // View part
        setContentView(R.layout.activity_main);
    }
}