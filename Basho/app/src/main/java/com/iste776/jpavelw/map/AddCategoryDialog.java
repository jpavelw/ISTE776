package com.iste776.jpavelw.map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;

/**
 * Created by jpavelw on 11/17/16.
 */

public class AddCategoryDialog extends DialogFragment {

    private AddCategoryListener positive;

    public interface AddCategoryListener {
        void onAddCategoryClick(EditText addNewCategory);
    }

    public void setPositive(AddCategoryListener positiveListener){
        this.positive = positiveListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.add_category_dialog, null))
                .setTitle(R.string.new_category_label)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(positive != null){
                            Dialog dialog = (Dialog) dialogInterface;
                            EditText addNewCategory = (EditText) dialog.findViewById(R.id.category_name_dialog);
                            positive.onAddCategoryClick(addNewCategory);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddCategoryDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
