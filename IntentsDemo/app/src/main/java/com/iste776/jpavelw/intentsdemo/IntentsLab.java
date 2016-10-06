package com.iste776.jpavelw.intentsdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntentsLab extends AppCompatActivity {

    private final int REQUEST_CODE = 1;
    private final String TAG = "Lab-Intents";
    private final String URL = "https://www.google.com";
    // For use with app chooser
    private final String CHOOSER_TEXT = "Load " + this.URL + " with:";

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intents_lab);

        // Get reference to the textView
        this.txt = (TextView) findViewById(R.id.dataTxt);
        txt.setText(getResources().getString(R.string.default_text));

        // Declare and setup Explicit Activation button
        Button explicitBtn = (Button) findViewById(R.id.explicitBtn);
        explicitBtn.setOnClickListener(new View.OnClickListener() {
            // Call startExplicitActivation() when pressed
            @Override
            public void onClick(View view) {
                startExplicitActivation();
            }
        });

        // Declare and setup Implicit Activation button
        Button implicitBtn = (Button) findViewById(R.id.implicitBtn);
        implicitBtn.setOnClickListener(new View.OnClickListener() {
            // Call startImplicitActivation() when pressed
            @Override
            public void onClick(View view) {
                startImplicitActivation();
            }
        });
    }

    // Start the ExplicitlyLoadedActivity
    private void startExplicitActivation(){
        Log.i(this.TAG, "Entered startExplicitActivation()");

        // TODO - Create a new intent to launch the ExplicitlyLoadedActivity class
        //Intent explicitIntent = null;
        Intent explicitIntent = new Intent(IntentsLab.this, ExplicitlyLoaded.class);

        // TODO - Start an Activity using that intent and the request code defined above
        startActivityForResult(explicitIntent, this.REQUEST_CODE);

    }

    // Start a Browser Activity to view a web page or its URL
    private void startImplicitActivation(){
        Log.i(this.TAG, "Entered startImplicitActivation()");

        // TODO - Create a base intent for viewing a URL
        // (HINT:  second parameter uses Uri.parse())
        //Intent baseIntent = null;
        Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.URL));

        // TODO - Create a chooser intent, for choosing which Activity
        // will carry out the baseIntent
        // (HINT: Use the Intent class' createChooser() method)
        //Intent chooserIntent = null;
        Intent chooserIntent = Intent.createChooser(baseIntent, this.CHOOSER_TEXT);

        Log.i(this.TAG, "Chooser Intent Action: " + chooserIntent.getAction());

        // TODO - Start the chooser Activity, using the chooser intent
        startActivity(chooserIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        Log.i(this.TAG, "Entered onActivityResult()");
        // TODO - Process the result only if this method received both a
        // RESULT_OK result code and a recognized request code
        // If so, update the Textview showing the user-entered text.
        Log.i(this.TAG, String.valueOf(resultCode));
        if(requestCode == this.REQUEST_CODE){
            if(resultCode == RESULT_OK){
                this.txt.setText(data.getStringExtra("givenText"));
            } else {
                this.txt.setText(getResources().getString(R.string.error_message));
            }
        }
    }
}
