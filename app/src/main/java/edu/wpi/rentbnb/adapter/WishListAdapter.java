package edu.wpi.rentbnb.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.wpi.rentbnb.R;
import edu.wpi.rentbnb.datamodels.Photo;
import edu.wpi.rentbnb.datamodels.RentalListingDetailsDTO;
import edu.wpi.rentbnb.datamodels.RentalSpace;
import edu.wpi.rentbnb.datamodels.WishList;
import edu.wpi.rentbnb.helper.ApplicationHelper;

/**
 * Created by Kevin on 28-11-2016.
 */
public class WishListAdapter extends ArrayAdapter<RentalListingDetailsDTO> {
	private ArrayList<RentalListingDetailsDTO> rentalDetailsList;
	private Activity activity;
	private ArrayList<Photo> photoList;
	private HashMap<Integer, WishList> wishMap;

	public WishListAdapter(Activity activity, ArrayList<RentalListingDetailsDTO> objects, HashMap<Integer, WishList> wishMap) {
		super(activity, IGNORE_ITEM_VIEW_TYPE, objects);
		this.rentalDetailsList = objects;
		this.activity = activity;
		this.wishMap = wishMap;
		this.photoList = ApplicationHelper.getInstance().getRandomPhotos(activity);
	}

	@Override
	public RentalListingDetailsDTO getItem(int position) {
		return rentalDetailsList.get(position);
	}

	@Override
	public int getCount() {
		return rentalDetailsList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RentalSpaceHolder holder = null;
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.item_wishlist, parent, false);
			holder = new RentalSpaceHolder();
			final RentalListingDetailsDTO rentalListingDetail = rentalDetailsList.get(position);
			WishList wish = wishMap.get(rentalListingDetail.getId());
			holder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
			holder.tvMilesAway = (TextView) convertView.findViewById(R.id.tvMilesAway);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
			holder.tvSavedDate = (TextView) convertView.findViewById(R.id.tvSavedDate);
			holder.tvComments = (TextView) convertView.findViewById(R.id.tvComments);
			holder.ivPreview = (ImageView) convertView.findViewById(R.id.ivPreview);
			holder.tvAddress.setText(getApartmentName(rentalListingDetail));
			holder.tvMilesAway.setText(String.valueOf(rentalListingDetail.getLocation().getDistanceFromCampus()) + " miles from campus");
			String myFormat = "MMM dd, yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
			holder.tvSavedDate.setText(sdf.format(wish.getCreationDate()));
			holder.tvComments.setText(wish.getComments());
			holder.tvPrice.setText(rentalListingDetail.getRentalSpaceDetails().getPrice().toString() + " per day.");
			holder.ivPreview.setImageBitmap(getPreview(rentalListingDetail));
			convertView.setTag(holder);
		} else {
			holder = (RentalSpaceHolder) convertView.getTag();
		}
		return convertView;
	}

	private Bitmap getPreview(RentalListingDetailsDTO rentalListingDetail) {
		Bitmap image;
		int randomNum = new Random().nextInt((5 - 0) + 1) + 0;
		image = photoList.get(randomNum).getData();
		if (image == null) {
			image = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.default_rental_space);
		}
		return image;
	}

	private String getApartmentName(RentalListingDetailsDTO detailsDTO) {
		String name = "";
		name = name.concat("Apt ").concat(detailsDTO.getApartmentDetails().getNumber().toString()).concat(", ").concat(detailsDTO.getLocation().getStreet());
		return name;
	}

	private String getAvailability(RentalSpace rentalSpace) {
		String availability = "";
		availability = availability.concat(new SimpleDateFormat("MM/dd/yyyy").format(rentalSpace.getAvailableFrom())).concat(" to ")
				.concat(new SimpleDateFormat("MM/dd/yyyy").format(rentalSpace.getAvailableTo()));
		return availability;
	}

	public void swapItems(ArrayList<RentalListingDetailsDTO> listingsList) {
		this.rentalDetailsList.clear();
		this.rentalDetailsList = listingsList;
		notifyDataSetChanged();
	}

	public class RentalSpaceHolder {
		private ImageView ivPreview;
		private TextView tvAddress, tvSavedDate, tvPrice, tvComments, tvMilesAway;
	}
}
