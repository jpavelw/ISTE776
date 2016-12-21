package com.iste776.jpavelw.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.iste776.jpavelw.map.DAO.Place;

public class MapActivity extends AppCompatActivity {

    private JLocation jLocation;

    public static final String STREET_KEY = "STREET_KEY";
    public static final String CITY_KEY = "CITY_KEY";
    public static final String STATE_KEY = "STATE_KEY";
    public static final String ZIP_CODE_KEY = "ZIP_CODE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Place selectedPlace = jLocation.getCurrentPlace();
                if(selectedPlace != null){
                    prepareIntent(selectedPlace);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_retrieving_place), Toast.LENGTH_LONG).show();
                }
            }
        });

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_map);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        this.jLocation = new JLocation(getApplicationContext(), (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_map);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /*super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case USER_PLACE_PICKER_REQUEST_ACCESS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getCurrentLocation();
                }
            }
        }*/
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.jLocation.showMap();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.location_settings) {
            if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                this.jLocation.showMap();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void prepareIntent(Place selectedPlace){
        Intent intentResult = new Intent();
        intentResult.putExtra(STREET_KEY, selectedPlace.getStreet());
        intentResult.putExtra(CITY_KEY, selectedPlace.getCity());
        intentResult.putExtra(STATE_KEY, selectedPlace.getState());
        intentResult.putExtra(ZIP_CODE_KEY, selectedPlace.getZipCode());
        setResult(RESULT_OK, intentResult);
        finish();
    }

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.addCategory) {
            Toast.makeText(getApplicationContext(), "From add categories", Toast.LENGTH_LONG).show();
            //Snackbar.make(item, "Add", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_map);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
}
