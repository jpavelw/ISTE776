package com.iste776.jpavelw.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by jpavelw on 11/8/16.
 */

public class JLocation extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private static final String TAG = JLocation.class.getSimpleName();
    private LocationManager locationManager = null;
    private Location location = null;
    private Context appContext = null;
    private GoogleMap googleMap = null;
    private Geocoder geocoder = null;
    private Marker currentMarker = null;
    private JSONObject jResponse = null;

    public JLocation(Context appContext, SupportMapFragment mapFragment) {
        if(mapFragment == null){
            Toast.makeText(this.appContext, "Shit is null", Toast.LENGTH_LONG).show();
            return;
        }
        mapFragment.getMapAsync(this);
        this.appContext = appContext;
        this.geocoder = new Geocoder(appContext, new Locale("en"));
        if (this.locationManager == null) {
            this.locationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
        }
    }

    public void showMap(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        this.getCurrentLocation();
        if(this.location == null){
            Toast.makeText(this.appContext, "Location is not ready", Toast.LENGTH_LONG).show();
        } else {
            LatLng currentLocation = new LatLng(this.location.getLatitude(), this.location.getLongitude());
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
            this.createMark(currentLocation);
        }
    }

    private void getCurrentLocation(){
        if(this.googleMap == null){
            Toast.makeText(this.appContext, "Maps is not ready", Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "Fail to request location update, ignore", e);
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
            Log.e(TAG, "Fail to request geocoder", e);
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
                Toast.makeText(this.appContext, "Maps is not ready", Toast.LENGTH_LONG).show();
            } else {
                if(this.currentMarker == null){
                    this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
                this.createMark(latLng);
            }
        }
    }

    private void getSnippet(LatLng latLng){
        String url = "http://jpavelw.me:2957/getaddressbygps?latlng=" + latLng.latitude + "," + latLng.longitude;
        RequestQueue queue = Volley.newRequestQueue(appContext);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("OK")){
                        jResponse = response;
                        String snippet = response.getString("formatted_address");
                        currentMarker.setSnippet(snippet);
                        currentMarker.showInfoWindow();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(appContext, "Could not retrieve address", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonRequest);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        this.googleMap.setOnMapClickListener(this);
        this.googleMap.setOnMarkerClickListener(this);
    }
}
