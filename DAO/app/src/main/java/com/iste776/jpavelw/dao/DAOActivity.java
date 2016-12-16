package com.iste776.jpavelw.dao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.greendao.query.Query;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class DAOActivity extends AppCompatActivity {

    private TextView textView;
    private StringBuilder text;

    private NoteDao noteDao;
    private Query<Note> notesQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao);

        Button button = (Button) findViewById(R.id.button);
        this.textView = (TextView) findViewById(R.id.textView);
        this.text = new StringBuilder();

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        this.noteDao = daoSession.getNoteDao();

        this.notesQuery = noteDao.queryBuilder().orderAsc(NoteDao.Properties.Text).build();
        this.updateNotes();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DateFormat DF= DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
                String comment = "Added on " + DF.format(new Date());
                Note note = new Note(null, "Something ", comment, new Date(), NoteType.TEXT);
                noteDao.insert(note);
                Log.i("DaoExample", "Inserted new note, ID: " + note.getId());
                updateNotes();
            }
        });
    }

    private void updateNotes(){
        List<Note> notes = this.notesQuery.list();
        for(Note note : notes){
            this.text.append(note.getComment() + "-" + note.getDate() + "-" + note.getId() + "-" + note.getType());
        }
        this.textView.setText(this.text.toString());
    }
}
