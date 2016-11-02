package com.iste776.jpavelw.addressbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AddressBook extends AppCompatActivity {

    public static final String ROW_ID = "row_id";
    private ListView contactListView;
    private CursorAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        this.contactListView = (ListView) findViewById(R.id.list_view);
        //this.contactListView.setOnClickListener(viewContactListener);
        TextView emptyText = (TextView) View.inflate(this, R.layout.contact_list_empty_item, null);
        emptyText.setVisibility(View.GONE);

        ((ViewGroup) contactListView.getParent()).addView(emptyText);
        contactListView.setEmptyView(emptyText);

        String[] from = new String[] { "name" };
        int[] to = new int[] { R.id.contact_text_view };
        contactAdapter = new SimpleCursorAdapter(AddressBook.this, R.layout.contact_list_item, null, from, to, 0);

        contactListView.setAdapter(contactAdapter);
    }
}
