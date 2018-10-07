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

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by MahTak on 11/2/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName = "com.example.mahtak")
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
    public void generateUUIDTest() throws Exception {
        assertFalse("UUID exists before creation", sharedPrefs.contains("UUID"));
        shp.generateUUID(context);
        assertTrue("not UUID", sharedPrefs.contains("UUID"));

        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

        assertNotNull("no macaddress", info.getMacAddress());
        assertNotNull("no model", Build.MODEL);
        assertNotNull("no brand", Build.BRAND);
        assertNotNull("no manufacturer", Build.MANUFACTURER);
        assertNotNull("no sdk int", Build.VERSION.SDK_INT);
        assertNotNull("no package info", packageInfo);

        //Assert
        assertNotNull("not UUID", shp.getStringFromPreferences(context, null, "UUID", "deviceID"));
    }


    @Test
    public void storagePutGetTest() throws Exception {
        //Arrange
        String key = "key";
        String testString = "1234 abc";
        String fileKey = "file";
        String returnString = "";
        //Act
        shp.putStringInPreferences(context, key, testString, fileKey);
        returnString = shp.getStringFromPreferences(context, "", key, fileKey);
        //Assert
        assertEquals("not Equal", testString, returnString);
    }

    @Test
    public void getAll() {
        //Arrange
        String key1 = "key1";
        String key2 = "key2";
        String testString1 = "1234 abc";
        String testString2 = "5678 efg";
        Map<String, String> testMap = new LinkedHashMap<>();
        String fileKey = "file";


        //Act
        shp.putStringInPreferences(context, key1, testString1, fileKey);
        shp.putStringInPreferences(context, key2, testString2, fileKey);

        testMap.put(key1, testString1);
        testMap.put(key2, testString2);

        Map<String, ?> returnMap = shp.getAll(context, fileKey);


        //Assert
        assertEquals("not the same", testMap, returnMap);
    }

    //
    @Test
    public void removeAllTest() {
        //Arrange
        String key1 = "key1";
        String key2 = "key2";
        String testString1 = "1234 abc";
        String testString2 = "5678 efg";
        String fileKey = "file";

        //Act
        shp.putStringInPreferences(context, key1, testString1, fileKey);
        shp.putStringInPreferences(context, key2, testString2, fileKey);
        shp.removeAll(context, fileKey);

        //Assert
        assertEquals("not removed all", "{}", shp.getAll(context, fileKey).toString());
    }

    @Test
    public void addCustomRecordTest() {
        //Arrange
        String key1 = "key1";
        int testInt = 2;
        Map testMap = new LinkedHashMap();

        //Act
        shp.addCustomRecord(context, key1);
        shp.addCustomRecord(context, key1);
        String returnKey = "CustomEvent_" + key1;

        testMap.put(returnKey, testInt);
        Map returnString = shp.getAll(context, "temp");

        //Assert
        assertEquals("customRecord not added", testMap, returnString);

    }
}