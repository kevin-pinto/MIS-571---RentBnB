package edu.wpi.rentbnb.activities;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import edu.wpi.rentbnb.BaseActivity;
import edu.wpi.rentbnb.R;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {
	private TextView tvDob;
	private Calendar calendar = Calendar.getInstance();
	private DatePickerDialog.OnDateSetListener pickerListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		init();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tvDob:
			new DatePickerDialog(this, pickerListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
			break;
		}
	}

	private void init() {
		tvDob = (TextView) findViewById(R.id.tvDob);
		tvDob.setOnClickListener(this);
		Spinner spinner = (Spinner) findViewById(R.id.spGender);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.gender_list)));
		spinner.setAdapter(adapter);
		pickerListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				setDateOfBirth();
			}
		};
	}

	private void setDateOfBirth() {
		String myFormat = "MM/dd/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		tvDob.setText(sdf.format(calendar.getTime()));
	}
}
