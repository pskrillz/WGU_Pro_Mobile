package com.pi.wgu_pro.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.pi.wgu_pro.DB.Database;
import com.pi.wgu_pro.Entities.Assessment;
import com.pi.wgu_pro.R;
import com.pi.wgu_pro.Utils.DatePickerFragment;
import com.pi.wgu_pro.Utils.MiscSingleton;
import com.pi.wgu_pro.Utils.ReminderBroadcast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AssessmentDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    //vars
    Database db;
    Intent intent;
    int assessmentId;
    int courseId;
    Assessment selAssessment;
    String typeSpinnerValue;
    boolean assessmentUpdated = false;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");



    //views
    EditText title;
    Spinner type;
    TextView start;
    TextView end;
    Switch alert;
    Button save;
    TextView pickedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        //vars
        db = Database.getInstance(getApplicationContext());
        intent = getIntent();
        assessmentId = intent.getIntExtra("assessmentId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        selAssessment = db.assessmentDao().getSpecAssessment(assessmentId);



        //views
        title = findViewById(R.id.adTitle);
        type = findViewById(R.id.adTypeSpinner);
        start = findViewById(R.id.adStartTv);
        end = findViewById(R.id.adEndTv);
        alert = findViewById(R.id.adAlertSwitch);
        save = findViewById(R.id.adSaveBtn);


        //ui
        // set up drop down
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessmentTypeArray, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        // get spinner value
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeSpinnerValue = type.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        save.setOnClickListener(v -> {
            try {
                saveAssessment();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(assessmentUpdated == true) {
                Toast.makeText(this, selAssessment.getAssessmentTitle() + " saved." , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CourseDetails.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });





        initDatePicker();
        initAssessmentValues();


    }// end on create

    private void saveAssessment() throws ParseException {
        selAssessment.setAssessmentTitle(title.getText().toString());
        selAssessment.setAssessmentType(typeSpinnerValue);

        Date startD = dateFormatter.parse(start.getText().toString());
        selAssessment.setAssessmentStart(startD);

        Date endD = dateFormatter.parse(end.getText().toString());
        selAssessment.setAssessmentEnd(endD);

        long sDate = startD.getTime();
        long eDate = endD.getTime();
        selAssessment.setAssessmentAlert(alert.isChecked());

        if(alert.isChecked()){
            ReminderBroadcast.setAlert(this, "assessmentAlerts", 20, sDate,
                    "Prepare for Assessment", "Assessment: " + title + " is coming soon");
            ReminderBroadcast.setAlert(this, "assessmentAlerts", 20, eDate,
                    "Assessment Today", "Assessment: " + title + " is today, " +
                            "\n did you pass?");
        }

        db.assessmentDao().updateAssessment(selAssessment);
        assessmentUpdated = true;
    }

    private void initAssessmentValues() {
        title.setText(selAssessment.getAssessmentTitle());

        // spinner
        typeSpinnerValue = selAssessment.getAssessmentType();
        type.setSelection(((ArrayAdapter<String>)type.getAdapter()).getPosition(typeSpinnerValue));

        //dates
        String startStr = MiscSingleton.formatDateStr(selAssessment.getAssessmentStart());
        String endStr = MiscSingleton.formatDateStr(selAssessment.getAssessmentEnd());
        start.setText(startStr);
        end.setText(endStr);

        // alerts
        if(selAssessment.getAssessmentAlert()){
            alert.toggle();
        }



    }



    // setting up date pickers for start and end
    private void initDatePicker() {
        start.setOnClickListener((View view) ->{
            pickedDate = findViewById(R.id.adStartTv);
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date dialog");
        });

        end.setOnClickListener((View view) ->{
            pickedDate = findViewById(R.id.adEndTv);
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



    // menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.adDeleteBtn:
                db.assessmentDao().deleteAssessment(selAssessment);
                Toast.makeText(this, "Deleted " + selAssessment.getAssessmentTitle() , Toast.LENGTH_SHORT).show();
                openCourseDetail();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void openCourseDetail(){
        Intent newIntent = new Intent(getApplicationContext(), CourseDetails.class);
        newIntent.putExtra("courseId", courseId);
        startActivity(newIntent);
    }


} // end class