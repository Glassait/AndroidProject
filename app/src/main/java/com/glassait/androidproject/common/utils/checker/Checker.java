package com.glassait.androidproject.common.utils.checker;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract class for all elements who need to have a specific shape.
 */
public abstract class Checker {
    private final Pattern mPattern;

    /**
     * When the class is created, the first thing is to compile the regex string into a Pattern
     * with {@link Pattern#compile(String)}.
     * <p>
     * If we don't compile the regex, we cannot use the regex to check the value.
     *
     * @param regex The current regular expression you want to compile
     *
     * @see Pattern#compile(String)
     */
    public Checker(String regex) {
        this.mPattern = Pattern.compile(regex);
    }

    /**
     * Check if the value is correctly formatted with the regex pattern.
     * <p>
     * The check is performed with
     * {@link Pattern#matcher(CharSequence)} and {@link Matcher#matches()}
     *
     * @return True if the value match the regular expression, else false;
     *
     * @see Pattern#matcher(CharSequence)
     * @see Matcher#matches()
     */
    public boolean checkValue(String value) {
        return mPattern.matcher(value)
                       .matches();
    }

    /**
     * Encode the value for security purposes.
     * For example replace <strong>'</strong> by <strong>\'</strong>
     *
     * @param value The string to encode
     *
     * @return The encoded string
     *
     * @see TextUtils#htmlEncode(String)
     */
    public String encode(String value) {
        return TextUtils.htmlEncode(value);
    }
}