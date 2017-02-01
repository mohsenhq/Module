package com.example.mahtak;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

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
                SHP.putStringInPreferences(context, "Locations", locationList.toString(), "temp");
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

        /**
         * Register Network and Gps provider to update location every 10 minutes
         */

        if (
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Contacts permissions have not been granted.
            Log.e("PER F", "Contact permissions has NOT been granted. Requesting permissions.");

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_CONTACTS)) {
                showMessageOKCancel("You need to allow access to Contacts", context,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 11);

                            }
                        });
                return;
            }


            return;
        }

        // Contact permissions have been granted. Show the contacts fragment.
        Log.e("PER T", "Contact permissions have already been granted. Displaying contact details.");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10 * 60 * 1000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10 * 60 * 1000, 0, locationListener);


    }

    private void showMessageOKCancel(String message, Context context, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder((Activity)context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}