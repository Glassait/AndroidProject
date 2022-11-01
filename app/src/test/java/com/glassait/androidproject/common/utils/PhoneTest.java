package com.glassait.androidproject.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhoneTest {
    /**
     * Test the initialisation of the class
     *
     * @see Phone#Phone()
     * @see Phone#getPhone()
     * @see org.junit.Assert#assertEquals(Object, Object)
     */
    @Test
    public void initClass() {
        Phone mPhone = new Phone();
        assertEquals(
                "",
                mPhone.getPhone()
        );
    }

    /**
     * Test the setting of the Phone class
     *
     * @see Phone#Phone()
     * @see Phone#getPhone()
     * @see Phone#setPhone(String)
     * @see org.junit.Assert#assertEquals(Object, Object)
     */
    @Test
    public void setPhone() {
        Phone  mPhone = new Phone();
        String phone  = "+3300000000";
        mPhone.setPhone(phone);
        assertEquals(
                phone,
                mPhone.getPhone()
        );
    }

    /**
     * This test checks all phone number that AS to be valid
     *
     * @see Phone#Phone()
     * @see Phone#setPhone(String)
     * @see Phone#checkPhone()
     * @see org.junit.Assert#assertTrue(boolean)
     */
    @Test
    public void checkRightPhone() {
        Phone mPhone = new Phone();

        String[] right_phone = {"+3300000000", "+1012345678", "+12340123456789"};
        for (String phone : right_phone) {
            mPhone.setPhone(phone);
            assertTrue(mPhone.checkPhone());
        }
    }

    /**
     * This test checks all phone number that AS to not be valid
     *
     * @see Phone#Phone()
     * @see Phone#setPhone(String)
     * @see Phone#checkPhone()
     * @see org.junit.Assert#assertFalse(boolean)
     */
    @Test
    public void checkWrongPhone() {
        Phone mPhone = new Phone();

        String[] wrong_phone = {"3300000000", "+00000000", "00", "0000 0000", "+"};
        for (String phone : wrong_phone) {
            mPhone.setPhone(phone);
            assertFalse(mPhone.checkPhone());
        }
    }
}