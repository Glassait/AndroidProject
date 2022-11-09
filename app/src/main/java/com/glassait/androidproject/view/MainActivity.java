package com.glassait.androidproject.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.file.Cache;
import com.glassait.androidproject.model.dao.UserDao;
import com.glassait.androidproject.model.database.AppDatabase;
import com.glassait.androidproject.model.database.Builder;
import com.glassait.androidproject.model.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Build the database
        Builder.getInstance()
               .buildDatabase(this);

        // Automatic-connection part
        Cache cache = new Cache(
                "user_id",
                getApplicationContext()
        );
        String result = cache.readDataFromFile();
        if (result != null) {
            try {
                JSONObject data = new JSONObject(result);

                AppDatabase database = Builder.getInstance()
                                              .getAppDatabase();

                UserDao userDao = database.userDao();
                User[]  users   = new User[1];
                userDao.getUserFromEmail(data.getString("email"))
                       .subscribe(
                               user -> users[0] = user,
                               throwable -> System.out.println("No user register with this email: ")
                       )
                       .dispose();

                if (data.length() > 0 && users[0] != null
                        && users[0].uuid.equals(UUID.fromString(data.getString("uuid")))) {
                    // Launch the second activity
                    Intent intent = new Intent(
                            getApplicationContext(),
                            SecondActivity.class
                    );
                    intent.putExtra(
                            "USER",
                            users[0]
                    );
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        setContentView(R.layout.activity_main);
    }
}