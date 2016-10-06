package com.iste776.jpavelw.activitylab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityOne extends AppCompatActivity {

    private int onCreateCounter = 0;
    private int onStartCounter = 0;
    private int onResumeCounter = 0;
    private int onRestartCounter = 0;

    private final String ON_CREATE_KEY = "onCreate";
    private final String ON_START_KEY = "onStart";
    private final String ON_RESUME_KEY = "onResume";
    private final String ON_RESTART_KEY = "onRestart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_one);

        Button startActivityTwoBtn = (Button) findViewById(R.id.startActivityTwoBtn);
        startActivityTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityOne.this, ActivityTwo.class);
                startActivity(intent);
            }
        });

        ++this.onCreateCounter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ++this.onStartCounter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ++this.onResumeCounter;

        TextView onCreateTxt = (TextView) findViewById(R.id.onCreateText);
        TextView onStartTxt = (TextView) findViewById(R.id.onStartText);
        TextView onResumeTxt = (TextView) findViewById(R.id.onResumeText);
        TextView onRestartTxt = (TextView) findViewById(R.id.onRestartText);

        String msg = getResources().getString(R.string.onCreateText) + " " + this.onCreateCounter;

        onCreateTxt.setText(msg);
        msg = getResources().getString(R.string.onStartText) + " " + this.onStartCounter;
        onStartTxt.setText(msg);
        msg = getResources().getString(R.string.onResumeText) + " " + this.onResumeCounter;
        onResumeTxt.setText(msg);
        msg = getResources().getString(R.string.onRestartText) + " " + this.onRestartCounter;
        onRestartTxt.setText(msg);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ++this.onRestartCounter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(this.ON_CREATE_KEY, this.onCreateCounter);
        outState.putInt(this.ON_START_KEY, this.onStartCounter);
        outState.putInt(this.ON_RESUME_KEY, this.onResumeCounter);
        outState.putInt(this.ON_RESTART_KEY, this.onRestartCounter);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        this.onCreateCounter += savedInstanceState.getInt(this.ON_CREATE_KEY);
        this.onStartCounter += savedInstanceState.getInt(this.ON_START_KEY);
        this.onResumeCounter += savedInstanceState.getInt(this.ON_RESUME_KEY);
        this.onRestartCounter += savedInstanceState.getInt(this.ON_RESTART_KEY);
    }
}
