package com.glassait.androidproject.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.LocalData;

import java.util.Map;

public class CompareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        Map<Integer, String[]> database = LocalData.getInstance().getDatabase();
    }
}