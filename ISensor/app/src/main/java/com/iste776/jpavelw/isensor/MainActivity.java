package com.iste776.jpavelw.isensor;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView txtView;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtView = (TextView) findViewById(R.id.txtSensors);
        this.txtView.setVisibility(View.GONE);

        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = this.sensorManager.getSensorList(Sensor.TYPE_ALL);

        for(Sensor sensor : sensors){
            this.txtView.append("\nName: " + sensor.getName() + "\nVendor: " + sensor.getVendor() + "\n");
        }
        this.txtView.setVisibility(View.VISIBLE);
    }
}
