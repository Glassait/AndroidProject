package com.glassait.androidproject.common.utils;

import java.util.regex.Pattern;

public abstract class Checker {
    private final Pattern mPattern;

    public Checker(String regex) {
        this.mPattern = Pattern.compile(regex);
    }

    /**
     * Check if the value is correctly formatted with the regex pattern
     *
     * @return boolean
     *
     * @see Pattern#matcher(CharSequence)
     */
    public boolean checkValue(String value) {
        return mPattern.matcher(value)
                       .matches();
    }
}
