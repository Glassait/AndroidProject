package com.glassait.androidproject.common.utils.validator;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.checker.Email;

public abstract class EmailValidator extends Fragment {
    public final Email email = new Email();

    /**
     * Check if the EditText field is filled.
     * <p>
     * If the length of the string get from the EditText is equals to 0 then we set an error on the
     * EditText.
     *
     * @param et The editText to check
     *
     * @return True if the editText is filled, false otherwise
     *
     * @see EditText#setError(CharSequence)
     */
    public boolean checkIfEtIsFilled(@NonNull EditText et) {
        if (et.getText()
              .toString()
              .length() == 0) {
            et.setError(getString(R.string.error_cannot_be_empty));
            return false;
        }
        return true;
    }

    /**
     * Check if the email is correctly formatted and encode it.
     * <p>
     * If not set an error on the input and return false.
     * <p>
     * Use the {@link Email#checkEmail()} to realise the check
     *
     * @return True if the email is correctly formatted, false otherwise
     *
     * @see Email#checkEmail()
     * @see Email#setEmail(String)
     * @see Email#encode(String)
     * @see EditText#setError(CharSequence)
     */
    public boolean checkEmail(EditText emailEt, View root) {
        email.setEmail(email.encode(emailEt.getText()
                                           .toString()));
        if (!email.checkEmail()) {
            emailEt.setError(root.getResources()
                                 .getString(R.string.error_incorrect_email) + ": "
                    + "id@domain_name.domain_extension");
            return false;
        }
        return true;
    }
}
