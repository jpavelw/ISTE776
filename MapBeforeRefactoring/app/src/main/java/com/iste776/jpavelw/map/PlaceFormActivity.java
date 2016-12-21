package com.iste776.jpavelw.map;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.iste776.jpavelw.map.DAO.Category;
import com.iste776.jpavelw.map.DAO.CategoryAdapter;
import com.iste776.jpavelw.map.DAO.CategoryDao;
import com.iste776.jpavelw.map.DAO.DBHandler;
import com.iste776.jpavelw.map.DAO.Place;
import com.iste776.jpavelw.map.DAO.PlaceDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class PlaceFormActivity extends AppCompatActivity {

    private EditText placeName;
    private EditText placeStreet;
    private EditText placeCity;
    private Spinner stateSpinner;
    private EditText placeZipcode;
    private Spinner countrySpinner;
    //private ImageView locationIcon;
    private List<StatesHandler.State> states;
    private StatesHandler statesHandler;
    private Button addPlace;
    private Button cancel;

    private RecyclerView categoriesRecyclerView;
    //private CategoryAdapter categoryAdapter;
    //private CategoryDao categoryDao;
    private PlaceDao placeDao;
    //private Query<Category> categoriesQuery;
    //private LinearLayout addCategory;
    //private DrawerLayout drawer;

    //private FragmentManager fragmentManager;
    //private AddCategoryDialog addCategoryDialog;
    //private boolean isCategorySet = false;
    //private String categoryId;

    //private int selectedPosition;
    private final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_form);

        //this.selectedPosition = -1;

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //this.drawer = (DrawerLayout) findViewById(R.id.drawer_place_form);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(selectedPosition == -1){
                    try {
                        selectedPosition = 0;
                        categoriesRecyclerView.getChildAt(selectedPosition).setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryLight));
                    } catch (NullPointerException e) { selectedPosition = -1; }
                }
                if(!isCategorySet){
                    isCategorySet = true;
                    categoriesRecyclerView.getChildAt(selectedPosition).setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryLight));
                }
            }
        };*/
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();

        /*this.locationIcon = (ImageView) findViewById(R.id.imageView);
        this.locationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Location pressed", Toast.LENGTH_LONG).show();
                Intent map = new Intent(PlaceFormActivity.this, MapActivity.class);
                //map.putExtra(PlacesActivity.CATEGORY_ID_KEY, categoryId);
                startActivityForResult(map, REQUEST_CODE);
            }
        });*/

        //this.addCategory = (LinearLayout) findViewById(R.id.addCategory);
        /*this.addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategoryDialog.show(fragmentManager, "dialog");
            }
        });*/
        this.cancel = (Button) findViewById(R.id.cancel_btn);
        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                setResult(RESULT_CANCELED, intentResult);
                finish();
            }
        });

        //this.categoryDao = DBHandler.getCategoryDao(getApplication());
        //this.categoriesQuery = categoryDao.queryBuilder().orderAsc(CategoryDao.Properties.Date).build();

        this.placeDao = DBHandler.getPlaceDao(getApplication());

        this.placeName = (EditText) findViewById(R.id.name_txt);
        this.placeStreet = (EditText) findViewById(R.id.street_txt);
        this.placeCity = (EditText) findViewById(R.id.city_txt);
        this.placeZipcode = (EditText) findViewById(R.id.zip_code_txt);
        this.addPlace = (Button) findViewById(R.id.add_btn);
        this.addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPlace();
            }
        });

        this.initializeStateSpinner();
        //this.initializeCategories();

        //Intent thisIntent = getIntent();
        //this.categoryId = thisIntent.getStringExtra(PlacesActivity.CATEGORY_ID_KEY);
    }

    /*private void closeDrawer(){
        this.drawer.closeDrawer(GravityCompat.START);
    }*/

    private void initializeStateSpinner(){
        statesHandler = new StatesHandler(PlaceFormActivity.this);
        this.states = statesHandler.getStatesList();
        this.states.add(0, statesHandler.new State("", "-- Select a State --"));
        this.stateSpinner = (Spinner) findViewById(R.id.state_spinner);
        this.stateSpinner.setAdapter(statesHandler.new StatesAdapter(PlaceFormActivity.this, android.R.layout.simple_spinner_dropdown_item));
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
        this.countrySpinner.setAdapter(ArrayAdapter.createFromResource(PlaceFormActivity.this, R.array.countries, android.R.layout.simple_spinner_dropdown_item));
        this.countrySpinner.setEnabled(false);
    }

    /*private void initializeCategories(){
        this.categoriesRecyclerView = (RecyclerView) findViewById(R.id.list_categories);
        this.categoryAdapter = new CategoryAdapter(this.categoriesQuery.list(), this.categoryClickListener);
        this.categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.categoriesRecyclerView.setAdapter(this.categoryAdapter);
        this.fragmentManager = getSupportFragmentManager();
        this.addCategoryDialog = new AddCategoryDialog();
        this.addCategoryDialog.setPositive(positiveListener);
    }*/

    /*private void addCategory(String name){
        Category category = new Category(name);
        try {
            this.categoryDao.insert(category);
            this.categoryAdapter.addCategory(category);
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_inserting_category) + " " + name, Toast.LENGTH_LONG).show();
        }
    }*/

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
        getMenuInflater().inflate(R.menu.place_form_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.use_gps) {
            Intent map = new Intent(PlaceFormActivity.this, MapActivity.class);
            startActivityForResult(map, REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*private CategoryAdapter.CategoryClickListener categoryClickListener = new CategoryAdapter.CategoryClickListener() {
        @Override
        public void onCategoryClick(int position) {
            Category category = categoryAdapter.getCategory(position);
            if(selectedPosition > -1){
                categoriesRecyclerView.getChildAt(selectedPosition).setBackgroundColor(Color.TRANSPARENT);
            }
            selectedPosition = position;
            categoriesRecyclerView.getChildAt(position).setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryLight));
            closeDrawer();
            Toast.makeText(getApplicationContext(), "ID: " + category.getID() + " Name: " + category.getName() + " Date: " + category.getDate(), Toast.LENGTH_LONG).show();
        }
    };*/

    /*private AddCategoryDialog.AddCategoryListener positiveListener = new AddCategoryDialog.AddCategoryListener() {
        @Override
        public void onAddCategoryClick(EditText addNewCategory) {
            String name = addNewCategory.getText().toString();
            addNewCategory.setText("");
            if(!name.trim().equals("")){
                addCategory(name);
            }
        }
    };*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
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
                this.placeZipcode.setText(data.getStringExtra(MapActivity.ZIP_CODE_KEY));
            }
        }
    }

    private void addNewPlace(){
        GMapAPI gMapAPI = new GMapAPI(PlaceFormActivity.this, gMapAPIListener);
        //int position = this.selectedPosition == -1 ? 0 : this.selectedPosition;
        long categoryId = PlacesActivity.categoryId == -1 ? 1 : PlacesActivity.categoryId;
        String address = this.placeStreet.getText().toString() + ", "
                + this.placeCity.getText().toString() + ", "
                + this.statesHandler.getStatesList().get(this.stateSpinner.getSelectedItemPosition()).getShortName();
        gMapAPI.getPlaceByAddress(address, categoryId);
    }

    private GMapAPI.GMapAPIListener gMapAPIListener = new GMapAPI.GMapAPIListener() {
        @Override
        public void onResponseGetPlaceByLatLng(Place place) {

        }

        @Override
        public void onErrorResponseGetPlaceByLatLng(Exception exception) {

        }

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
                    Toast.makeText(getApplicationContext(), "The address specified is not valid", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        @Override
        public void onErrorResponseGetPlaceByAddress(Exception exception) {
            Toast.makeText(getApplicationContext(), "Something went wrong adding by address", Toast.LENGTH_LONG).show();
            exception.printStackTrace();
        }
    };

}