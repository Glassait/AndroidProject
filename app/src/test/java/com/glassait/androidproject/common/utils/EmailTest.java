package com.glassait.androidproject.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailTest {
    private final Email mEmail = new Email();

    @Test
    public void checkEmail_right() {
        String[] right_email =
                {"right_mail@mail.com", "right_mail@gmail.domain.com", "right.mail@gmail.com", "right.mail@gmail.domain.com"};
        for (String mail : right_email) {
            mEmail.setEmail(mail);
            assertTrue(mEmail.checkEmail());
        }
    }

    @Test
    public void checkEmail_wrong() {
        String[] wrong_email =
                {"wrong_mail", "wrong_mail@mail", "wrong_mail.com", "wrong_mail@gmail.c", "wrong_mail@gmail.cozmdzh"};
        for (String mail : wrong_email) {
            mEmail.setEmail(mail);
            assertFalse(mEmail.checkEmail());
        }
    }

    @Test
    public void setEmail() {
        String newEmail = "mailing@gmail.com";
        mEmail.setEmail(newEmail);
        assertEquals(
                newEmail,
                mEmail.getEmail()
        );
    }
}