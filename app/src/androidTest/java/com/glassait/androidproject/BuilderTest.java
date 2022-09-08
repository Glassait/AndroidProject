package com.glassait.androidproject;

import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.glassait.androidproject.database.AppDatabase_Impl;
import com.glassait.androidproject.database.Builder;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BuilderTest {
    /**
     * Test the buildDatabase function of the {@link Builder}
     *
     * @see Builder#buildDatabase(Context)
     */
    @Test
    public void build() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertTrue(new Builder().buildDatabase(appContext) instanceof AppDatabase_Impl);
    }
}
