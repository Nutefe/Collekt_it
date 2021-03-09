package com.perfekxia.collekt_it.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;


public class GpsCoordinates implements LocationListener {
    private Context context;
    private String provider;
    private LocationManager locationManager;
    private Location coordinates;
    private String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    public void onLocationChanged(Location location) {
        coordinates = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Activate GPS");
        builder.setMessage("Veuillez activé le GPS de votre mobile.");
        builder.setPositiveButton("Activate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    private void initManager() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria, false);
        coordinates = new Location(provider);
    }

    public Location getCoordinates() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context, PERMISSIONS, 1);
            return null;
        }

        if (provider != null && !provider.equals("")) {
            coordinates = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 100, 5, this);

            if (coordinates != null){
                onLocationChanged(coordinates);
            }
        }

        return coordinates;
    }

    public GpsCoordinates(Context context) {
        this.context = context;
        initManager();
    }

}
