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
import com.pi.wgu_pro.Utils.MiscSingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditCourse extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //vars
    Database db;
    SimpleDateFormat dateFormatter;
    boolean courseAdded = false;
    Course selCourse;
    Intent intent;
    int termId;
    int courseId;


    //views
    EditText Title;
    Spinner StatusSpinner;
    TextView StartTv;
    TextView EndTv;
    Switch AlertSwitch;
    Button SaveButton;

    // dynamic views
    TextView pickedDate;
    String statusSpinnerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        getSupportActionBar().setTitle("Add Course");

        //vars
        db = Database.getInstance(getApplicationContext());
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        selCourse = db.courseDao().getSpecCourse(courseId);

        Title = findViewById(R.id.ecTitle);
        StatusSpinner = findViewById(R.id.ecTypeSpinner);
        StartTv = findViewById(R.id.ecStartTv);
        EndTv = findViewById(R.id.ecEndTv);
        AlertSwitch = findViewById(R.id.ecAlertSwitch);
        SaveButton = findViewById(R.id.ecSaveBtn);

        // set up drop down
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.courseStatusArray, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        StatusSpinner.setAdapter(adapter);

        // get spinner value
        StatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statusSpinnerValue = StatusSpinner.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        SaveButton.setOnClickListener(v -> {
            try {
                saveCourse();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(courseAdded == true) {
                Intent intent = new Intent(getApplicationContext(), CourseDetails.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("termId", termId);
                startActivity(intent);
            }
        });


        initDatePicker();
        initCourseValues();


    } //end onCreate


    private void initCourseValues(){
        Title.setText(selCourse.getCourseName());

        // setting up spinner
        statusSpinnerValue = selCourse.getCourseStatus();
        StatusSpinner.setSelection(((ArrayAdapter<String>)StatusSpinner.getAdapter()).getPosition(selCourse.getCourseStatus()));

        // dates
        String start = MiscSingleton.formatDateStr(selCourse.getCourseStart());
        String end = MiscSingleton.formatDateStr(selCourse.getCourseEnd());
        StartTv.setText(start);
        EndTv.setText(end);

        // check if alerts are on.
        if(selCourse.getCourseAlert()){
            AlertSwitch.toggle();
        }

    }


    private void saveCourse() throws ParseException {
        String title = Title.getText().toString();
        String startDate = StartTv.getText().toString();
        String endDate = EndTv.getText().toString();
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        Date dateStart = dateFormatter.parse(startDate);
        Date dateEnd = dateFormatter.parse(endDate);
        boolean alert = AlertSwitch.isChecked();

        // set the term object to send to db
        Course course = new Course();
        course.setCourseIdPK(courseId);
        course.setTermIdFK(termId);
        course.setCourseName(title);
        course.setCourseStatus(statusSpinnerValue);
        course.setCourseStart(dateStart);
        course.setCourseEnd(dateEnd);
        course.setCourseAlert(alert);
        db.courseDao().updateCourse(course);
        Toast.makeText(this, title + " has been updated", Toast.LENGTH_SHORT).show();
        courseAdded = true;
    }




    // setting up date pickers for start and end
    private void initDatePicker() {
        StartTv.setOnClickListener((View view) ->{
            pickedDate = findViewById(R.id.ecStartTv);
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date dialog");
        });

        EndTv.setOnClickListener((View view) ->{
            pickedDate = findViewById(R.id.ecEndTv);
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