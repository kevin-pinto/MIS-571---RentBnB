package edu.wpi.rentbnb.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.wpi.rentbnb.BaseActivity;
import edu.wpi.rentbnb.R;
import edu.wpi.rentbnb.helper.Constants;
import edu.wpi.rentbnb.tools.DBOpenHelper;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
	private TextView tvRegister;
	private EditText etUsername, etPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnLogin:
			validateLogin();
			break;
		case R.id.tvRegister:
			goToRegistration();
			break;
		}
	}

	private void validateLogin() {
		String username = etUsername.getText().toString().trim();
		String password = etPassword.getText().toString().trim();
		if (username.isEmpty()) {
			etUsername.setError("Please enter a valid email id.");
			return;
		}
		if (password.isEmpty()) {
			etPassword.setError("Please enter a password to continue.");
			return;
		}
		DBOpenHelper dbHelp = new DBOpenHelper(this, Constants.DATABASE_COMPLETE_LOC, Constants.DATABASE_VERSION);
		SQLiteDatabase db = dbHelp.getWritableDatabase();
		String[] columns = new String[] { username };
		Cursor res = db.rawQuery("SELECT USER_PASS, USER_ID, USER_FIRST_NAME, USER_LAST_NAME FROM user where USER_EMAIL = ?", columns);
		if (res.getCount() == 0) {
			Toast.makeText(this, "No user found matching the given credentials.", Toast.LENGTH_LONG).show();
		} else {
			while (res.moveToNext()) {
				String pass = res.getString(0);
				Integer userId = res.getInt(1);
				String fName = res.getString(2);
				String lName = res.getString(3);
				if (!password.equalsIgnoreCase(pass)) {
					Toast.makeText(this, "Please make sure you have provided the correct password.", Toast.LENGTH_LONG).show();
				} else {
					getSharedPreferences(Constants.EXTRA_LOGIN, MODE_PRIVATE).edit().putBoolean(Constants.IS_LOGGEDIN, true).commit();
					getSharedPreferences(Constants.EXTRA_USER, MODE_PRIVATE).edit().putInt(Constants.EXTRA_USER_ID, userId).commit();
					getSharedPreferences(Constants.EXTRA_USER, MODE_PRIVATE).edit().putString(Constants.EXTRA_USER_NAME, fName + " " + lName).commit();
					goToListingsPage();
				}
			}
		}
		res.close();
	}

	private void goToRegistration() {
		Intent intent = new Intent(this, RegistrationActivity.class);
		startActivity(intent);
	}

	private void goToListingsPage() {
		Intent intent = new Intent(this, RentalListingActivity.class);
		startActivity(intent);
		finish();
	}

	private void init() {
		tvRegister = (TextView) findViewById(R.id.tvRegister);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etUsername = (EditText) findViewById(R.id.etUsername);
		tvRegister.setText(Html.fromHtml("Don't have an account? <u>Register</u>"));
		tvRegister.setOnClickListener(this);
		findViewById(R.id.btnLogin).setOnClickListener(this);
	}
}
