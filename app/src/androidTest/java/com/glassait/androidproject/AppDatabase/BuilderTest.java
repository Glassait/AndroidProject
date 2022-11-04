package com.glassait.androidproject.AppDatabase;

import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.glassait.androidproject.model.database.AppDatabase_Impl;
import com.glassait.androidproject.model.database.Builder;

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
        Context appContext = InstrumentationRegistry.getInstrumentation()
                                                    .getTargetContext();
        Builder builder = Builder.getInstance();
        builder.buildDatabase(appContext);

        assertTrue(builder.getAppDatabase() instanceof AppDatabase_Impl);
    }
}
