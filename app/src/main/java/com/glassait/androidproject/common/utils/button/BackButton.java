package com.glassait.androidproject.common.utils.button;

import android.view.View;

import com.glassait.androidproject.R;

public class BackButton {
    /**
     * Search the back button and add the onClickListener on it
     *
     * @param root            The root view
     * @param onClickListener The click listener of the back button
     */
    public BackButton(View root, View.OnClickListener onClickListener) {
        root.findViewById(R.id.back_btn)
            .setOnClickListener(onClickListener);
    }
}