package edu.wpi.rentbnb.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.wpi.rentbnb.BaseActivity;
import edu.wpi.rentbnb.R;
import edu.wpi.rentbnb.datamodels.Gender;
import edu.wpi.rentbnb.datamodels.User;
import edu.wpi.rentbnb.datamodels.UserType;
import edu.wpi.rentbnb.tools.Database;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {
	private TextView tvDob;
	private EditText etPass, etFirstName, etLastName, etPhone, etEmail;
	private Button btnRegister;
	private RadioGroup userType;
	private Spinner spGender;
	private Calendar calendar = Calendar.getInstance();
	private DatePickerDialog.OnDateSetListener pickerListener;
	private boolean isVisible = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		init();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnRegister:
			registerUser();
			break;
		case R.id.tvDob:
			new DatePickerDialog(this, pickerListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
			break;
		case R.id.etPass:
			if (isVisible) {
				etPass.setTransformationMethod(new PasswordTransformationMethod());
				etPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_invisible, 0);
				isVisible = false;
			} else {
				etPass.setTransformationMethod(null);
				etPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_visible, 0);
				isVisible = true;
			}
			break;
		}
	}

	private void registerUser() {
		if (etFirstName.getText().toString().trim().isEmpty()) {
			etFirstName.setError("Please insert first name.");
			return;
		}
		if (etLastName.getText().toString().trim().isEmpty()) {
			etLastName.setError("Please insert last name.");
			return;
		}
		if (tvDob.getText().toString().trim().isEmpty()) {
			Toast.makeText(this, "Please select date of birth.", Toast.LENGTH_SHORT).show();
			return;
		}
		if (etPhone.getText().toString().trim().isEmpty()) {
			etPhone.setError("Please provide a phone number.");
			return;
		}
		if (etEmail.getText().toString().trim().isEmpty()) {
			etEmail.setError("Please provide an email.");
			return;
		}
		if (etPass.getText().toString().trim().isEmpty()) {
			etPass.setError("Please provide a password.");
			return;
		}
		String genderStr = spGender.getSelectedItem().toString().substring(0, 1);
		Gender gender = null;
		switch (genderStr) {
		case "M":
			gender = Gender.MALE;
			break;
		case "F":
			gender = Gender.FEMALE;
			break;
		case "O":
			gender = Gender.OTHER;
			break;
		}
		redirectToLogin();
		User user = new User();
		user.setType((userType.getCheckedRadioButtonId() == R.id.rbtnTenant) ? UserType.TENANT : UserType.LANDLORD);
		user.setGender(gender);
		user.setFirstName(etFirstName.getText().toString().trim());
		user.setLastName(etLastName.getText().toString().trim());
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			user.setDob(formatter.parse(tvDob.getText().toString().trim()));
		} catch (ParseException ex) {
			Log.e("ParseException", ex.getMessage());
		}
		user.setEmail(etEmail.getText().toString().trim());
		user.setPhoneNumber(etPhone.getText().toString());
		user.setCreationDate(new Date());
		long id = Database.getInstance().insertUserData(user, etPass.getText().toString().trim());
		showNotification(user);
		//		Toast.makeText(this, "User id: " + String.valueOf(id), Toast.LENGTH_SHORT).show();
	}

	private void showNotification(User user) {
		String subTitle = "Hey " + user.getFirstName() + " welcome to RenBnB. We're pleased to have you onboard and we look forward to simplifying your search with apartments.";
		Intent notificationIntent = new Intent(this, SplashActivity.class);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.app_logo).setContentTitle("Welcome Onboard").setContentText(subTitle);
		mBuilder.setContentIntent(pendingNotificationIntent).setAutoCancel(true);
		mBuilder.setLights(Color.BLUE, 3000, 3000);
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		mBuilder.setSound(alarmSound);
		Notification notification = mBuilder.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(1, notification);
	}

	private void redirectToLogin() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	private void init() {
		userType = (RadioGroup) findViewById(R.id.userType);
		tvDob = (TextView) findViewById(R.id.tvDob);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		etPass = (EditText) findViewById(R.id.etPass);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etFirstName = (EditText) findViewById(R.id.etFirstName);
		etLastName = (EditText) findViewById(R.id.etLastName);
		etPhone = (EditText) findViewById(R.id.etPhone);
		etPass.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		tvDob.setOnClickListener(this);
		spGender = (Spinner) findViewById(R.id.spGender);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, Arrays.asList(getResources().getStringArray(R.array.gender_list)));
		spGender.setAdapter(adapter);
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
