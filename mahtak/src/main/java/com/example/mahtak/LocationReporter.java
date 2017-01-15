package com.example.mahtak;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

/**
 * Created by MahTak on 1/1/2017.
 */
public class LocationReporter {

    /**
     * Object of ToSharedPreferences for saving location list
     */
    ToSharedPreferences SHP;
    /**
     * The list of Locations
     */
    public ArrayList<Location> locationList;

    /**
     * Instantiates a new Location reporter.
     *
     * @param context the context
     */
    public LocationReporter(final Context context) {
        SHP = new ToSharedPreferences();
        locationList = new ArrayList<Location>();
        LocationManager locationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            /**
             * On location update save location to the sharedPreferences
             * @param location, updated location
             */
            @Override
            public void onLocationChanged(Location location) {
                locationList.add(location);
                SHP.putStringInPreferences(context,"Locations",locationList.toString(),"temp");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        /**
         * Register Network and Gps provider to update location every 10 minutes
         */
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10 * 60 * 1000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10 * 60 * 1000, 0, locationListener);


    }
}