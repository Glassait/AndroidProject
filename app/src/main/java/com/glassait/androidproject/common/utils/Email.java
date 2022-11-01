package com.glassait.androidproject.common.utils;

/**
 * Is class is for handle the check of the email address during register process
 */
public class Email extends Checker {
    private String mCurrentEmail = "";

    /**
     * Constructor for the email handler
     * The regex pattern is store in the abstract class {@link Checker}
     * <p>
     * For testing the regex pattern is live you can use this web site:
     * <a href="https://regex101.com/">regex101</a>.
     * With the different example is the unit test
     *
     * @see <a href="https://regex101.com/">regex101</a>
     */
    public Email() {
        // Regex pattern used to match email addresses, see EmailTest
        // for example of right/ wrong email
        super("^[a-zA-Z.-_]+@[a-zA-Z]+([.][a-zA-Z]+)*[.][a-zA-Z]{2,4}$");
    }

    /**
     * @return The current email address
     */
    public String getEmail() {
        return mCurrentEmail;
    }

    /**
     * Check if the email is correctly formatted with the regex pattern.
     * To seen how it's works check {@link Checker#checkValue(String)}
     *
     * @return True ig the mail address is correctly formatted, else false
     *
     * @see Checker#checkValue(String)
     */
    public boolean checkEmail() {
        return super.checkValue(mCurrentEmail);
    }

    /**
     * The email has to be encoded before with {@link Email#encode(String)} or
     * {@link Checker#encode(String)}.
     *
     * @param email The mail address
     */
    public void setEmail(String email) {
        mCurrentEmail = email;
    }
}
