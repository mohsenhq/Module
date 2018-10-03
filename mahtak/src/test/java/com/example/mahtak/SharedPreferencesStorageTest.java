package com.example.mahtak;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by MahTak on 11/2/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SharedPreferencesStorageTest {

    SharedPreferencesStorage shp;
    Application context;
    SharedPreferences sharedPrefs;
    @Before
    public void setUp() throws Exception {
        shp = new SharedPreferencesStorage();
        context = RuntimeEnvironment.application;
        sharedPrefs = context.getSharedPreferences("deviceID", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
    }

    @Test
    public void generateUUID() throws Exception {
        assertFalse("UUID exists before creation",sharedPrefs.contains("UUID"));
        shp.generateUUID(context);
        assertTrue("not UUID",sharedPrefs.contains("UUID"));

        WifiManager wifiManager = (WifiManager)   context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        PackageInfo packageInfo=context.getPackageManager().getPackageInfo(context.getPackageName(),0);

        assertNotNull("no macaddress",info.getMacAddress());
        assertNotNull("no model",Build.MODEL);
        assertNotNull("no brand",Build.BRAND);
        assertNotNull("no manufacturer",Build.MANUFACTURER);
        assertNotNull("no sdk int",Build.VERSION.SDK_INT);
        assertNotNull("no package info",packageInfo);

        //Assert
        assertNotNull("not UUID", shp.getStringFromPreferences(context, null, "UUID", "deviceID"));
    }


//    @Test
//    public void putGet() throws Exception {
//        //Arrange
//        String key = "key";
//        String test = "1234 abc";
//        String fileKey = "file";
//        //Act
//        shp.putStringInPreferences(context, key, test, fileKey);
//
//        //Assert
//        assertEquals("not Equal", test, shp.getStringFromPreferences(context, null, key, fileKey));
//    }
//
//    @Test
//    public void getAll() {
//        //Arrange
//        String key = "key";
//        String test = "1234 abc";
//        String fileKey = "file";
//        shp.putStringInPreferences(context, key, test, fileKey);
//
//        //Act
//        Map<String, ?> a = shp.getAll(context, fileKey);
//
//        //Assert
//        assertEquals("not the same", "{key=1234 abc}",a.toString());
//    }
//
//    @Test
//    public void removeAll() {
//        //Arrange
//        String key1 = "key1";
//        String key2 = "key2";
//        String value1 = "value1";
//        String value2 = "value2";
//        String fileKey = "file";
//        shp.putStringInPreferences(context, key1, value1, fileKey);
//        shp.putStringInPreferences(context, key2, value2, fileKey);
//
//        //Act
//        shp.removeAll(context,fileKey);
//
//        //Assert
//        assertEquals("not null","{}",shp.getAll(context,fileKey).toString());
//    }


}