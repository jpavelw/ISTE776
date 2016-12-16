package com.iste776.jpavelw.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iste776.jpavelw.map.dao.Place;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by jpavelw on 11/8/16.
 */

public class JLocation extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private LocationManager locationManager = null;
    private Location location = null;
    private Context appContext = null;
    private GoogleMap googleMap = null;
    private Geocoder geocoder = null;
    private Marker currentMarker = null;
    private GMapAPI gMapAPI;
    private Place currentPlace;

    public JLocation(Context appContext, SupportMapFragment mapFragment) {
        if(mapFragment == null){
            return;
        }
        mapFragment.getMapAsync(this);
        this.appContext = appContext;
        this.geocoder = new Geocoder(appContext, new Locale("en"));
        if (this.locationManager == null) {
            this.locationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
        }
        this.gMapAPI = new GMapAPI(appContext, this.gMapAPIListener);
    }

    public void showMap(){
        this.getCurrentLocation();
        if(this.location == null){
            Toast.makeText(this.appContext, appContext.getResources().getString(R.string.error_location_not_ready), Toast.LENGTH_LONG).show();
        } else {
            LatLng currentLocation = new LatLng(this.location.getLatitude(), this.location.getLongitude());
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
            this.createMark(currentLocation);
        }
    }

    private void getCurrentLocation(){
        if(this.googleMap == null){
            Toast.makeText(this.appContext, appContext.getResources().getString(R.string.error_maps_not_ready), Toast.LENGTH_LONG).show();
        } else {
            try {
                List<String> providers = this.locationManager.getProviders(true);
                for(String provider : providers){
                    Location loc = this.locationManager.getLastKnownLocation(provider);
                    if(loc == null)
                        continue;
                    if(this.location == null || loc.getAccuracy() < this.location.getAccuracy())
                        this.location = loc;
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private void createMark(LatLng latLng){
        try {
            Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            if (address != null) {
                String addrName = address.getAddressLine(0);
                if(this.currentMarker == null) {
                    this.currentMarker = this.googleMap.addMarker(new MarkerOptions().position(latLng).title(addrName));
                } else {
                    this.currentMarker.setTitle(addrName);
                    this.currentMarker.setPosition(latLng);
                }
                this.getSnippet(latLng);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(this.location != null) {
            this.location.setAltitude(latLng.latitude);
            this.location.setLongitude(latLng.longitude);
            this.createMark(latLng);
        } else {
            if(this.googleMap == null){
                Toast.makeText(this.appContext, appContext.getResources().getString(R.string.error_maps_not_ready), Toast.LENGTH_LONG).show();
            } else {
                if(this.currentMarker == null){
                    this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
                this.createMark(latLng);
            }
        }
    }

    private void getSnippet(LatLng latLng){
        this.gMapAPI.getPlaceByLatLng(latLng.latitude, latLng.longitude, PlacesActivity.categoryId);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        this.googleMap.setOnMapClickListener(this);
        this.googleMap.setOnMarkerClickListener(this);
    }

    private GMapAPI.GMapAPIListener gMapAPIListener = new GMapAPI.GMapAPIListener() {
        @Override
        public void onResponseGetPlaceByLatLng(Place place) {
            try {
                if(place != null){
                    currentPlace = place;
                    String snippet = place.toString();
                    currentMarker.setSnippet(snippet);
                    currentMarker.showInfoWindow();
                } else {
                    Toast.makeText(getApplicationContext(), appContext.getResources().getString(R.string.error_invalid_address), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        @Override
        public void onErrorResponseGetPlaceByLatLng(Exception exception) {
            Toast.makeText(appContext, appContext.getResources().getString(R.string.error_retrieving_place), Toast.LENGTH_LONG).show();
            exception.printStackTrace();
        }

        @Override
        public void onResponseGetPlaceByAddress(Place place) { }
        @Override
        public void onErrorResponseGetPlaceByAddress(Exception exception) { }
    };

    public Place getCurrentPlace() { return this.currentPlace; }
}
