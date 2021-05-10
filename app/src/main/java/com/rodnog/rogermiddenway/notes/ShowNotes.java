package com.rodnog.rogermiddenway.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowNotes extends AppCompatActivity implements NoteAdapter.OnRowClickListener{

    List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);
        initialise();
    }
    public void initialise(){
        DataBaseHelper db = new DataBaseHelper(this);
        noteList = db.fetchAllNotes();
        Toast.makeText(this, String.valueOf(noteList.size()) + " notes found", Toast.LENGTH_SHORT).show();

        RecyclerView showNotesRecyclerView = findViewById(R.id.showNotesRecyclerView);
        NoteAdapter noteItemAdapter = new NoteAdapter(noteList, ShowNotes.this, this);
        showNotesRecyclerView.setAdapter(noteItemAdapter);
        RecyclerView.LayoutManager showNotesLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        showNotesRecyclerView.setLayoutManager(showNotesLayoutManager);

        TextView noNotesTextView = findViewById(R.id.noNotesTextView);
        if(noteList.size() > 0){
            noNotesTextView.setVisibility(View.INVISIBLE);
        }
        else{
            noNotesTextView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onItemClick(int position) {
        DataBaseHelper db = new DataBaseHelper(this);
        Note note = db.fetchNote(noteList.get(position).getId());
        Fragment fragment = EditNoteFragment.newInstance(note, true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.blankFragment, fragment)
                .addToBackStack("0")
                .commit();


    }
}