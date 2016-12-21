package com.iste776.jpavelw.map;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpavelw on 11/13/16.
 */

public class StatesHandler {
    private final List<StatesHandler.State> statesList = new ArrayList<>();
    private final ArrayList<String> statesArray = new ArrayList<>();

    public StatesHandler(Activity activity){
        try {
            JSONArray jStates = new JSONArray(this.loadFromJson(activity));
            for(int index = 0; index < jStates.length(); index++){
                JSONObject jState = jStates.getJSONObject(index);
                StatesHandler.State state = new StatesHandler.State(jState.getString("shortName"), jState.getString("longName"));
                this.statesList.add(state);
                this.statesArray.add(jState.getString("longName"));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public List<StatesHandler.State> getStatesList(){ return this.statesList; }

    public ArrayList<String> getStatesArray(){ return this.statesArray; }

    private String loadFromJson(Activity activity){
        String json = null;
        try {
            InputStream is = activity.getAssets().open("states.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e){
            e.printStackTrace();
        }
        return json;
    }

    public class State {

        private String shortName;
        private String longName;

        public State(String shortName, String longName) {
            this.shortName = shortName;
            this.longName = longName;
        }

        public String getLongName() { return longName; }
        public String getShortName() { return shortName; }
        public void setLongName(String longName) { this.longName = longName; }
        public void setShortName(String shortName) { this.shortName = shortName; }
    }
}
