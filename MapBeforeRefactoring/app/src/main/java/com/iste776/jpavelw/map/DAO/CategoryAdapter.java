package com.iste776.jpavelw.map.DAO;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iste776.jpavelw.map.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpavelw on 11/17/16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> dataSet;
    private CategoryClickListener clickListener;
    private Context appContext;

    public interface CategoryClickListener {
        void onCategoryClick(int position);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private View view;

        public CategoryViewHolder(View view, final CategoryClickListener clickListener){
            super(view);
            this.view = view;
            this.textView = (TextView) view.findViewById(R.id.list_menu_item);
            this.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener != null){
                        clickListener.onCategoryClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public CategoryAdapter(@NonNull List<Category> dataSet, @NonNull CategoryClickListener clickListener, @NonNull Context appContext) {
        this.dataSet = dataSet;
        this.clickListener = clickListener;
        this.appContext = appContext;
    }
    public CategoryAdapter(@NonNull CategoryClickListener clickListener){
        this.clickListener = clickListener;
        this.dataSet = new ArrayList<>();
    }

    public void setCategories(List<Category> categories){
        this.dataSet = categories;
        notifyDataSetChanged();
    }

    public void addCategory(Category category){
        this.dataSet.add(category);
        notifyItemInserted(this.dataSet.size()-1);
    }

    public Category getCategory(int position){ return this.dataSet.get(position); }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.textView.setText(this.dataSet.get(position).getName());
        if(position == 0)
            holder.view.setBackgroundColor(ContextCompat.getColor(this.appContext, R.color.colorPrimaryLight));
    }

    @Override
    public int getItemCount() { return this.dataSet.size(); }

    @Override
    public CategoryViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_list, parent, false);
        return new CategoryViewHolder(view, this.clickListener);
    }
}
