package com.iste776.jpavelw.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText txtCity;
    private TextView lblResponse;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtCity = (EditText) findViewById(R.id.txt_city);
        this.lblResponse = (TextView) findViewById(R.id.lbl_response);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Button btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RetrieveFeedTask().execute();
            }
        });

    }

    private class RetrieveFeedTask extends AsyncTask<Void, Void, String>{

        String city;

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            lblResponse.setText("");
            city = txtCity.getText().toString();
            city = city.replace(" ", "+");
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(Credentials.API_URL + city + "&appid=" + Credentials.API_KEY);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        builder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return builder.toString();
                } finally {
                    urlConnection.disconnect();
                }

            }
            catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            //super.onPostExecute(s);
            if(response == null)
                response = "THERE WAS AN ERROR";
            progressBar.setVisibility(View.GONE);
            StringBuilder fullResponse = new StringBuilder();
            //Log.i("INFO", response);
            //lblResponse.setText(response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject coord = jsonObject.getJSONObject("coord");
                String lon = coord.getString("lon").toString();
                String lat = coord.getString("lat").toString();
                //lblResponse.setText("latitude is: " + lat + "\nlongitude is " + lon);
                fullResponse.append("Latitude: " + lat + "\nLongitude:" + lon + "\n");

                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                JSONObject weatherOne = weatherArray.getJSONObject(0);
                String description = weatherOne.getString("description");
                String weatherMain = weatherOne.getString("main");
                //Log.i("WEATHER ARRAY 0 ", description);
                //lblResponse.setText(description);
                fullResponse.append("Main weather: " + weatherMain + "\nWeather description: " + description + "\n");

                JSONObject main = jsonObject.getJSONObject("main");
                String temp = main.getString("temp");
                String temp_min = "";
                String temp_max = "";
                String regx = "\\.";
                try {
                    temp_min = main.getString("temp_min");
                } finally {}

                try {
                    temp_max = main.getString("temp_max");
                } finally {}

                String humidity = main.getString("humidity");
                fullResponse.append("Temperature: " + temp.split(regx)[0] + "°F\n");
                if(!temp_min.equals(""))
                    fullResponse.append("Min temperature: " + temp_min.split(regx)[0] + "°F\n");
                if(!temp_max.equals(""))
                    fullResponse.append("Max temperature: " + temp_max.split(regx)[0] + "°F\n");
                fullResponse.append("Humidity: " + humidity + "%\n");

                JSONObject clouds = jsonObject.getJSONObject("clouds");
                String cloudPercentage = clouds.getString("all");
                fullResponse.append("Cloud percentage: " + cloudPercentage + "%\n");

                JSONObject sys = jsonObject.getJSONObject("sys");
                //String sunset = sys.getString("sunset").toString();
                //lblResponse.setText(sunset);

                long sunset = sys.getLong("sunset");
                Date sunsetTime = new Date(sunset * 1000L);
                //lblResponse.setText("Sunset tonight: " + sunsetTime.toString());
                fullResponse.append("Sunset tonight: " + sunsetTime.toString() + "\n");

                lblResponse.setText(fullResponse.toString());

            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
