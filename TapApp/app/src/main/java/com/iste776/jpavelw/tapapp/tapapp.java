package com.iste776.jpavelw.tapapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class tapapp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapapp);
    }

    public void onButtonApp(View v){
        Random rdm = new Random();
        int[] myRandoms = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int response = myRandoms[rdm.nextInt(10)];
        StringBuilder myToastMsg = new StringBuilder();
        myToastMsg.append("Your secret number is " + response);
        Toast myToast = Toast.makeText(getApplicationContext(), myToastMsg, Toast.LENGTH_LONG);
        myToast.show();
    }
}
