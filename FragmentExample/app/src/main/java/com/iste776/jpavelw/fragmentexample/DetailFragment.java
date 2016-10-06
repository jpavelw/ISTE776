package com.iste776.jpavelw.fragmentexample;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jpavelw on 9/25/16.
 */

public class DetailFragment extends Fragment{
    private String itemName;

    public static DetailFragment newInstance(String itemName){
        DetailFragment myFragment = new DetailFragment();

        Bundle args = new Bundle();
        args.putString("ITEM", itemName);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        Bundle args = this.getArguments();
        //itemName = args.getString("ITEM","Unknown");
        itemName = (String) args.get("ITEM");

        //inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.detail_fragment, container, false);
        ((TextView) myView.findViewById(R.id.info_about_text)).setText(itemName);
        return myView;
    }
}
