package com.rodnog.rogermiddenway.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> noteList;
    private Context context;
    private OnRowClickListener listener;

    public NoteAdapter(List<Note> noteList, Context context, OnRowClickListener listener) {
        this.noteList = noteList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteView = LayoutInflater.from(context).inflate(R.layout.note, parent, false);
        return new NoteViewHolder(noteView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        holder.noteTextView.setText(noteList.get(position).getShortText());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder{
        public TextView noteTextView;
        public OnRowClickListener onRowClickListener;

        public NoteViewHolder(@NonNull View itemView, OnRowClickListener onRowClickListener) {
            super(itemView);
            noteTextView = itemView.findViewById(R.id.noteTextView);
            itemView.setOnClickListener(v -> onRowClickListener.onItemClick(this.getAdapterPosition()));
        }
    }
    public interface OnRowClickListener{
        void onItemClick(int position);

    }
}
