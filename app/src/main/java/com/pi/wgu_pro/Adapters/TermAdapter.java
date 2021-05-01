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

import com.pi.wgu_pro.Activities.TermDetails;
import com.pi.wgu_pro.Entities.Term;
import com.pi.wgu_pro.R;

import java.util.Date;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder>{
    private static final String TAG = "TermAdapter";

   // private ArrayList<String> termTitles = new ArrayList<>();
    private List<Term> termList;
    private Context ctx;

    public TermAdapter(List<Term> termList, Context ctx) {
        this.termList = termList;
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
        holder.termTitle.setText(termList.get(position).getTermName());

        holder.parentLayout.setOnClickListener(v -> {
            Log.d("test", "onClick: " + termList.get(position));

            // TODO: trigger the form for new term info.
            int termId = termList.get(position).getTermId();
            String termTitle = termList.get(position).getTermName();
            String termStatus = termList.get(position).getTermStatus();
            Date start = termList.get(position).getTermStart();
            Date end = termList.get(position).getTermEnd();
            Term term = new Term(termId, termTitle, termStatus, start, end);

            Intent intent = new Intent(this.ctx, TermDetails.class);
            intent.putExtra("termId", term.getTermId());
            intent.putExtra("termTitle", term.getTermName());
            intent.putExtra("termStatus", term.getTermStatus());
            intent.putExtra("start", term.getTermStart());
            intent.putExtra("end", term.getTermEnd());
            this.ctx.startActivity(intent);


        });

    }

    @Override
    public int getItemCount() {
        return termList.size();
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