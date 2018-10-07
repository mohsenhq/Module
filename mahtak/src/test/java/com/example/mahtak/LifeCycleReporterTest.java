package com.example.mahtak;

import android.app.Application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by MahTak on 11/9/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName = "com.example.mahtak")
@Implements(LifeCycleReporter.class)
public class LifeCycleReporterTest {
    SharedPreferencesStorage shp;
    ActivityController controller;
    Application context;

    @Before
    public void setUp() throws Exception {

        context = RuntimeEnvironment.application;
        shp = new SharedPreferencesStorage();

        controller = Robolectric.buildActivity(MainA.class).create().start();

    }

    @Test
    public void onCreate() throws Exception {

        assertNotNull("its null", shp.getStringFromPreferences(context, null, "UUID", "deviceID"));
    }

    @Test
    public void onPause() throws Exception {
        controller.pause();
        assertNotNull("not Null", shp.getAll(context, "temp"));
    }

    @Test
    public void onDestroy() throws Exception {
        controller.pause();
        controller.destroy();
        assertEquals("is not empty", "{}", shp.getAll(context, "temp").toString());
    }
}