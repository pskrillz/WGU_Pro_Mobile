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

import com.pi.wgu_pro.Entities.Course;
import com.pi.wgu_pro.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    private static final String TAG = "CourseAdapter";

    // private ArrayList<String> termTitles = new ArrayList<>();
    private List<Course> coursesList;
    private Context ctx;

    public CourseAdapter(List<Course> coursesList, Context ctx) {
        this.coursesList = coursesList;
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
        holder.title.setText(coursesList.get(position).getCourseName());

        holder.parentLayout.setOnClickListener(v -> {
            Log.d("test", "onClick: " + coursesList.get(position));

            // TODO: trigger the form for new term info.
//            int termId = termList.get(position).getTermId();
//            String termTitle = termList.get(position).getTermName();
//            String termStatus = termList.get(position).getTermStatus();
//            Date start = termList.get(position).getTermStart();
//            Date end = termList.get(position).getTermEnd();
//            Term term = new Term(termId, termTitle, termStatus, start, end);
//
//            Intent intent = new Intent(this.ctx, TermDetails.class);
//            intent.putExtra("termId", term.getTermId());
//            intent.putExtra("termTitle", term.getTermName());
//            intent.putExtra("termStatus", term.getTermStatus());
//            intent.putExtra("start", term.getTermStart());
//            intent.putExtra("end", term.getTermEnd());
//            this.ctx.startActivity(intent);


        });

    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout parentLayout;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            // reusing list for courses too.
            title = itemView.findViewById(R.id.tvTitle);
        }
    }
}