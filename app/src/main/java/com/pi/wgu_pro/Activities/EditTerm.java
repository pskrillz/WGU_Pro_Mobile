package com.pi.wgu_pro.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.pi.wgu_pro.DB.Database;
import com.pi.wgu_pro.Entities.Term;
import com.pi.wgu_pro.R;
import com.pi.wgu_pro.Utils.DatePickerFragment;
import com.pi.wgu_pro.Utils.MiscSingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class EditTerm extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText etTermTitle;
    private Spinner spinnerStatus;
    private String statusSpinnerValue;
    private Database db;
    private int termId;
    private Term selTerm;
    Intent intent;

    private Button saveTermBtn;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private TextView pickedDate;
    private boolean  termAdded = false;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // start boiler plate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        getSupportActionBar().setTitle("Edit Term");

        //vars
        db = Database.getInstance(getApplicationContext());
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        selTerm = db.termDao().getSpecTerm(termId);

        // elements

        saveTermBtn = findViewById(R.id.etSaveTermBtn);
        spinnerStatus = findViewById(R.id.etSpinnerStatus);
        etTermTitle = findViewById(R.id.etTermTitle);
        tvStartDate = findViewById(R.id.etStartDate);
        tvEndDate = findViewById(R.id.etEndDate);

        // set up the drop down
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.termStatusArray, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        initDatePicker();


        // get spinner value
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statusSpinnerValue = spinnerStatus.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // set up button to save
        saveTermBtn.setOnClickListener(v -> {
            try {
                saveTerm();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(termAdded == true) {
                Intent intent = new Intent(getApplicationContext(), TermDetails.class);
                intent.putExtra("termId", termId);
                startActivity(intent);
            }
        });


        initTermValues();
    } // end onCreate

    public void initTermValues(){
        etTermTitle.setText(selTerm.getTermName());

        statusSpinnerValue = selTerm.getTermStatus(); // sets init spinner value
        spinnerStatus.setSelection(((ArrayAdapter<String>)spinnerStatus.getAdapter()).getPosition(selTerm.getTermStatus()));

        // date conversions using utils
        String start = MiscSingleton.formatDateStr(selTerm.getTermStart());
        String end = MiscSingleton.formatDateStr(selTerm.getTermEnd());

        tvStartDate.setText(start);
        tvEndDate.setText(end);
    }



    private void saveTerm() throws ParseException {
        String title = etTermTitle.getText().toString();
        String startDate = tvStartDate.getText().toString();
        String endDate = tvEndDate.getText().toString();
        Date dateStart = dateFormatter.parse(startDate);
        Date dateEnd = dateFormatter.parse(endDate);

        // set the term object to send to db
        Term term = new Term(title, statusSpinnerValue, dateStart, dateEnd);
        term.setTermId(termId);
        db.termDao().updateTerm(term);
        Toast.makeText(this, title + " has been updated", Toast.LENGTH_SHORT).show();
        termAdded = true;
    }


    // setting up date pickers for start and end
    private void initDatePicker() {
        tvStartDate.setOnClickListener((View view) ->{
            pickedDate = findViewById(R.id.etStartDate);
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date dialog");
        });

        tvEndDate.setOnClickListener((View view) ->{
            pickedDate = findViewById(R.id.etEndDate);
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date dialog");
        });
    }



    // sets the date picker value to the view
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month = month + 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = month + "/" + dayOfMonth + "/" + year;
        pickedDate.setText(currentDateString);

    }


}