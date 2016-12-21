package com.iste776.jpavelw.map;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.iste776.jpavelw.map.DAO.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jpavelw on 12/13/16.
 */

public class GMapAPI {
    private final String BASE_URL = "http://jpavelw.me:2957/";
    private Context appContext = null;
    private GMapAPIListener listener = null;

    public interface GMapAPIListener {
        void onResponseGetPlaceByLatLng(Place place);
        void onErrorResponseGetPlaceByLatLng(Exception exception);
        void onResponseGetPlaceByAddress(Place place);
        void onErrorResponseGetPlaceByAddress(Exception exception);
    }

    public GMapAPI(@NonNull Context appContext, @NonNull GMapAPIListener listener){
        this.appContext = appContext;
        this.listener = listener;
    }

    public void getPlaceByLatLng(final double LATITUDE, final double LONGITUDE, final long CATEGORY_ID){
        final String URL = this.BASE_URL + "getlocationbylatlng?latlng=" + LATITUDE + "," + LONGITUDE;
        RequestQueue queue = Volley.newRequestQueue(appContext);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("OK")){
                        Place place = getPlace(response, CATEGORY_ID);
                        listener.onResponseGetPlaceByLatLng(place);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponseGetPlaceByLatLng(error);
            }
        });
        queue.add(jsonRequest);
    }

    public void getPlaceByAddress(final String ADDRESS, final long CATEGORY_ID){
        String address = ADDRESS.replace(" ", "+");
        final String URL = this.BASE_URL + "getlocationbyaddress?address=" + address;
        RequestQueue queue = Volley.newRequestQueue(appContext);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("OK")){
                        Place place = getPlace(response, CATEGORY_ID);
                        listener.onResponseGetPlaceByAddress(place);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponseGetPlaceByAddress(error);
            }
        });
        queue.add(jsonRequest);
    }

    private Place getPlace(JSONObject response, long categoryId){
        Place place = null;
        try {
            String lat = String.valueOf(response.getJSONObject("location").getDouble("lat"));
            String lng = String.valueOf(response.getJSONObject("location").getDouble("lng"));
            JSONArray addressComponents = response.getJSONArray("address_components");
            int length = addressComponents.length();
            String streetNumber = "";
            String route = "";
            String city = "";
            String state = "";
            String zipcode = "";
            String country = "";
            for(int i = 0; i < length; i++){
                JSONObject jObject = addressComponents.getJSONObject(i);
                String type = jObject.getJSONArray("types").getString(0);
                switch (type){
                    case "street_number":
                        streetNumber = jObject.getString("long_name");
                        break;
                    case "route":
                        route = jObject.getString("long_name");
                        break;
                    case "locality":
                        city = jObject.getString("long_name");
                        break;
                    case "administrative_area_level_1":
                        state = jObject.getString("short_name");
                        break;
                    case "country":
                        country = jObject.getString("short_name");
                        break;
                    case "postal_code":
                        zipcode = jObject.getString("long_name");
                        break;
                }
            }
            if(country.equals(this.appContext.getResources().getStringArray(R.array.countries)[0])) {
                place = new Place(null, categoryId, "", streetNumber + " " + route, city, state, zipcode, country, lat, lng);
            } else {
                Toast.makeText(appContext, appContext.getResources().getString(R.string.error_not_US_place), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) { e.printStackTrace(); }
        return place;
    }
}
