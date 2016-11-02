package com.iste776.jpavelw.edibleroc;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class EdibleActivity extends AppCompatActivity {

    //private int selectedItem;
    private String addressItem;
    private Resources res;
    private boolean isValid = false;

    private boolean isFastFoodTouched = false;
    private boolean isCasualDiningTouched = false;
    private boolean isEliteCuisineTouched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edible);

        this.res = getResources();

        // Get a reference to the Spinner
        final Spinner spinFastFood = (Spinner) findViewById(R.id.spinFastFood);
        final Spinner spinCasualDining = (Spinner) findViewById(R.id.spinCasualDining) ;
        final Spinner spinEliteCuisine = (Spinner) findViewById(R.id.spinEliteCuisine);
        // Get a reference to the Go Button
        Button btnGo = (Button) findViewById(R.id.btnGo);

        // Create an Adapter that holds a list of fast food locations
        // An ArrayAdapter is an adapter backed by an array of objects.
        // It links the array to the Adapter View.
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.fastfood_array, R.layout.dropdown);
        ArrayList<String> fastFoodSource = new ArrayList<>(Arrays.asList(this.res.getStringArray(R.array.fastfood_array)));
        fastFoodSource.add(0, "- - - -");
        ArrayAdapter<String> fastFoodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fastFoodSource);
        // also with android:entries="@array/fastfood_array" in the layout file
        // Set the Adapter for the spinner
        spinFastFood.setAdapter(fastFoodAdapter);
        // Set an setOnItemSelectedListener on the spinner
        spinFastFood.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isFastFoodTouched = true;
                isCasualDiningTouched = false;
                isEliteCuisineTouched = false;
                return false;
            }
        });
        spinFastFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Display the fast food restaurant on the button
                //selectedItem = i-1;
                if(isFastFoodTouched){
                    if(i > 0){
                        isValid = true;
                        addressItem = res.getStringArray(R.array.fastfood_addr)[i-1];
                        spinCasualDining.setSelection(0, true);
                        spinEliteCuisine.setSelection(0, true);
                    } else {
                        isValid = false;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        ArrayList<String> casualDiningSource = new ArrayList<>(Arrays.asList(this.res.getStringArray(R.array.casualdining_array)));
        casualDiningSource.add(0, "- - - -");
        ArrayAdapter<String> casualDiningAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, casualDiningSource);
        spinCasualDining.setAdapter(casualDiningAdapter);

        spinCasualDining.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isFastFoodTouched = false;
                isCasualDiningTouched = true;
                isEliteCuisineTouched = false;
                return false;
            }
        });

        spinCasualDining.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(isCasualDiningTouched){
                    if(i > 0){
                        isValid = true;
                        addressItem = res.getStringArray(R.array.casualdining_addr)[i-1];
                        spinFastFood.setSelection(0, true);
                        spinEliteCuisine.setSelection(0, true);
                    } else {
                        isValid = false;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        ArrayList<String> eliteCuisineSource = new ArrayList<>(Arrays.asList(this.res.getStringArray(R.array.elitecuisine_array)));
        eliteCuisineSource.add(0, "- - - -");
        ArrayAdapter<String> eliteCuisineAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, eliteCuisineSource);
        spinEliteCuisine.setAdapter(eliteCuisineAdapter);

        spinEliteCuisine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isFastFoodTouched = false;
                isCasualDiningTouched = false;
                isEliteCuisineTouched = true;
                return false;
            }
        });

        spinEliteCuisine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isEliteCuisineTouched){
                    if(i > 0){
                        isValid = true;
                        addressItem = res.getStringArray(R.array.elitecuisine_addr)[i-1];
                        spinFastFood.setSelection(0, true);
                        spinCasualDining.setSelection(0, true);
                    } else {
                        isValid = false;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            // Called when user clicks the Show Map button
            @Override
            public void onClick(View view) {
                if(isValid) {
                    // Process text for network transmission
                    //String[] addresses = res.getStringArray(R.array.fastfood_addr);
                    //addressItem = addresses[selectedItem];

                    // Create Intent object for starting Google Maps application
                    Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + addressItem));
                    // Use the Intent to start Google Maps application using Activity.startActivity()
                    startActivity(geoIntent);
                } else {
                    Toast.makeText(getApplicationContext(), res.getText(R.string.wrong_index_error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
