package com.iste776.jpavelw.map;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iste776.jpavelw.map.dao.Category;
import com.iste776.jpavelw.map.dao.CategoryAdapter;
import com.iste776.jpavelw.map.dao.CategoryDao;
import com.iste776.jpavelw.map.dao.DBHandler;
import com.iste776.jpavelw.map.dao.Place;
import com.iste776.jpavelw.map.dao.PlaceAdapter;
import com.iste776.jpavelw.map.dao.PlaceDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    private RecyclerView categoriesRecyclerView;
    private CategoryAdapter categoryAdapter;
    private CategoryDao categoryDao;
    private Query<Category> categoriesQuery;
    private LinearLayout addCategory;
    private DrawerLayout drawer;

    private PlaceAdapter placeAdapter;
    private PlaceDao placeDao;

    private FragmentManager fragmentManager;
    private AddCategoryDialog addCategoryDialog;

    private int selectedPosition;
    public static long categoryId;

    private final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        this.selectedPosition = -1;
        categoryId = -1;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.drawer = (DrawerLayout) findViewById(R.id.drawer_places);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        this.addCategory = (LinearLayout) findViewById(R.id.addCategory);
        this.addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategoryDialog.show(fragmentManager, "dialog");
            }
        });

        this.categoryDao = DBHandler.getCategoryDao(getApplication());
        this.categoriesQuery = this.categoryDao.queryBuilder().orderAsc(CategoryDao.Properties.Date).build();

        this.placeDao = DBHandler.getPlaceDao(getApplication());

        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categoryAdapter.getItemCount() > 0){
                    Intent placeForm = new Intent(PlacesActivity.this, PlaceFormActivity.class);
                    startActivityForResult(placeForm, REQUEST_CODE);
                } else {
                    addCategory.callOnClick();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.empty_category_list_message), Toast.LENGTH_LONG).show();
                }
            }
        });
        this.initializeCategories();
        this.initializeList();
    }

    private void initializeList(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_places);
        this.placeAdapter = new PlaceAdapter(this.filterList(), this.placeClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.placeAdapter);
    }

    private List<Place> filterList(){
        if(this.categoryAdapter.getItemCount() == 0)
            return new ArrayList<>();
        Query<Place> placesQuery = this.placeDao.queryBuilder().where(PlaceDao.Properties.CategoryId.eq(categoryId)).orderAsc(PlaceDao.Properties.Date).build();
        return placesQuery.list();
    }

    private void closeDrawer(){
        this.drawer.closeDrawer(GravityCompat.START);
        this.placeAdapter.setPlaces(filterList());
    }

    private void initializeCategories(){
        this.categoriesRecyclerView = (RecyclerView) findViewById(R.id.list_categories);
        this.categoryAdapter = new CategoryAdapter(this.categoriesQuery.list(), this.categoryClickListener, getApplicationContext());
        this.categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.categoriesRecyclerView.setAdapter(this.categoryAdapter);
        this.fragmentManager = getSupportFragmentManager();
        this.addCategoryDialog = new AddCategoryDialog();
        this.addCategoryDialog.setPositive(positiveListener);
        if(this.categoryAdapter.getItemCount() > 0){
            categoryId = this.categoryAdapter.getCategory(0).getID();
            this.selectedPosition = 0;
        }
    }

    private void addCategory(String name){
        Category category = new Category(name);
        try {
            this.categoryDao.insert(category);
            this.categoryAdapter.addCategory(category);
            if(selectedPosition == -1){
                categoryId = this.categoryAdapter.getCategory(0).getID();
                this.selectedPosition = 0;
                this.fab.callOnClick();
            }
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_inserting_category) + " " + name, Toast.LENGTH_LONG).show();
        }
    }

    private CategoryAdapter.CategoryClickListener categoryClickListener = new CategoryAdapter.CategoryClickListener() {
        @Override
        public void onCategoryClick(int position) {
            Category category = categoryAdapter.getCategory(position);
            if(selectedPosition > -1){
                categoriesRecyclerView.getChildAt(selectedPosition).setBackgroundColor(Color.TRANSPARENT);
            } /*else {
                categoriesRecyclerView.getChildAt(0).setBackgroundColor(Color.TRANSPARENT);
            }*/
            selectedPosition = position;
            categoryId = category.getID();
            categoriesRecyclerView.getChildAt(position).setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryLight));
            closeDrawer();
        }
    };

    private AddCategoryDialog.AddCategoryListener positiveListener = new AddCategoryDialog.AddCategoryListener() {
        @Override
        public void onAddCategoryClick(EditText addNewCategory) {
            String name = addNewCategory.getText().toString();
            addNewCategory.setText("");
            if(!name.trim().equals("")){
                addCategory(name);
            }
        }
    };

    private PlaceAdapter.PlaceClickListener placeClickListener = new PlaceAdapter.PlaceClickListener() {
        @Override
        public void onPlaceClick(int position) {
            Place place = placeAdapter.getPlace(position);
            Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + place.getLatitude() + "," + place.getLongitude() + "?q=" + Uri.encode(place.toString())));
            geoIntent.setPackage("com.google.android.apps.maps");
            startActivity(geoIntent);
        }

        @Override
        public void onNavigationClick(int position) {
            Place place = placeAdapter.getPlace(position);
            Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + Uri.encode(place.toString())));
            geoIntent.setPackage("com.google.android.apps.maps");
            startActivity(geoIntent);
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_places);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == this.REQUEST_CODE){
            if(resultCode == RESULT_OK){
                this.placeAdapter.setPlaces(filterList());
            } else if(resultCode != RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_inserting_place), Toast.LENGTH_LONG).show();
            }
        }
    }
}
