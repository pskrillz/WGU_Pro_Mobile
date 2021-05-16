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
import com.pi.wgu_pro.Entities.Assessment;
import com.pi.wgu_pro.R;
import com.pi.wgu_pro.Utils.DatePickerFragment;
import com.pi.wgu_pro.Utils.ReminderBroadcast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddAssessment extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    //vars
    Database db;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
    boolean assessmentAdded = false;
    Intent intent;
    int courseId;

    //views
    EditText aaTitle;
    Spinner aaTypeSpinner;
    TextView aaStartTv;
    TextView aaEndTv;
    Switch aaAlertSwitch;
    Button aaSaveButton;

    // dynamic views
    TextView pickedDate;
    String statusSpinnerValue;
    String typeSpinnerValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        getSupportActionBar().setTitle("Add Assessment");

        //vars
        db = Database.getInstance(getApplicationContext());
        intent = getIntent();
        courseId = intent.getIntExtra("courseId", -1);

        // view elements
        aaTitle = findViewById(R.id.aaTitle);
        aaTypeSpinner = findViewById(R.id.aaTypeSpinner);
        aaStartTv = findViewById(R.id.aaStartTv);
        aaEndTv = findViewById(R.id.aaEndTv);
        aaAlertSwitch = findViewById(R.id.aaAlertSwitch);
        aaSaveButton = findViewById(R.id.aaSaveBtn);

        //init UI
        initViews();


    }

    private void initViews() {
        initTypeDropdown();
        initDatePicker();
        initSaveButton();
    }

    private void saveAssessment() throws ParseException {
        String title = aaTitle.getText().toString();
        String startDate = aaStartTv.getText().toString();
        String endDate = aaEndTv.getText().toString();
        Date dateStart = dateFormatter.parse(startDate);
        Date dateEnd = dateFormatter.parse(endDate);

        long sDate = dateStart.getTime();
        long eDate = dateEnd.getTime();
        boolean alert = aaAlertSwitch.isChecked();

        // set alerts
        if(alert){
            ReminderBroadcast.setAlert(this, "assessmentAlerts", 20, sDate,
                    "Prepare for Assessment", "Assessment: " + title + " is coming soon");
            ReminderBroadcast.setAlert(this, "assessmentAlerts", 20, eDate,
                    "Assessment Today", "Assessment: " + title + " is today, " +
                            "\n did you pass?");

        }


        // set the term object to send to db
        Assessment assessment = new Assessment(courseId, typeSpinnerValue, title, dateStart,
                dateEnd, alert);


        db.assessmentDao().insertAssessment(assessment);
        Toast.makeText(this, title + " has been added", Toast.LENGTH_SHORT).show();
        assessmentAdded = true;
    }

    private void initSaveButton() {
        aaSaveButton.setOnClickListener(v -> {
            try {
                saveAssessment();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(assessmentAdded == true) {
                Intent intent = new Intent(getApplicationContext(), CourseDetails.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });
    }


    private void initTypeDropdown() {
        // set up drop down
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessmentTypeArray, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aaTypeSpinner.setAdapter(adapter);

        // get spinner value
        aaTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeSpinnerValue = aaTypeSpinner.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month = month + 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = month + "/" + dayOfMonth + "/" + year;
        pickedDate.setText(currentDateString);

    }

    // setting up date pickers for start and end
    private void initDatePicker() {
        aaStartTv.setOnClickListener((View view) ->{
            pickedDate = findViewById(R.id.aaStartTv);
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date dialog");
        });

        aaEndTv.setOnClickListener((View view) ->{
            pickedDate = findViewById(R.id.aaEndTv);
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date dialog");
        });
    }




}