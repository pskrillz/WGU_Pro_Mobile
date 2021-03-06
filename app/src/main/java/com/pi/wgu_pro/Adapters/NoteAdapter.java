package com.pi.wgu_pro.Adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.wgu_pro.Activities.NoteDetails;
import com.pi.wgu_pro.Entities.Note;
import com.pi.wgu_pro.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private static final String TAG = "NoteAdapter";

    // private ArrayList<String> termTitles = new ArrayList<>();
    private List<Note> noteList;
    private Context ctx;

    public NoteAdapter(List<Note> noteList, Context ctx) {
        this.noteList = noteList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(noteList.get(position).getNoteTitle());

        holder.parentLayout.setOnClickListener(v -> {
            Log.d("test", "onClick: " + noteList.get(position));

            int courseId = noteList.get(position).getCourseIdFK();
            int noteId = noteList.get(position).getNoteId();
             Intent intent = new Intent(this.ctx, NoteDetails.class);
            intent.putExtra("noteId", noteId);
            intent.putExtra("courseId", courseId);
            this.ctx.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout parentLayout;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            title = itemView.findViewById(R.id.tvTitle);
        }
    }
}