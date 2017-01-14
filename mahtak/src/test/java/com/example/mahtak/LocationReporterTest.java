package com.example.mahtak;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLocationManager;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;


/**
 * Created by MahTak on 1/11/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml")

public class LocationReporterTest {

    LocationReporter lr;
    LocationManager locationManager;
    Context context;
    @Before
    public void setUp() throws Exception {
        Settings.Secure.putString(RuntimeEnvironment.application.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED, "locationProvider");

        context = RuntimeEnvironment.application.getBaseContext();
        lr = new LocationReporter(context);
    }

    private Location location(String provider, double latitude, double longitude) {
        Location location = new Location(provider);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setTime(System.currentTimeMillis());
        return location;
    }


    @Test
    public void test() throws Exception {
        //Arrange
        locationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        ShadowLocationManager shadowLocationManager = shadowOf(locationManager);
        Location expectedLocation = location(LocationManager.GPS_PROVIDER, 12.0, 20.0);


        //Act
        shadowLocationManager.setProviderEnabled(LocationManager.GPS_PROVIDER,true);
        System.out.println(shadowLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        shadowLocationManager.simulateLocation(expectedLocation);


        //Assert
        assertEquals(lr.locationList, expectedLocation);
    }

}