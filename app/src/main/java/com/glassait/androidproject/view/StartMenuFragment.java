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

public class StartMenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(
                R.layout.fragment_start_menu,
                container,
                false
        );
        TextView signUp = root.findViewById(R.id.start_menu_sign_up_button);

        NavController navController = NavHostFragment.findNavController(this);
        // Navigate to the sign up fragment
        signUp.setOnClickListener(view -> navController.navigate(R.id.sign_up));

        return root;
    }
}