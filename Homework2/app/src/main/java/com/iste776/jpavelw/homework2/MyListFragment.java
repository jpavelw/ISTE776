package com.iste776.jpavelw.homework2;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpavelw on 10/5/16.
 */

public class MyListFragment extends ListFragment {
    private ArrayList<String> schools;
    private ArrayAdapter<String> schoolsArrayAdapter;
    private SchoolSelectedListener schoolSelectedListener;

    private AdapterView.OnItemClickListener schoolsOnItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    schoolSelectedListener.onSelectedSchool(
                            ((TextView) view).getText().toString()
                    );
                }
            };

    public interface SchoolSelectedListener {
        void onSelectedSchool(String schoolAbbreviation);
    }

    private static class ViewHolder{
        TextView schoolTextView;
    }

    private class SchoolArrayAdapter<T> extends ArrayAdapter<String> {
        private Context context;
        private LayoutInflater inflater;
        private List<String> schools;

        public SchoolArrayAdapter(Context context, int textViewId, List<String> schools){
            super(context, -1, schools);
            this.context = context;
            this.schools = schools;
            inflater = (LayoutInflater) context.getSystemService(this.context.LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(null == convertView){
                convertView = inflater.inflate(R.layout.list_school, null);
                viewHolder = new ViewHolder();
                viewHolder.schoolTextView = (TextView) convertView.findViewById(R.id.item_list);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            String abbreviation = schools.get(position);
            viewHolder.schoolTextView.setText(abbreviation);

            return convertView;
        }
    }

    public void setSchoolSelectedListener(SchoolSelectedListener listener){
        this.schoolSelectedListener = listener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.schools = School.getSchoolAbbreviations();
        setListAdapter(new SchoolArrayAdapter<String>(
                this.getActivity(),
                R.layout.list_school,
                this.schools
        ));
        ListView thisLisView = getListView();
        thisLisView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        thisLisView.setOnItemClickListener(schoolsOnItemClickListener);
    }
}
