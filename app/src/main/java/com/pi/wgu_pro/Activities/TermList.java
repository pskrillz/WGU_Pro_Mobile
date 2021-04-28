package com.pi.wgu_pro.Activities;

import android.os.Bundle;

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

        termTitles.add("asdfgsaf");
        termTitles.add("assdaf 2");

       initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView rv = findViewById(R.id.rvTerms);
        TermAdapter adapter = new TermAdapter(termTitles, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }


}