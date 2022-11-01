package com.glassait.androidproject.common.utils;

/**
 * Is class is for handle the check of the phone number during register process
 */
public class Phone extends Checker {
    private String mCurrentPhone = "";

    /**
     * Constructor for the phone handler
     * The regex pattern is store in the abstract class {@link Checker}
     * <p>
     * For testing the regex pattern is live you can use this web site: <a href="https://regex101.com/">regex101</a>.
     * With the different example is the unit test
     *
     * @see <a href="https://regex101.com/">regex101</a>
     */
    public Phone() {
        // Regex pattern used to match phone addresses, see PhoneTest
        // for example of right/ wrong phone
        super("^[+][1-9][0-9]{0,3}[0-9]{8,10}$");
    }

    /**
     * Check if the phone is correctly formatted with the regex pattern.
     * To seen how it's works check {@link Checker#checkValue(String)}
     *
     * @return True if the phone is correctly formatted, false otherwise
     *
     * @see Checker#checkValue(String)
     */
    public boolean checkPhone() {
        return super.checkValue(mCurrentPhone);
    }

    /**
     * @return The current phone number
     */
    public String getPhone() {
        return mCurrentPhone;
    }

    /**
     * The phone has to be encode before with {@link Phone#encode(String)} or {@link Checker#encode(String)}.
     *
     * @param phone The phone number
     */
    public void setPhone(String phone) {
        mCurrentPhone = phone;
    }
}
