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

import com.pi.wgu_pro.Activities.CourseDetails;
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

            int courseId = coursesList.get(position).getCourseIdPK();
            int termId = coursesList.get(position).getTermIdFK();
            Intent intent = new Intent(this.ctx, CourseDetails.class);
            intent.putExtra("courseId", courseId);
            intent.putExtra("termId", termId);
            this.ctx.startActivity(intent);

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