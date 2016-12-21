package com.iste776.jpavelw.map.DAO;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iste776.jpavelw.map.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpavelw on 12/14/16.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>{

    private List<Place> dataSet;
    private PlaceClickListener clickListener;

    public interface PlaceClickListener {
        void onPlaceClick(int position);
        void onNavigationClick(int position);
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView listItemDetailed;
        private LinearLayout textLayout;
        private LinearLayout navigationLayout;

        public PlaceViewHolder(View view, final PlaceClickListener clickListener){
            super(view);
            this.textView = (TextView) view.findViewById(R.id.list_item);
            this.listItemDetailed = (TextView) view.findViewById(R.id.list_item_detailed);
            this.textLayout = (LinearLayout) view.findViewById(R.id.text_layout);
            this.navigationLayout = (LinearLayout) view.findViewById(R.id.navigation_layout);
            /*this.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener != null){
                        clickListener.onPlaceClick(getAdapterPosition());
                    }
                }
            });*/
            this.textLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener != null){
                        clickListener.onPlaceClick(getAdapterPosition());
                    }
                }
            });

            this.navigationLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener != null){
                        clickListener.onNavigationClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public PlaceAdapter(@NonNull List<Place> dataSet, @NonNull PlaceClickListener clickListener){
        this.dataSet = dataSet;
        this.clickListener = clickListener;
    }

    public PlaceAdapter(@NonNull PlaceClickListener clickListener){
        this.clickListener = clickListener;
        this.dataSet = new ArrayList<>();
    }

    public void setPlaces(@NonNull List<Place> dataSet){
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    public void addPlace(@NonNull Place place){
        this.dataSet.add(place);
        notifyItemInserted(this.dataSet.size()-1);
    }

    public Place getPlace(int position) { return this.dataSet.get(position); }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new PlaceViewHolder(view, this.clickListener);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        holder.textView.setText(this.dataSet.get(position).getName());
        holder.listItemDetailed.setText(this.dataSet.get(position).toString());
    }

    @Override
    public int getItemCount() { return this.dataSet.size(); }
}
