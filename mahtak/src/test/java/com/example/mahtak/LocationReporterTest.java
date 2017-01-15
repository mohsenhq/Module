package com.example.mahtak;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.io.File;

import static org.junit.Assert.assertNotNull;

/**
 * Created by MahTak on 1/14/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "AndroidManifest.xml")
// ".\\src\\debug\\AndroidManifest.xml"
public class LocationReporterTest {
    LocationReporter lr;

    protected Context instance() {
        ShadowApplication shadowApp = Shadows.shadowOf(RuntimeEnvironment.application);
        shadowApp.grantPermissions("android.permission.INTERNET");

        return shadowApp.getApplicationContext();
    }

    @Before
    public void setUp() throws Exception {

        System.out.println(LocationManager.GPS_PROVIDER);

        lr = new LocationReporter(instance());
        LocationManager lm = (LocationManager) RuntimeEnvironment.application.getSystemService(Context.LOCATION_SERVICE);

        if(lm.getAllProviders() != null) {
            System.out.println(lm.getAllProviders());
//            lm.removeTestProvider(LocationManager.GPS_PROVIDER);
        }

//        lm.addTestProvider("test", false, false, false, false, true, true, true, 0, 5);
//        lm.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);

//        if (null == lm.getProvider("gps")){
//            lm.addTestProvider("gps", false, false, false, false, false, false, false, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
//        }
        Location location = new Location("gps");
        location.setLatitude(10.0);
        location.setLongitude(20.0);
        location.setTime(System.currentTimeMillis());

        if (LocationManager.GPS_PROVIDER != null) {
            lm.setTestProviderEnabled("gps",true);
            System.out.println(lm.isProviderEnabled(LocationManager.GPS_PROVIDER));
            lm.setTestProviderLocation(LocationManager.GPS_PROVIDER, location);
        }else{
            System.out.println("problem!");}
    }

    @Test
    public void test() {
        System.out.println(lr.locationList);
        assertNotNull("", lr.locationList);
    }

}