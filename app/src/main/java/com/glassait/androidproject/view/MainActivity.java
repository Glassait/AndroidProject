package com.glassait.androidproject.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.file.GetUserFromFile;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.User;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Build the database
        Builder.getInstance()
               .buildDatabase(this);
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
            intent.putExtra(
                    "USER",
                    user
            );
            startActivity(intent);
        }

        // View part
        setContentView(R.layout.activity_main);
    }
}