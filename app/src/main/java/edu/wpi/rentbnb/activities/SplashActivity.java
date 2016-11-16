package edu.wpi.rentbnb.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.wpi.rentbnb.BuildConfig;
import edu.wpi.rentbnb.R;

/**
 * Launcher activity for network calls and database reading. Used for background
 * parsing/reading of data to be used in the application.
 */
public class SplashActivity extends Activity {
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		init();
		displayProgress();
	}

	private void displayProgress() {
		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
		progressBar.setProgress(0);
		progressBar.setMax(5);
		CountDownTimer timer = new CountDownTimer(3000, 500) {
			@Override
			public void onTick(long millisUntilFinished) {
				count++;
				progressBar.setProgress(count);
			}

			@Override
			public void onFinish() {
				startNextActivity();
			}
		};
		timer.start();
	}

	private void init() {
		loadDatabase();
		int versionCode = BuildConfig.VERSION_CODE;
		String versionName = BuildConfig.VERSION_NAME;
		((TextView) findViewById(R.id.tvVersion)).setText("Version " + versionName);
	}

	protected void startNextActivity() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * Callback method to indicate that loading of data from database has
	 * completed.
	 *
	 * @param success
	 *            Indicates whether the loading has completed successfully or
	 *            not.
	 */
	public void onDatabaseLoadComplete(boolean success) {
		Log.d("---Loading database", "Loading completed " + (success ? "successfully" : "unsuccessfully"));
	}

	/**
	 * Loads data from the database.
	 */
	public void loadDatabase() {
		Log.d("---Loading database", "---");
		//		new LoadLocalDatabaseAyncTask(this).execute();
	}
}
