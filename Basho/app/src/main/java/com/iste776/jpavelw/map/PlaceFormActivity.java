package com.iste776.jpavelw.map;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iste776.jpavelw.map.dao.DBHandler;
import com.iste776.jpavelw.map.dao.Place;
import com.iste776.jpavelw.map.dao.PlaceDao;

import java.util.List;

public class PlaceFormActivity extends AppCompatActivity {

    private EditText placeName;
    private EditText placeStreet;
    private EditText placeCity;
    private Spinner stateSpinner;
    private EditText placeZipCode;
    private StatesHandler statesHandler;

    private PlaceDao placeDao;
    private final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_form);
        Button cancel = (Button) findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                setResult(RESULT_CANCELED, intentResult);
                finish();
            }
        });

        this.placeDao = DBHandler.getPlaceDao(getApplication());

        this.placeName = (EditText) findViewById(R.id.name_txt);
        this.placeStreet = (EditText) findViewById(R.id.street_txt);
        this.placeCity = (EditText) findViewById(R.id.city_txt);
        this.placeZipCode = (EditText) findViewById(R.id.zip_code_txt);
        Button addPlace = (Button) findViewById(R.id.add_btn);
        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPlace();
            }
        });

        this.initializeStateSpinner();
    }

    private void initializeStateSpinner(){
        statesHandler = new StatesHandler(PlaceFormActivity.this);
        List<StatesHandler.State> states = statesHandler.getStatesList();
        states.add(0, statesHandler.new State("", "-- Select a State --"));
        this.stateSpinner = (Spinner) findViewById(R.id.state_spinner);
        this.stateSpinner.setAdapter(statesHandler.new StatesAdapter(PlaceFormActivity.this, android.R.layout.simple_spinner_dropdown_item));

        Spinner countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        countrySpinner.setAdapter(ArrayAdapter.createFromResource(PlaceFormActivity.this, R.array.countries, android.R.layout.simple_spinner_dropdown_item));
        countrySpinner.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_place_form);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.place_form_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.use_gps) {
            Intent map = new Intent(PlaceFormActivity.this, MapActivity.class);
            startActivityForResult(map, REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == this.REQUEST_CODE){
            if(resultCode == RESULT_OK){
                this.placeStreet.setText(data.getStringExtra(MapActivity.STREET_KEY));
                this.placeCity.setText(data.getStringExtra(MapActivity.CITY_KEY));
                String state = data.getStringExtra(MapActivity.STATE_KEY);
                for(int i = 1; i < this.statesHandler.getStatesList().size(); i++){
                    if(this.statesHandler.getStatesList().get(i).getShortName().equals(state)){
                        this.stateSpinner.setSelection(i, true);
                        break;
                    }
                }
                this.placeZipCode.setText(data.getStringExtra(MapActivity.ZIP_CODE_KEY));
            }
        }
    }

    private void addNewPlace(){
        GMapAPI gMapAPI = new GMapAPI(PlaceFormActivity.this, gMapAPIListener);
        long categoryId = PlacesActivity.categoryId;

        String placeStreet = this.placeStreet.getText().toString();
        String placeCity = this.placeCity.getText().toString();
        String placeState = this.statesHandler.getStatesList().get(this.stateSpinner.getSelectedItemPosition()).getShortName();

        if(this.placeName.getText().toString().equals("") ||
                placeStreet.trim().equals("") ||
                placeCity.trim().equals("") ||
                placeState.trim().equals("")){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_empty_fields), Toast.LENGTH_LONG).show();
        } else {
            String address =  placeStreet+ ", " + placeCity + ", " + placeState;
            gMapAPI.getPlaceByAddress(address, categoryId);
        }
    }

    private GMapAPI.GMapAPIListener gMapAPIListener = new GMapAPI.GMapAPIListener() {
        @Override
        public void onResponseGetPlaceByLatLng(Place place) { }
        @Override
        public void onErrorResponseGetPlaceByLatLng(Exception exception) { }

        @Override
        public void onResponseGetPlaceByAddress(Place place) {
            try {
                if(place != null){
                    place.setName(placeName.getText().toString());
                    placeDao.insert(place);
                    Intent intentResult = new Intent();
                    setResult(RESULT_OK, intentResult);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_invalid_address), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        @Override
        public void onErrorResponseGetPlaceByAddress(Exception exception) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_saving_place), Toast.LENGTH_LONG).show();
            exception.printStackTrace();
        }
    };

}