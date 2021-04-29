package com.pi.wgu_pro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pi.wgu_pro.Adapters.TermAdapter;
import com.pi.wgu_pro.R;

import java.util.ArrayList;

public class TermList extends AppCompatActivity {
    private static final String TAG = "TermList";

    // view elements
    private FloatingActionButton fabAddTerm;
    private RecyclerView rvTerms;

    // vars
    private ArrayList<String> termTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("On Create for term's list Started");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setTitle("Terms List");

        // sample data
        termTitles.add("asdfgsaf");
        termTitles.add("assdaf 2");

        fabAddTerm = findViewById(R.id.fabAddTerm);

       initRecyclerView();

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
        TermAdapter adapter = new TermAdapter(termTitles, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }


}