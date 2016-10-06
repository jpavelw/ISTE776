package com.iste776.jpavelw.activitylab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityTwo extends AppCompatActivity {

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
        setContentView(R.layout.activity_activity_two);

        Button closeActivityBtn = (Button) findViewById(R.id.closeActivityTwoBtn);
        closeActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

        TextView onCreateText2 = (TextView) findViewById(R.id.onCreateText2);
        TextView onStartText2 = (TextView) findViewById(R.id.onStartText2);
        TextView onResumeText2 = (TextView) findViewById(R.id.onResumeText2);
        TextView onRestartText2 = (TextView) findViewById(R.id.onRestartText2);

        String msg = getResources().getString(R.string.onCreateText) + " " + this.onCreateCounter;

        onCreateText2.setText(msg);
        msg = getResources().getString(R.string.onStartText) + " " + this.onStartCounter;
        onStartText2.setText(msg);
        msg = getResources().getString(R.string.onResumeText) + " " + this.onResumeCounter;
        onResumeText2.setText(msg);
        msg = getResources().getString(R.string.onRestartText) + " " + this.onRestartCounter;
        onRestartText2.setText(msg);
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
