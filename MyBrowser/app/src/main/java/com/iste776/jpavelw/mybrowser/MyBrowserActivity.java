package com.iste776.jpavelw.mybrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyBrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_browser);

        // Save the String passed with the intent
        String url = getIntent().getDataString();

        if (null == url)
            url = "No Data Provided";

        // Get a reference to the TextView and set the text it to the String
        TextView textView = (TextView) findViewById(R.id.url);
        textView.setText(url);
    }
}
