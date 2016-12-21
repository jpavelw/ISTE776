package com.iste776.jpavelw.map;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jpavelw on 11/12/16.
 */

public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuAdapter.ViewHolder> {

    private List<String> dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(View v){
            super(v);
            this.textView = (TextView) v.findViewById(R.id.list_menu_item);
        }
    }

    public void addElement(String element){
        this.dataSet.add(element);
    }

    public ListMenuAdapter(List<String> dataSet){
        this.dataSet = dataSet;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(this.dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return this.dataSet.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_list, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = ((RecyclerView) parent).getChildLayoutPosition(v);
                Toast.makeText(parent.getContext(), dataSet.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return new ViewHolder(v);
    }
}
