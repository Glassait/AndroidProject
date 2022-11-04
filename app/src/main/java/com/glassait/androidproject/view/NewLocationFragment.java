package com.glassait.androidproject.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;

public class NewLocationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRoot = inflater.inflate(
                R.layout.fragment_new_location,
                container,
                false
        );

        TextView      backButton    = mRoot.findViewById(R.id.new_location_back_btn);
        NavController navController = NavHostFragment.findNavController(this);
        backButton.setOnClickListener(view -> navController.navigate(R.id.sign_in_fragment));

        return mRoot;
    }
}