package edu.wpi.rentbnb.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import edu.wpi.rentbnb.R;
import edu.wpi.rentbnb.datamodels.Photo;

/**
 * Created by Kevin on 11-12-2016.
 */
public class SlidingImageAdapter extends PagerAdapter {
	private ArrayList<Photo> images;
	private LayoutInflater inflater;
	private Context context;

	public SlidingImageAdapter(Context context, ArrayList<Photo> images) {
		this.context = context;
		this.images = images;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return images.size();
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		View imageLayout = inflater.inflate(R.layout.pager_detail_item, view, false);
		assert imageLayout != null;
		Photo photo = images.get(position);
//		photo.setData(BitmapFactory.decodeByteArray(photo.getImageBytes(), 0, photo.getImageBytes().length));
		final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
		imageView.setImageBitmap(photo.getData());
		view.addView(imageLayout, 0);
		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}
}
