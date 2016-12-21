package com.iste776.jpavelw.map;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PlaceForm extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Spinner stateSpinner;
    private Spinner countrySpinner;
    private ArrayList<String> states;
    private StatesHandler statesHandler;

    private RecyclerView categoriesRecyclerView;
    private MenuItemsHandler menuItemsHandler;


    private LinearLayout addCategory;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_form);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_place_form);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.drawer = (DrawerLayout) findViewById(R.id.drawer_place_form);
        this.addCategory = (LinearLayout) findViewById(R.id.addCategory);
        this.addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "From add categories", Toast.LENGTH_LONG).show();
                closeDrawer();
            }
        });

        this.initializeSpinner();
        this.initializeCategories();
    }

    private void closeDrawer(){
        this.drawer.closeDrawer(GravityCompat.START);
    }

    private void initializeSpinner(){
        this.statesHandler = new StatesHandler(PlaceForm.this);
        this.states = this.statesHandler.getStatesArray();
        this.states.add(0, "-- Select a State --");
        this.stateSpinner = (Spinner) findViewById(R.id.state_spinner);
        this.stateSpinner.setAdapter(new ArrayAdapter<>(PlaceForm.this, android.R.layout.simple_spinner_dropdown_item, this.states));
        this.stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long rowId) {
                if(position != 0){
                    StatesHandler.State state = statesHandler.getStatesList().get(position);
                    Toast.makeText(getApplicationContext(), "Short name: " + state.getShortName() + ". Long name: " + state.getLongName(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        this.countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        this.countrySpinner.setAdapter(ArrayAdapter.createFromResource(PlaceForm.this, R.array.countries, android.R.layout.simple_spinner_dropdown_item));
        this.countrySpinner.setEnabled(false);
    }

    private void initializeCategories(){
        this.menuItemsHandler = new MenuItemsHandler();
        this.categoriesRecyclerView = (RecyclerView) findViewById(R.id.list_categories);
        List<String> categories = this.menuItemsHandler.getCategoriesArray();
        RecyclerView.Adapter adapter = new ListMenuAdapter(categories);
        this.categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.categoriesRecyclerView.setAdapter(adapter);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.location_settings){

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.addCategory) {
            Toast.makeText(getApplicationContext(), "From add categories", Toast.LENGTH_LONG).show();
            //Snackbar.make(item, "Add", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_place_form);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}