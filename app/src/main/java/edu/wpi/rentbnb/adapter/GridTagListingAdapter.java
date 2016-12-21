package edu.wpi.rentbnb.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import edu.wpi.rentbnb.R;
import edu.wpi.rentbnb.datamodels.Tag;

/**
 * Created by Kevin on 09-12-2016.
 */
public class GridTagListingAdapter extends ArrayAdapter<Tag> {
	private ArrayList<Tag> tagsList;
	private Activity activity;

	public GridTagListingAdapter(Activity activity, ArrayList<Tag> objects) {
		super(activity, IGNORE_ITEM_VIEW_TYPE, objects);
		this.tagsList = objects;
		this.activity = activity;
	}

	@Override
	public Tag getItem(int position) {
		return tagsList.get(position);
	}

	@Override
	public int getCount() {
		return tagsList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TagsListingHolder holder = null;
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.item_grid_tag_listing, parent, false);
			holder = new TagsListingHolder();
			holder.textView = (CheckBox) convertView.findViewById(R.id.item);
			holder.textView.setText(tagsList.get(position).getName());
			convertView.setTag(holder);
		} else {
			holder = (TagsListingHolder) convertView.getTag();
		}
		return convertView;
	}

	public class TagsListingHolder {
		private CheckBox textView;
	}
}
