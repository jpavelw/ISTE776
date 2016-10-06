package com.iste776.jpavelw.intentsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ExplicitlyLoaded extends AppCompatActivity {

    private final String TAG = "Lab-Intents-Explicit";
    private EditText providedTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicitly_loaded);

        // Get a reference to the EditText field
        providedTxt = (EditText) findViewById(R.id.providedText);

        // Declare and setup "Enter" button
        Button enterBtn = (Button) findViewById(R.id.enterBtn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            // Call enterClicked() when pressed
            @Override
            public void onClick(View view) {
                enterClicked();
            }
        });

    }

    private void enterClicked(){
        Log.i(this.TAG, "Entered enterClicked()");
        // TODO - Save user provided input from the EditText field
        String providedText = this.providedTxt.getText().toString();
        // TODO - Create a new intent and save the input from the EditText field as an extra
        Intent intentResult = new Intent();
        intentResult.putExtra("givenText", providedText);
        // TODO - Set Activity's result with result code RESULT_OK
        setResult(RESULT_OK, intentResult);
        // TODO - Finish the Activity
        finish();
    }
}
