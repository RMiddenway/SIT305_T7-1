package com.rodnog.rogermiddenway.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showNotesButton = findViewById(R.id.showNotesButton);
        Button createNoteButton = findViewById(R.id.createNoteButton);
        View blankFragment = findViewById(R.id.blankFragment);

        showNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowNotes.class);
                startActivity(intent);
            }
        });

        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = EditNoteFragment.newInstance(new Note(), false);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.blankFragment, fragment)
                        .addToBackStack("0")
                        .commit();
            }
        });
    }
}