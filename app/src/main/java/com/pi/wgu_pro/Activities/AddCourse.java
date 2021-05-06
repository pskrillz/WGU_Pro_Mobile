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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.pi.wgu_pro.DB.Database;
import com.pi.wgu_pro.Entities.Course;
import com.pi.wgu_pro.R;
import com.pi.wgu_pro.Utils.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCourse extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //vars
    Database db;
    SimpleDateFormat dateFormatter;
    boolean courseAdded = false;
    Intent intent;
    int termId;


    //views
    EditText acTitle;
    Spinner acStatusSpinner;
    TextView acStartTv;
    TextView acEndTv;
    Switch acAlertSwitch;
    Button acSaveButton;

    // dynamic views
    TextView pickedDate;
    String statusSpinnerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setTitle("Add Course");

        //vars
        db = Database.getInstance(getApplicationContext());
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);

        acTitle = findViewById(R.id.acTitle);
        acStatusSpinner = findViewById(R.id.aaTypeSpinner);
        acStartTv = findViewById(R.id.aaStartTv);
        acEndTv = findViewById(R.id.aaEndTv);
        acAlertSwitch = findViewById(R.id.aaAlertSwitch);
        acSaveButton = findViewById(R.id.aaSaveBtn);

        // set up drop down
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.courseStatusArray, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acStatusSpinner.setAdapter(adapter);

        // get spinner value
        acStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statusSpinnerValue = acStatusSpinner.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        acSaveButton.setOnClickListener(v -> {
            try {
                saveCourse();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(courseAdded == true) {
                Intent intent = new Intent(getApplicationContext(), TermDetails.class);
                intent.putExtra("termId", termId);
                startActivity(intent);
            }
        });


        initDatePicker();


    } //end onCreate

    private void saveCourse() throws ParseException {
        String title = acTitle.getText().toString();
        String startDate = acStartTv.getText().toString();
        String endDate = acEndTv.getText().toString();
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        Date dateStart = dateFormatter.parse(startDate);
        Date dateEnd = dateFormatter.parse(endDate);
        boolean alert = acAlertSwitch.isChecked();

        // set the term object to send to db
        Course course = new Course();
        course.setTermIdFK(termId);
        course.setCourseName(title);
        course.setCourseStatus(statusSpinnerValue);
        course.setCourseStart(dateStart);
        course.setCourseEnd(dateEnd);
        course.setCourseAlert(alert);
        db.courseDao().insertCourse(course);
        Toast.makeText(this, title + " has been added", Toast.LENGTH_SHORT).show();
        courseAdded = true;
    }




    // setting up date pickers for start and end
    private void initDatePicker() {
        acStartTv.setOnClickListener((View view) ->{
            pickedDate = findViewById(R.id.aaStartTv);
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date dialog");
        });

        acEndTv.setOnClickListener((View view) ->{
            pickedDate = findViewById(R.id.aaEndTv);
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
} // end class