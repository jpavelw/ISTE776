package com.iste776.jpavelw.map;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

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

    public StatesHandler(Activity activity){
        try {
            JSONArray jStates = new JSONArray(this.loadFromJson(activity));
            for(int index = 0; index < jStates.length(); index++){
                JSONObject jState = jStates.getJSONObject(index);
                StatesHandler.State state = new StatesHandler.State(jState.getString("shortName"), jState.getString("longName"));
                this.statesList.add(state);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public List<StatesHandler.State> getStatesList(){ return this.statesList; }

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
    }

    public class StatesAdapter extends ArrayAdapter {

        private Context context;

        public StatesAdapter(Context context, int resource){
            super(context, resource, statesList);
            this.context = context;
        }

        private View getCustomView(int position, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            CheckedTextView textView = (CheckedTextView) layout;
            textView.setText(statesList.get(position).getLongName());
            return layout;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            return this.getCustomView(position, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            return this.getCustomView(position, parent);
        }
    }
}
