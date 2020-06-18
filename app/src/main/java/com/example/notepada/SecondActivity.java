package com.example.notepada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.notepada.db.DataBaseNote;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private Adapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        searchView = findViewById(R.id.search);

        recyclerView = findViewById(R.id.recycler);
        final List<Note> notes = DataBaseNote.getInstance(this).getNoteDao().getAllNote();
        adapter = new Adapter(this,R.layout.item_view,notes);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);


        //search item form a arrylist......

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s = s.toLowerCase();
                ArrayList<Note> newList = new ArrayList<>();
                for(Note information :notes) {
                    String name = information.getNoteText();
                    if (name.contains(s))
                        newList.add(information);
                }
                    adapter.getFilter(newList);
                    return true;
            }
        });

    }

    public void addNote(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}