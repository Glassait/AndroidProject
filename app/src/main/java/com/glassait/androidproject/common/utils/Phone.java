package com.glassait.androidproject.common.utils;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Is class is for handle the check of the phone
 */
public class Phone extends Checker {
    private String mPreviousPhone = "";
    private String mPhone         = "";

    public Phone() {
        // Regex pattern used to match phone addresses, see PhoneTest
        // for example of right/ wrong phone
        super("^[+][0-9]{1,4}[0-9]{8,9}$");
    }

    /**
     * Check if the phone is correctly formatted with the regex pattern
     *
     * @return boolean
     *
     * @see Pattern#matcher(CharSequence)
     */
    public boolean checkPhone() {
        return super.checkValue(mPhone);
    }

    /**
     * Getter for the phone
     *
     * @return String
     */
    public String getPhone() {
        return mPhone;
    }

    /**
     * Setter for the phone.
     * The phone is encoded
     *
     * @param phone String
     *
     * @see #checkVersion(String)
     */
    public void setEmail(String phone) {
        checkVersion(phone);
    }

    /**
     * Setter for the phone from an edit text.
     * The phone is encoded
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
        if (!mPreviousPhone.equals(value)) {
            mPreviousPhone = mPhone;
            mPhone = value;
        }
    }
}
