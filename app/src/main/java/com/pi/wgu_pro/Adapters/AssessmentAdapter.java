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

import com.pi.wgu_pro.Activities.AssessmentDetails;
import com.pi.wgu_pro.Entities.Assessment;
import com.pi.wgu_pro.R;

import java.util.List;

// TODO Finish assessment adapter and test;
public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder>{
    private static final String TAG = "AssessmentAdapter";

    private List<Assessment> assessmentList;
    private Context ctx;

    public AssessmentAdapter(List<Assessment> assessmentList, Context ctx) {
        this.assessmentList = assessmentList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AssessmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        AssessmentAdapter.ViewHolder viewHolder = new AssessmentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.ViewHolder holder, int position) {
        holder.title.setText(assessmentList.get(position).getAssessmentTitle());

        holder.parentLayout.setOnClickListener(v -> {
            Log.d("test", "onClick: " + assessmentList.get(position));

            // TODO: Create Detailed Note View
            int assessmentId = assessmentList.get(position).getAssessmentId();
             Intent intent = new Intent(this.ctx, AssessmentDetails.class);
             intent.putExtra("assessmentId", assessmentId);
             this.ctx.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return assessmentList.size();
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
