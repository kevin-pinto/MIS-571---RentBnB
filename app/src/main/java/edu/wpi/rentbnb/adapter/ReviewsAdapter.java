package edu.wpi.rentbnb.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import edu.wpi.rentbnb.R;
import edu.wpi.rentbnb.datamodels.Review;

/**
 * Created by Kevin on 28-11-2016.
 */
public class ReviewsAdapter extends ArrayAdapter<Review> {
	private ArrayList<Review> reviewsList;
	private Activity activity;

	public ReviewsAdapter(Activity activity, ArrayList<Review> objects) {
		super(activity, IGNORE_ITEM_VIEW_TYPE, objects);
		this.reviewsList = objects;
		this.activity = activity;
	}

	@Override
	public Review getItem(int position) {
		return reviewsList.get(position);
	}

	@Override
	public int getCount() {
		return reviewsList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ReviewHolder holder = null;
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.item_review, parent, false);
			holder = new ReviewHolder();
			final Review review = reviewsList.get(position);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvNames);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tvTimestamp);
			holder.tvRecommendation = (TextView) convertView.findViewById(R.id.tvRecommend);
			holder.tvReview = (TextView) convertView.findViewById(R.id.tvReview);
			holder.rbReview = (RatingBar) convertView.findViewById(R.id.rating);
			holder.tvName.setText(review.getUserName());
			holder.tvDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(review.getCreationDate()));
			holder.tvRecommendation.setText("Would recommend: " + (review.isWouldRecommend() ? "Yes" : "No"));
			holder.tvReview.setText(review.getComments());
			holder.rbReview.setRating(review.getRating());
			convertView.setTag(holder);
		} else {
			holder = (ReviewHolder) convertView.getTag();
		}
		return convertView;
	}

	public class ReviewHolder {
		private RatingBar rbReview;
		private TextView tvName, tvDate, tvReview, tvRecommendation;
	}
}
