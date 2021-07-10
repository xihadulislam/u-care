package com.project.ucare.medicine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.project.ucare.R;
import com.project.ucare.auth.LoginActivity;
import com.project.ucare.auth.SignUpActivity;
import com.project.ucare.models.Schedule;
import com.xihad.androidutils.AndroidUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class AddMedicineActivity extends AppCompatActivity {


    TextView tvSat, tvSun, tvMon, tvTue, tvWed, tvThu, tvFri;
    EditText etTimePicker, etStartDate, medicineName, medicineDuration;


    LinearLayout Linear_days;
    Button saveButton;


    String sat = "not";
    String sun = "not";
    String mon = "not";
    String tue = "not";
    String wed = "not";
    String thu = "not";
    String fri = "not";

    Spinner spinnerType, spinnerUnit, spinnerIntake;
    String spTypeTx, spUnitTx;
    ProgressBar progressBar;


    static final String[] typeList = {"Syrup", "Capsule", "Tablet"};
    private static final String[] unitListSyrup = {"1 tablespoon", "2 tablespoon", "3 tablespoon", "4 tablespoon", "5 tablespoon"};
    private static final String[] unitListCapsule = {"1", "2", "3"};
    private static final String[] unitListTablet = {"1/4", "1/2", "3/2", "1", "2"};
    private static final String[] intakeList = {"1 hour before eating", "1hour after eating", "1/2 hour before eating", "1/2 hour after eating"};

    boolean satClicked = false;
    boolean sunClicked = false;
    boolean monClicked = false;
    boolean tueClicked = false;
    boolean wedClicked = false;
    boolean thuClicked = false;
    boolean friClicked = false;

    String selectedTime = "";
    String startDate = "";
    String medIntake;
    private int hour;
    private int minutes;
    ArrayAdapter<String> adapterunit;

    List<String> daysList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        AndroidUtils.Companion.init(this);

        tvSat = findViewById(R.id.sat);
        tvSun = findViewById(R.id.sun);
        tvMon = findViewById(R.id.mon);
        tvTue = findViewById(R.id.tue);
        tvWed = findViewById(R.id.wed);
        tvThu = findViewById(R.id.thu);
        tvFri = findViewById(R.id.friday);

        Linear_days = findViewById(R.id.Linear_days);
        saveButton = findViewById(R.id.save_button);

        medicineName = findViewById(R.id.et_medicine_name);
        medicineDuration = findViewById(R.id.medicineDuration);


        etTimePicker = findViewById(R.id.medicine_reminderTime);
        etStartDate = findViewById(R.id.et_start_date);


        spinnerType = findViewById(R.id.spinner_type);
        spinnerUnit = findViewById(R.id.spinner_unit);
        spinnerIntake = findViewById(R.id.spinner_intake);

        progressBar = findViewById(R.id.Pb_Addmed);

        TimePickerDialog.OnTimeSetListener mTimeSetListener =
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // TODO Auto-generated method stub
                        hour = hourOfDay;
                        minutes = minute;
                        String timeSet = "";
                        if (hour > 12) {
                            hour -= 12;
                            timeSet = "PM";
                        } else if (hour == 0) {
                            hour += 12;
                            timeSet = "AM";
                        } else if (hour == 12) {
                            timeSet = "PM";
                        } else {
                            timeSet = "AM";
                        }

                        String min = "";
                        if (minutes < 10)
                            min = "0" + minutes;
                        else
                            min = String.valueOf(minutes);

                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hour).append(':')
                                .append(min).append(" ").append(timeSet).toString();
                        etTimePicker.setText(aTime);
                        selectedTime = aTime;
                        if (selectedTime != "") {
                            Linear_days.setVisibility(View.VISIBLE);
                        } else {
                            Linear_days.setVisibility(View.GONE);
                        }
                    }
                };
        etTimePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddMedicineActivity.this, mTimeSetListener, hour, minutes, false).show();
            }
        });

        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                startDate = sdf.format(myCalendar.getTime());
                etStartDate.setText(startDate);
            }

        };

        etStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddMedicineActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, typeList);
        spinnerType.setAdapter(adapter);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                spTypeTx = parent.getItemAtPosition(position).toString();
                if (parent.getItemAtPosition(position).toString() == "Syrup") {

                    adapterunit = new ArrayAdapter<String>(AddMedicineActivity.this,
                            android.R.layout.simple_spinner_item, unitListSyrup);
                    spinnerUnit.setAdapter(adapterunit);
                } else if (parent.getItemAtPosition(position).toString() == "Tablet") {
                    adapterunit = new ArrayAdapter<String>(AddMedicineActivity.this,
                            android.R.layout.simple_spinner_item, unitListTablet);
                    spinnerUnit.setAdapter(adapterunit);
                } else {
                    adapterunit = new ArrayAdapter<String>(AddMedicineActivity.this,
                            android.R.layout.simple_spinner_item, unitListCapsule);
                    spinnerUnit.setAdapter(adapterunit);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> adapterIntake = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, intakeList);
        spinnerIntake.setAdapter(adapterIntake);

        spinnerIntake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                medIntake = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spinnerUnit.setAdapter(adapterunit);
        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                spUnitTx = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        tvSat.setOnClickListener(v -> {
            satClicked = !satClicked;
            if (satClicked) {
                sat = "sat";
                tvSat.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg_selected));
            } else {
                sat = "not";
                tvSat.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg));
            }

        });
        tvSun.setOnClickListener(v -> {
            sunClicked = !sunClicked;
            if (sunClicked) {
                sun = "sun";
                tvSun.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg_selected));
            } else {
                sun = "not";
                tvSun.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg));
            }

        });

        tvMon.setOnClickListener(v -> {
            monClicked = !monClicked;
            if (monClicked) {
                mon = "mon";
                tvMon.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg_selected));
            } else {
                mon = "not";
                tvMon.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg));
            }

        });


        tvTue.setOnClickListener(v -> {
            tueClicked = !tueClicked;
            if (tueClicked) {
                tue = "tue";
                tvTue.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg_selected));
            } else {
                tue = "not";
                tvTue.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg));
            }

        });


        tvWed.setOnClickListener(v -> {
            wedClicked = !wedClicked;
            if (wedClicked) {
                wed = "wed";
                tvWed.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg_selected));
            } else {
                wed = "not";
                tvWed.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg));
            }

        });


        tvThu.setOnClickListener(v -> {
            thuClicked = !thuClicked;
            if (thuClicked) {
                thu = "thu";
                tvThu.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg_selected));
            } else {
                thu = "not";
                tvThu.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg));
            }

        });


        tvFri.setOnClickListener(v -> {
            friClicked = !friClicked;
            if (friClicked) {
                fri = "fri";
                tvFri.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg_selected));
            } else {
                fri = "not";
                tvFri.setBackground(ContextCompat.getDrawable(AddMedicineActivity.this, R.drawable.bt_days_bg));
            }

        });


        saveButton.setOnClickListener(v -> {

            String medName = medicineName.getText().toString().trim();
            String medDuration = medicineDuration.getText().toString().trim();


            if (medName.isEmpty()) {
                medicineName.setError("This field can not be blank");
                return;

            }
            if (spTypeTx.isEmpty()) {
                Toast.makeText(AddMedicineActivity.this, "Medicine type time can not be null ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (spUnitTx.isEmpty()) {
                Toast.makeText(AddMedicineActivity.this, "Medicine unit can not be null ", Toast.LENGTH_SHORT).show();
                return;
            }

            if (startDate == "") {
                Toast.makeText(AddMedicineActivity.this, "Start date can not be null ", Toast.LENGTH_SHORT).show();
                return;
            }

            if (medDuration.isEmpty()) {
                medicineDuration.setError("This field can not be blank");
                return;
            }
            if (medIntake.isEmpty()) {
                Toast.makeText(AddMedicineActivity.this, "Intake time can not be null ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedTime == "") {
                Toast.makeText(AddMedicineActivity.this, "reminder time can not be null ", Toast.LENGTH_SHORT).show();
                return;
            }


            if (sat.equals("not") && sun.equals("not") && mon.equals("not") && tue.equals("not") && wed.equals("not") && thu.equals("not") && fri.equals("not")) {
                Toast.makeText(AddMedicineActivity.this, "You must select a day", Toast.LENGTH_SHORT).show();
                return;
            }


            saveButton.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            if (!sat.equals("not")) {
                daysList.add(sat);
            }

            if (!sun.equals("not")) {
                daysList.add(sun);
            }

            if (!mon.equals("not")) {
                daysList.add(mon);
            }

            if (!tue.equals("not")) {
                daysList.add(tue);
            }

            if (!wed.equals("not")) {
                daysList.add(wed);
            }

            if (!thu.equals("not")) {
                daysList.add(thu);
            }

            if (!fri.equals("not")) {
                daysList.add(fri);
            }


            Log.d(TAG, "onCreate:all " + "medName: " + medName + "medType: " + spTypeTx + "meUnit: " + spUnitTx + "start date: " + startDate + "duration: " + medDuration + "medIntake: " + medIntake + "reminderTime: " + selectedTime + "selectedDate: " + daysList);

            String id = String.valueOf(System.currentTimeMillis());

            String userId = "";

            if (AndroidUtils.sharePrefSettings.getStringValue("pro").equalsIgnoreCase("")) {
                userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            } else {
                userId = AndroidUtils.sharePrefSettings.getStringValue("pro");
            }

            Schedule schedule = new Schedule(id, userId, medName, spTypeTx, spUnitTx, startDate, medDuration, medIntake, selectedTime, daysList, true, System.currentTimeMillis());

            FirebaseDatabase.getInstance().getReference().child("Schedule").child(userId).child(id).setValue(schedule).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    Toast.makeText(AddMedicineActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                    saveButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    finish();

                } else {
                    saveButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddMedicineActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });


    }
}