package com.glassait.androidproject.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.glassait.androidproject.common.utils.checker.Email;

import org.junit.Test;

public class EmailTest {
    /**
     * Test the creation of the email class
     *
     * @see Email#Email()
     * @see Email#getEmail()
     * @see org.junit.Assert#assertEquals(Object, Object)
     */
    @Test
    public void initClass() {
        Email mEmail = new Email();
        assertEquals(
                "",
                mEmail.getEmail()
        );
    }

    /**
     * Test the setting of email
     *
     * @see Email#Email()
     * @see Email#setEmail(String)
     * @see Email#getEmail()
     * @see org.junit.Assert#assertEquals(Object, Object)
     */
    @Test
    public void setEmail() {
        Email  mEmail = new Email();
        String email  = "test@example.com";
        mEmail.setEmail(email);
        assertEquals(
                email,
                mEmail.getEmail()
        );
    }

    /**
     * This test checks all email addresses that AS to be valid
     *
     * @see Email#Email()
     * @see Email#setEmail(String)
     * @see Email#checkEmail()
     * @see org.junit.Assert#assertTrue(boolean)
     */
    @Test
    public void checkRightEmail() {
        Email mEmail = new Email();

        String[] right_email =
                {"right_mail@mail.com", "right_mail@gmail.domain.com", "right.mail@gmail.com", "right.mail@gmail.domain.com"};
        for (String mail : right_email) {
            mEmail.setEmail(mail);
            assertTrue(mEmail.checkEmail());
        }
    }

    /**
     * This test checks all email addresses that AS to not be valid
     *
     * @see Email#Email()
     * @see Email#setEmail(String)
     * @see Email#checkEmail()
     * @see org.junit.Assert#assertFalse(boolean)
     */
    @Test
    public void checkWrongEmail() {
        Email mEmail = new Email();

        String[] wrong_email =
                {"wrong_mail", "wrong_mail@mail", "wrong_mail.com", "wrong_mail@gmail.c", "wrong_mail@gmail.cozmdzh"};
        for (String mail : wrong_email) {
            mEmail.setEmail(mail);
            assertFalse(mEmail.checkEmail());
        }
    }
}