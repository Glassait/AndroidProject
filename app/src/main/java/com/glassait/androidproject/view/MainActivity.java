package com.glassait.androidproject.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.LocalData;
import com.glassait.androidproject.model.database.Builder;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Builder.getInstance()
               .buildDatabase(this);
        LocalData.getInstance();

        setContentView(R.layout.activity_main);

        Intent intent = new Intent(
                this,
                ScanningActivity.class
        );
        startActivity(intent);
    }
}
