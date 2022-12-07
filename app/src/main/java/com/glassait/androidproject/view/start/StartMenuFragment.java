package com.glassait.androidproject.view.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;
import com.glassait.androidproject.view.ScanningActivity;

public class StartMenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(
                R.layout.fragment_start_menu,
                container,
                false
        );

        NavController navController = NavHostFragment.findNavController(this);

        TextView signUp = root.findViewById(R.id.fragment_start_menu_sign_up_button);
        TextView signIn = root.findViewById(R.id.fragment_start_menu_sign_in_button);

        // Navigate to the sign up fragment
        signUp.setOnClickListener(view -> navController.navigate(R.id.sign_up));
        signIn.setOnClickListener(view -> {
            Intent intent = new Intent(
                    getContext(),
                    ScanningActivity.class
            );
            startActivity(intent);
        });

        return root;
    }
}