package com.glassait.androidproject.common.utils;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Is class is for handle the check of the email address
 */
public class Email extends Checker {
    private String mPreviousEmail = "";
    private String mEmail         = "";

    public Email() {
        // Regex pattern used to match email addresses, see EmailTest
        // for example of right/ wrong email
        super("^[a-zA-Z.-_]+@[a-zA-Z]+([.][a-zA-Z]+)*[.][a-zA-Z]{2,4}$");
    }

    /**
     * Check if the email is correctly formatted with the regex pattern
     *
     * @return boolean
     *
     * @see Pattern#matcher(CharSequence)
     */
    public boolean checkEmail() {
        return super.checkValue(mEmail);
    }

    /**
     * Getter for the email
     *
     * @return String
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Setter for the email.
     * The email is encoded
     *
     * @param email String
     *
     * @see #checkVersion(String)
     */
    public void setEmail(String email) {
        checkVersion(email);
    }

    /**
     * Setter for the email from an edit text.
     * The email is encoded
     *
     * @param input EditText
     *
     * @see #checkVersion(String)
     */
    public void setEmail(EditText input) {
        checkVersion(input.getText()
                          .toString());
    }

    /**
     * Check if the value is different from the previous value.
     * If true replace the current data store with the new value.
     *
     * @param value String
     *
     * @see TextUtils#htmlEncode(String)
     */
    private void checkVersion(String value) {
        value = TextUtils.htmlEncode(value);
        if (!mPreviousEmail.equals(value)) {
            mPreviousEmail = mEmail;
            mEmail = value;
        }
    }
}
