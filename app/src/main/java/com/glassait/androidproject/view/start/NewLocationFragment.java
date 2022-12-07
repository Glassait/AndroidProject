package com.glassait.androidproject.view.start;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.button.BackButton;

public class NewLocationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(
                R.layout.fragment_new_location,
                container,
                false
        );

        new BackButton(
                root,
                view -> NavHostFragment.findNavController(this)
                                       .navigate(R.id.sign_in_fragment)
        );

        return root;
    }
}