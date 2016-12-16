package com.iste776.jpavelw.wanelin;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TelephonyManager telephonyManager;
    private PhoneStateListener phoneStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                        ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 1);
                } else {
                    if(doStuff()){
                        Toast.makeText(getApplicationContext(), "Got location", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed location", Toast.LENGTH_LONG).show();
                    }
                    if(!getStrength()){
                        Toast.makeText(getApplicationContext(), "Failed signal", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length == 2){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(this.doStuff()){
                    Toast.makeText(getApplicationContext(), "Got location", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed location", Toast.LENGTH_LONG).show();
                }
            }
            if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
                if(!getStrength()){
                    Toast.makeText(getApplicationContext(), "Failed signal", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private boolean doStuff(){
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        try {
            List<String> providers = locationManager.getProviders(true);
            for(String provider : providers){
                Location loc = locationManager.getLastKnownLocation(provider);
                if(loc == null){
                    continue;
                }
                if(location == null || loc.getAccuracy() < location.getAccuracy()){
                    location = loc;
                }
            }
        } catch (SecurityException e){
            Log.e("MainActivity", "Fail to request location", e);
        }
        return (location != null);
    }

    private boolean getStrength(){
        this.telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        this.phoneStateListener = new PhoneStateListener(){
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);
                stopListening((2 * signalStrength.getGsmSignalStrength()) - 113);
            }
        };
        try {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void stopListening(int signalStrength){
        Toast.makeText(getApplicationContext(), String.valueOf(signalStrength), Toast.LENGTH_LONG).show();
        this.telephonyManager.listen(this.phoneStateListener, PhoneStateListener.LISTEN_NONE);
    }
}
