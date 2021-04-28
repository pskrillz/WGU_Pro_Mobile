package com.pi.wgu_pro.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.wgu_pro.R;

import java.util.ArrayList;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder>{
    private static final String TAG = "TermAdapter";

    private ArrayList<String> termTitles = new ArrayList<>();
    private Context ctx;

    public TermAdapter(ArrayList<String> termTitles, Context ctx) {
        this.termTitles = termTitles;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.termTitle.setText(termTitles.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "onClick: " + termTitles.get(position));

                // TODO: trigger the form for new term info.
            }
        });

    }

    @Override
    public int getItemCount() {
        return termTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout parentLayout;
        TextView termTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            termTitle = itemView.findViewById(R.id.tvTermTitle);
        }
    }
}
