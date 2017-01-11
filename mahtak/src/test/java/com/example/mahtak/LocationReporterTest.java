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
import org.robolectric.annotation.Config;


/**
 * Created by MahTak on 1/11/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml")

public class LocationReporterTest {

    Context context;
    LocationReporter locationReporter;

    @Before
    public void setUp() throws Exception {
        context = RuntimeEnvironment.application.getBaseContext();
        locationReporter = new LocationReporter(context);


    }


    @Test
    public void test() throws Exception {
        //Arrange
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.getAllProviders();
        lm.addTestProvider("test", false, false, false, false, false, false, false, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
        lm.setTestProviderEnabled("test", true);
        Location location = new Location("test");
        location.setLatitude(10.0);
        location.setLongitude(20.0);
        location.setTime(System.currentTimeMillis());

        lm.setTestProviderLocation("test", location);

        //Act


        //Assert
        System.out.println(locationReporter.locationList.toString());
//        Assert.assertEquals();
    }

}