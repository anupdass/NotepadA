package com.example.notepada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notepada.db.DataBaseNote;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView te;
    private EditText ed;
    private long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        te = findViewById(R.id.dateTV);
        ed = findViewById(R.id.noteET);

        Intent intent = getIntent();
        Note note = (Note) intent.getSerializableExtra("note");
        if(note != null){
            te.setText(note.getDate());
            ed.setText(note.getNoteText());
            id = note.getId();
        }
        te.setText(new SimpleDateFormat("EEEE, dd/MMMM/YYYY hh:mm a", Locale.getDefault()).format(new Date()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveNote:
                if(id == 0) {
                    saveNoteData();
                }
                else if (id >0) {
                    updateData();
                }
                break;
            case R.id.cancel:
                startActivity(new Intent(this,SecondActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateData() {
        String note = ed.getText().toString();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MMMM/YYYY hh:mm a");
        String dates = sdf.format(date);
        Note obNote = new Note(id,note, dates);

        final long updateRow= DataBaseNote.getInstance(this).getNoteDao().updateNote(obNote);

        if (updateRow > 0) {
            Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,SecondActivity.class));
        }
    }

    private void saveNoteData() {

        //convert Date to String
        String note = ed.getText().toString();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MMMM/YYYY hh:mm a");
        String dates = sdf.format(date);
        Note obNote = new Note(note, dates);

        final long insertRowId = DataBaseNote.getInstance(this).getNoteDao().insertNote(obNote);

        if (insertRowId > 0) {
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,SecondActivity.class));
        }
    }

    private void resetView() {
        ed.setText("");
        ed.requestFocus();
    }

}