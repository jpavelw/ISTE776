package com.iste776.jpavelw.homework2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jpavelw on 10/5/16.
 */

public class ItemDetail extends Fragment {
    private School school;

    public static ItemDetail newInstance(String abbreviation){
        ItemDetail myFragment = new ItemDetail();
        Bundle args = new Bundle();
        args.putString("SCHOOL", abbreviation);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = this.getArguments();
        this.school = new School((String) args.get("SCHOOL"));
        View myView = inflater.inflate(R.layout.school_detail, container, false);
        ((TextView) myView.findViewById(R.id.school_abb_lbl)).setText(this.school.getAbbreviation());
        ((TextView) myView.findViewById(R.id.school_name_lbl)).setText(getResources().getText(R.string.school_name_label) + " " + this.school.getName());
        ((TextView) myView.findViewById(R.id.school_foundation_lbl)).setText(getResources().getText(R.string.school_founded_label) + " " + String.valueOf(this.school.getFoundationYear()));
        ((TextView) myView.findViewById(R.id.school_location_lbl)).setText(getResources().getString(R.string.school_location_label) + " " + this.school.getLocation());
        return myView;
    }
}
