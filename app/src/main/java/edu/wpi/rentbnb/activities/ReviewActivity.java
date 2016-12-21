package edu.wpi.rentbnb.activities;

import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import edu.wpi.rentbnb.BaseActivity;
import edu.wpi.rentbnb.R;
import edu.wpi.rentbnb.adapter.ReviewsAdapter;
import edu.wpi.rentbnb.datamodels.Review;
import edu.wpi.rentbnb.helper.ApplicationHelper;
import edu.wpi.rentbnb.helper.Constants;
import edu.wpi.rentbnb.tools.Database;

public class ReviewActivity extends BaseActivity {
    private ListView lvRating;
    private ReviewsAdapter adapter;
    private ArrayList<Review> reviewsList;
    private Integer rsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        reviewsList = (ArrayList<Review>) getIntent().getSerializableExtra(Constants.EXTRA_RENTALSPACE_REVIEWS);
        rsId = getIntent().getIntExtra(Constants.EXTRA_RENTALSPACE_ID, -1);
        init();
    }

    private void populateReviewsList(boolean loadDb) {
        if (loadDb) {
            reviewsList.clear();
            reviewsList = Database.getInstance().fetchReviewsForRentalSpace(rsId);
        }
        adapter = new ReviewsAdapter(this, reviewsList);
        lvRating.setAdapter(adapter);
    }

    private void showWriteNewReviewAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.alert_write_review, null);
        final EditText etReview = (EditText) dialogView.findViewById(R.id.etReview);
        final RatingBar ratingbar = (RatingBar) dialogView.findViewById(R.id.rating);
        final RadioGroup rgRec = (RadioGroup) dialogView.findViewById(R.id.rgRec);
        etReview.setHint("Type your review here");
        alert.setView(dialogView);
        alert.setPositiveButton("Save", null);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        final AlertDialog dialog = alert.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etReview.getText().toString().isEmpty()) {
                    etReview.setError("Please enter a review!");
                } else {
                    boolean wouldRec = (rgRec.getCheckedRadioButtonId() == R.id.rbYes) ? true : false;
                    saveReviewToDatabase(etReview.getText().toString(), ratingbar.getRating(), wouldRec);
                    dialog.dismiss();
                }
            }
        });
    }

    private void saveReviewToDatabase(String comments, float rating, boolean wouldRec) {
        Review review = new Review();
        review.setComments(comments);
        review.setWouldRecommend(wouldRec);
        review.setRentalId(rsId);
        review.setUserId(Integer.valueOf(ApplicationHelper.getInstance().fetchMyDetails(this).get("id")));
        review.setRating(((int) rating));
        review.setUserName(ApplicationHelper.getInstance().fetchMyDetails(this).get("name"));
        Database.getInstance().saveReviewToDatabase(review);
        populateReviewsList(true);
    }

    private void init() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddReview);
        lvRating = (ListView) findViewById(R.id.lvRating);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWriteNewReviewAlert();
            }
        });
        populateReviewsList(false);
    }
}
