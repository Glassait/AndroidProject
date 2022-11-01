package com.glassait.androidproject.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.glassait.androidproject.R;
import com.glassait.androidproject.model.database.Builder;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Builder.getInstance()
               .buildDatabase(this);
        setContentView(R.layout.activity_main);
    }
}