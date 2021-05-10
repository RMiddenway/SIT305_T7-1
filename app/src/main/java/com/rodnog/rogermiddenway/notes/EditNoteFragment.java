package com.rodnog.rogermiddenway.notes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditNoteFragment extends Fragment {

    EditText noteEditText;
    Button saveButton;
    Button discardButton;
    Boolean editingNote;

    DataBaseHelper db;
    public EditNoteFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EditNoteFragment newInstance(Note note, Boolean editingNote) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putString("note", note.getText());
        args.putInt("id", note.getId());
        args.putBoolean("editing", editingNote);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null){

            db = new DataBaseHelper(this.getContext());
            noteEditText = view.findViewById(R.id.noteEditText);
            noteEditText.setText(getArguments().getString("note"));
            editingNote = getArguments().getBoolean("editing");
            saveButton = view.findViewById(R.id.saveButton);
            discardButton = view.findViewById(R.id.discardButton);

            if(editingNote)
            {
                saveButton.setText("Update");
                discardButton.setText("Delete");
                int noteId = getArguments().getInt("id");
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.updateNote(noteId, noteEditText.getText().toString());
                        ((ShowNotes)getActivity()).initialise();
                        Toast.makeText(getContext(), "Note updated!", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStackImmediate();
                    }
                });
                discardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.deleteNote(noteId);
                        ((ShowNotes)getActivity()).initialise();
                        Toast.makeText(getContext(), "Note deleted!", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStackImmediate();

                    }
                });

            }
            else {
                saveButton.setText("Save");
                discardButton.setText(("Discard"));
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.insertNote(noteEditText.getText().toString());
                        Toast.makeText(getContext(), "Note saved!", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStackImmediate();
                    }
                });
                discardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getFragmentManager().popBackStackImmediate();
                    }
                });
            }
        }
    }
}