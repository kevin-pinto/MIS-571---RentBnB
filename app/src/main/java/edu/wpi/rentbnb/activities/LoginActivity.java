package edu.wpi.rentbnb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import edu.wpi.rentbnb.BaseActivity;
import edu.wpi.rentbnb.R;

public class LoginActivity extends BaseActivity {
	private TextView tvRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}

	private void init() {
		tvRegister = (TextView) findViewById(R.id.tvRegister);
		tvRegister.setText(Html.fromHtml("Don't have an account? <u>Register</u>"));
		tvRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				goToRegistration();
			}
		});
	}

	private void goToRegistration() {
		Intent intent = new Intent(this, RegistrationActivity.class);
		startActivity(intent);
	}
}
