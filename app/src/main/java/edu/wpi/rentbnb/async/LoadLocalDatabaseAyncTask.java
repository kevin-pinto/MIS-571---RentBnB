package edu.wpi.rentbnb.async;

import android.app.Activity;
import android.os.AsyncTask;

import edu.wpi.rentbnb.activities.SplashActivity;
import edu.wpi.rentbnb.tools.Database;

/**
 * Created by Kevin on 11-10-2016.
 */
/**
 * This class is used to speed up data rtrival from the database as it does this
 * asynchronously. This includes reading and parsing .csv files from the assets
 * folder.
 */
public class LoadLocalDatabaseAyncTask extends AsyncTask<Object, Void, Boolean> {
	private Activity activity;
	boolean isSuccessFul;

	public LoadLocalDatabaseAyncTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		try {
			Database.copyDB(activity);
			isSuccessFul = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isSuccessFul;
	}

	/**
	 * Redirects to perform
	 * 
	 * @param success
	 */
	@Override
	protected void onPostExecute(Boolean success) {
		((SplashActivity) activity).onDatabaseLoadComplete(success);
	}
}
