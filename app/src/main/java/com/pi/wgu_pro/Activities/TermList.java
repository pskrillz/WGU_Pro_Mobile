package com.pi.wgu_pro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pi.wgu_pro.Adapters.TermAdapter;
import com.pi.wgu_pro.DB.Database;
import com.pi.wgu_pro.Entities.Term;
import com.pi.wgu_pro.R;

import java.util.List;

public class TermList extends AppCompatActivity {
    private static final String TAG = "TermList";

    // view elements
    private FloatingActionButton fabAddTerm;
    private RecyclerView rvTerms;

    // vars
//    private ArrayList<String> termTitles = new ArrayList<>();
    private List<Term> termList;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("On Create for term's list Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setTitle("Terms List");
        db = Database.getInstance(getApplicationContext());

//        // sample data
//        termTitles.add("asdfgsaf");
//        termTitles.add("assdaf 2");

        // different list attempt
        termList = db.termDao().getAllTerms();
        System.out.println(termList);


       initRecyclerView();

       fabAddTerm = findViewById(R.id.tdAddCourse);
       fabAddTerm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(v.getContext(), AddTerm.class);
               startActivity(intent);
           }
       });

    }

    private void initRecyclerView(){
        RecyclerView rv = findViewById(R.id.rvTerms);
        TermAdapter adapter = new TermAdapter(termList, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }


}